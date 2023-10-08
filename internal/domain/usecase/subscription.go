package usecase

import (
	"context"
	"net/mail"

	"github.com/ccallazans/match-notification/internal/domain/entity"
	"github.com/ccallazans/match-notification/internal/domain/utils"
)

type Subscription struct {
	userRepo       entity.UserRepo
	topicRepo      entity.TopicRepo
	userTopicsRepo entity.UserTopicsRepo
}

func NewSubscription(userRepo entity.UserRepo, topicRepo entity.TopicRepo, userTopicsRepo entity.UserTopicsRepo) *Subscription {
	return &Subscription{
		userRepo:       userRepo,
		topicRepo:      topicRepo,
		userTopicsRepo: userTopicsRepo,
	}
}

func (u *Subscription) Subscribe(ctx context.Context, email string, topic string) error {
	validTopic, err := u.validateTopic(ctx, topic)
	if err != nil {
		return err
	}

	err = u.validateEmail(ctx, email)
	if err != nil {
		return err
	}

	user := &entity.User{
		Email: email,
	}

	err = u.validateUser(ctx, user)
	if err != nil {
		return err
	}

	user, err = u.userRepo.Save(ctx, user)
	if err != nil {
		return err
	}

	err = u.userTopicsRepo.Save(ctx, &entity.UserTopics{UserID: user.ID, TopicID: validTopic.ID})
	if err != nil {
		return err
	}

	return nil
}

func (u *Subscription) validateTopic(ctx context.Context, topic string) (*entity.Topic, error) {
	topicExists, err := u.topicRepo.Exists(ctx, topic)
	if err != nil {
		return nil, err
	}

	if !topicExists {
		return nil, &utils.ValidationErr{Message: "topic do not exist"}
	}

	validTopic, err := u.topicRepo.FindByName(ctx, topic)
	if err != nil {
		return nil, err
	}

	return validTopic, nil
}

func (u *Subscription) validateEmail(ctx context.Context, email string) error {
	addr, err := mail.ParseAddress(email)
	if err != nil {
		return err
	}

	if addr.Name != "" || addr.Address == "" {
		return err
	}

	return nil
}

func (u *Subscription) validateUser(ctx context.Context, user *entity.User) error {
	userExists, err := u.userRepo.Exists(ctx, user.Email)
	if err != nil {
		return err
	}

	if userExists {
		return &utils.ValidationErr{Message: "email already registered"}
	}

	return nil
}
