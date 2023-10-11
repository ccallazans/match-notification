package usecase

import (
	"context"

	"github.com/ccallazans/match-notification/internal/db"
)

type UserUsecase struct {
	repo db.Querier
}

func NewUserUsecase(repo db.Querier) *UserUsecase {
	return &UserUsecase{
		repo: repo,
	}
}

func (u *UserUsecase) FindUsersByTopic(ctx context.Context, topicName string) ([]db.User, error) {
	users, err := u.repo.FindAllUsersByTopicName(ctx, topicName)
	if err != nil {
		return nil, err
	}

	return users, nil
}
