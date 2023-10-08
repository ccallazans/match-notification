package entity

import "context"

type UserTopics struct {
	UserID  uint
	TopicID uint
}

type UserTopicsRepo interface {
	Save(ctx context.Context, userTopics *UserTopics) error
}
