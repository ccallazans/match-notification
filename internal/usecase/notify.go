package usecase

import (
	"context"
	"encoding/json"
	"fmt"
	"log"
	"os"
	"strings"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/sqs"
	"github.com/ccallazans/match-notification/config"
	"github.com/ccallazans/match-notification/internal/db"
	"github.com/ccallazans/match-notification/internal/models"
	"github.com/ccallazans/match-notification/internal/utils"
)

type Notify struct {
	repo      db.Querier
	sqsClient *config.SQSClient
}

func NewNotify(repo db.Querier, sqsClient *config.SQSClient) *Notify {
	return &Notify{
		repo:      repo,
		sqsClient: sqsClient,
	}
}

func (u *Notify) Create(ctx context.Context, notificationType string, topicName string, body string) error {
	err := models.ValidNotificationType(notificationType)
	if err != nil {
		return err
	}

	topic, err := u.repo.FindTopicByName(ctx, strings.ToUpper(topicName))
	if err != nil {
		return &utils.ValidationErr{Message: fmt.Sprintf("invalid topic: %s", topicName)}
	}

	if body == "" {
		return &utils.ValidationErr{Message: "body cannot be empty"}
	}

	notification := db.Notification{
		Type:    notificationType,
		TopicID: int32(topic.ID),
		Body:    body,
	}

	_, err = u.repo.SaveNotification(ctx, db.SaveNotificationParams{
		Type:    notification.Type,
		TopicID: notification.TopicID,
		Body:    notification.Body,
	})
	if err != nil {
		return err
	}

	err = u.sendToQueue(ctx, &notification, topic.Name)
	if err != nil {
		return err
	}

	return nil
}

func (u *Notify) sendToQueue(ctx context.Context, notification *db.Notification, topicName string) error {
	queueName := os.Getenv("SQS_SEND_NOTIFICATION")

	type SendNotificationQueue struct {
		Topic string `json:"topic"`
		Type  string `json:"type"`
		Body  string `json:"body"`
	}

	queue := &sqs.GetQueueUrlInput{QueueName: &queueName}

	queueURL, err := u.sqsClient.GetQueueURL(ctx, queue)
	if err != nil {
		log.Printf("error when get queue name: %s", err.Error())
		return err
	}

	objectByte, err := json.Marshal(&SendNotificationQueue{
		Type:  string(notification.Type),
		Topic: topicName,
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
