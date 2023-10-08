package entity

import (
	"context"
)

type Notification struct {
	ID    uint
	Type  string
	TopicID uint
	Body  string
}

type NotificationDomain struct {
	Notification
	Topic
}

type NotificationRepo interface {
	Save(ctx context.Context, notification *Notification) error
}
