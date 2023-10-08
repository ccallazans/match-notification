package entity

import (
	"context"
)

type User struct {
	ID     uint
	Email  string
	Topics []Topic
}

type UserRepo interface {
	Save(ctx context.Context, user *User) (*User, error)
	FindByEmail(ctx context.Context, email string) (*User, error)
	FindAllByTopic(ctx context.Context, topic string) ([]*User, error)
	Exists(ctx context.Context, email string) (bool, error)
}
