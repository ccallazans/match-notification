package config

import (
	"context"

	"github.com/aws/aws-sdk-go-v2/config"
	"github.com/aws/aws-sdk-go-v2/service/sqs"
)

type SQSClient struct {
	client *sqs.Client
}

func NewSQSClient() *SQSClient {
	cfg, err := config.LoadDefaultConfig(context.TODO())
	if err != nil {
		panic(err)
	}

	client := sqs.NewFromConfig(cfg)

	return &SQSClient{
		client: client,
	}
}

func (sqsClient *SQSClient) GetQueueURL(c context.Context, input *sqs.GetQueueUrlInput) (*sqs.GetQueueUrlOutput, error) {
	return sqsClient.client.GetQueueUrl(c, input)
}

func (sqsClient *SQSClient) SendMsg(c context.Context, input *sqs.SendMessageInput) (*sqs.SendMessageOutput, error) {
	return sqsClient.client.SendMessage(c, input)
}
