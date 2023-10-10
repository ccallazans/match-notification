package usecase

import (
	"context"
	"fmt"
	"net/mail"
	"strings"

	"github.com/ccallazans/match-notification/internal/db"
	"github.com/ccallazans/match-notification/internal/utils"
)

type Subscription struct {
	repo db.Querier
}

func NewSubscription(repo db.Querier) *Subscription {
	return &Subscription{
		repo: repo,
	}
}

func (u *Subscription) Subscribe(ctx context.Context, email string, topicName string) error {
	topic, err := u.repo.FindTopicByName(ctx, strings.ToUpper(topicName))
	if err != nil {
		return &utils.ValidationErr{Message: fmt.Sprintf("invalid topic: %s", topicName)}
	}

	err = validateEmail(ctx, email)
	if err != nil {
		return err
	}

	user := &db.User{
		Email: email,
	}

	err = u.validateUser(ctx, user)
	if err != nil {
		return err
	}

	newUser, err := u.repo.SaveUser(ctx, user.Email)
	if err != nil {
		return err
	}

	_, err = u.repo.SaveUserTopics(ctx, db.SaveUserTopicsParams{
		UserID:  int32(newUser.ID),
		TopicID: int32(topic.ID),
	})
	if err != nil {
		return err
	}

	return nil
}

func validateEmail(ctx context.Context, email string) error {
	addr, err := mail.ParseAddress(email)
	if err != nil {
		return err
	}

	if addr.Name != "" || addr.Address == "" {
		return err
	}

	return nil
}

func (u *Subscription) validateUser(ctx context.Context, user *db.User) error {
	userExists, err := u.repo.ExistsUser(ctx, user.Email)
	if err != nil {
		return err
	}

	if userExists {
		return &utils.ValidationErr{Message: "email already registered"}
	}

	return nil
}
