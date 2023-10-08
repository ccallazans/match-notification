package repository

import (
	"context"
	"log"

	"github.com/ccallazans/match-notification/internal/domain/entity"
	"github.com/ccallazans/match-notification/internal/infra/database/pgImpl"
)

type PostgresUserRepo struct {
	db *pgImpl.Queries
}

func NewPostgresUserRepo(db *pgImpl.Queries) entity.UserRepo {
	return &PostgresUserRepo{
		db: db,
	}
}

func (r *PostgresUserRepo) Save(ctx context.Context, user *entity.User) (*entity.User, error) {
	parseUser, err := r.db.SaveUser(ctx, user.Email)
	if err != nil {
		log.Println(err)
		return nil, err
	}

	newUser := &entity.User{
		ID:    uint(parseUser.ID),
		Email: parseUser.Email,
	}

	return newUser, nil
}

func (r *PostgresUserRepo) FindByEmail(ctx context.Context, email string) (*entity.User, error) {
	parseUser, err := r.db.FindUserByEmail(ctx, email)
	if err != nil {
		log.Println(err)
		return nil, err
	}

	user := &entity.User{
		ID:    uint(parseUser.ID),
		Email: parseUser.Email,
	}

	return user, nil
}

func (r *PostgresUserRepo) FindAllByTopic(ctx context.Context, topic string) ([]*entity.User, error) {
	parseUsers, err := r.db.FindAllUsersByTopicName(ctx, topic)
	if err != nil {
		log.Println(err)
		return nil, err
	}

	var users []*entity.User
	for _, user := range parseUsers {
		users = append(users, &entity.User{ID: uint(user.ID), Email: user.Email})
	}

	return users, nil
}

func (r *PostgresUserRepo) Exists(ctx context.Context, email string) (bool, error) {
	exists, err := r.db.ExistsUser(ctx, email)
	if err != nil {
		log.Println(err)
		return false, err
	}

	return exists, nil
}
