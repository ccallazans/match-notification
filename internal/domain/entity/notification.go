package entity

import (
	"context"

	"github.com/ccallazans/match-notification/internal/domain"
	"gorm.io/gorm"
)

type Notification struct {
	gorm.Model
	ID      uint
	Type    domain.NotificationType
	TopicID uint
	Topic   Topic
	Body    string
}

type NotificationRepo interface {
	Save(ctx context.Context, notification *Notification) error
}
