package entity

import (
	"context"

	"gorm.io/gorm"
)

type Topic struct {
	gorm.Model
	ID   uint
	Name string `gorm:"unique"`
}

type TopicRepo interface {
	FindByName(ctx context.Context, name string) (*Topic, error)
}
