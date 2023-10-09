package usecase

import (
	"context"
	"encoding/json"
	"log"
	"os"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/sqs"
	"github.com/ccallazans/match-notification/internal/domain"
	"github.com/ccallazans/match-notification/internal/domain/entity"
	"github.com/ccallazans/match-notification/internal/domain/utils"
	"github.com/ccallazans/match-notification/internal/infra/queue"
)

type Notify struct {
	notificationRepo entity.NotificationRepo
	topicRepo        entity.TopicRepo
	sqsClient        *queue.SQSClient
}

func NewNotify(notificationRepo entity.NotificationRepo, topicRepo entity.TopicRepo, sqsClient *queue.SQSClient) *Notify {
	return &Notify{
		notificationRepo: notificationRepo,
		topicRepo:        topicRepo,
		sqsClient:        sqsClient,
	}
}

func (u *Notify) Create(ctx context.Context, notificationType string, topic string, body string) error {
	validTopic, err := u.validateTopic(ctx, topic)
	if err != nil {
		return err
	}

	notification := &entity.Notification{
		Type:    domain.NotificationType(notificationType),
		TopicID: validTopic.ID,
		Body:    body,
	}

	err = notification.Type.Validate()
	if err != nil {
		return err
	}

	err = u.notificationRepo.Save(ctx, notification)
	if err != nil {
		return err
	}

	err = u.sendToQueue(ctx, &entity.NotificationDomain{Notification: *notification, Topic: *validTopic})
	if err != nil {
		return err
	}

	return nil
}

func (u *Notify) validateTopic(ctx context.Context, topic string) (*entity.Topic, error) {
	topicExists, err := u.topicRepo.Exists(ctx, topic)
	if err != nil {
		return nil, err
	}

	if !topicExists {
		return nil, &utils.ValidationErr{Message: "topic do not exist"}
	}

	validTopic, err := u.topicRepo.FindByName(ctx, topic)
	if err != nil {
		return nil, err
	}

	return validTopic, nil
}

func (u *Notify) sendToQueue(ctx context.Context, notification *entity.NotificationDomain) error {
	sendNotificationQueue := os.Getenv("SQS_SEND_NOTIFICATION")

	type Notify struct {
		Topic string `json:"topic"`
		Type  string `json:"type"`
		Body  string `json:"body"`
	}

	queue := &sqs.GetQueueUrlInput{QueueName: &sendNotificationQueue}
	queueURL, err := u.sqsClient.GetQueueURL(ctx, queue)
	if err != nil {
		log.Printf("error when get queue name: %s", err.Error())
		return err
	}

	objectByte, err := json.Marshal(&Notify{
		Type:  string(notification.Type),
		Topic: notification.Topic.Name,
		Body:  notification.Body,
	})
	if err != nil {
		log.Printf("error when json marshaling: %s", err.Error())
		return err
	}

	sMInput := &sqs.SendMessageInput{
		MessageBody: aws.String(string(objectByte)),
		QueueUrl:    queueURL.QueueUrl,
	}

	_, err = u.sqsClient.SendMsg(ctx, sMInput)
	if err != nil {
		log.Printf("error sending message to queue: %s", err.Error())
		return err
	}

	return nil
}
