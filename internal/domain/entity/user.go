package entity

import (
	"context"
	"net/mail"

	"gorm.io/gorm"
)

type User struct {
	gorm.Model
	ID    uint
	Email string  `gorm:"unique"`
	Info  []*Topic `gorm:"many2many:user_topics;"`
}

func (e *User) Validate() error {
	addr, err := mail.ParseAddress(e.Email)
	if err != nil {
		return err
	}

	if addr.Name != "" || addr.Address == "" {
		return err
	}

	return nil
}

type UserRepo interface {
	Save(ctx context.Context, user *User) error
	FindByEmail(ctx context.Context, email string) (*User, error)
}
