package entity

import (
	"context"

	"github.com/ccallazans/match-notification/internal/domain"
)

type Notification struct {
	ID    uint
	Type  domain.NotificationType
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
