package entity

import (
	"context"
)

type Notification struct {
	ID    uint
	Type  string
	Topic Topic
	Body  string
}

type NotificationDomain struct {
	ID    uint
	Type  string
	Topic Topic
	Body  string
}

type NotificationRepo interface {
	Save(ctx context.Context, notification *Notification) error
}
