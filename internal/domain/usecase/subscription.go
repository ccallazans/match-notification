package usecase

import (
	"context"

	"github.com/ccallazans/match-notification/internal/domain/entity"
	"github.com/ccallazans/match-notification/internal/domain/utils"
)

type Subscription struct {
	userRepo  entity.UserRepo
	topicRepo entity.TopicRepo
}

func NewSubscription(userRepo entity.UserRepo, topicRepo entity.TopicRepo) *Subscription {
	return &Subscription{
		userRepo:  userRepo,
		topicRepo: topicRepo,
	}
}

func (u *Subscription) Subscribe(ctx context.Context, email string, topic string) error {
	validTopic, err := u.topicRepo.FindByName(ctx, topic)
	if err != nil {
		return &utils.ValidationErr{Message: "topic do not exist"}
	}

	user := &entity.User{
		Email: email,
		Info:  []*entity.Topic{validTopic},
	}

	err = user.Validate()
	if err != nil {
		return &utils.ValidationErr{Message: "email is not valid"}
	}

	exists, _ := u.userRepo.FindByEmail(ctx, email)
	if exists != nil {
		return &utils.ValidationErr{Message: "email already registered"}
	}

	err = u.userRepo.Save(ctx, user)
	if err != nil {
		return err
	}

	return nil
}
