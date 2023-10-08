package entity

import (
	"context"
)

type Topic struct {
	ID   uint
	Name string
}

type TopicRepo interface {
	FindByName(ctx context.Context, name string) (*Topic, error)
	Exists(ctx context.Context, name string) (bool, error)
}
