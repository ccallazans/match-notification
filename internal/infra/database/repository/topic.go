package repository

import (
	"context"
	"log"

	"github.com/ccallazans/match-notification/internal/domain/entity"
	"github.com/ccallazans/match-notification/internal/infra/database/pgImpl"
)

type PostgresTopicRepo struct {
	db *pgImpl.Queries
}

func NewPostgresTopicRepo(db *pgImpl.Queries) entity.TopicRepo {
	return &PostgresTopicRepo{
		db: db,
	}
}

func (r *PostgresTopicRepo) FindByName(ctx context.Context, name string) (*entity.Topic, error) {
	topic, err := r.db.FindTopicByName(ctx, name)
	if err != nil {
		log.Println(err)
		return nil, err
	}

	parse := &entity.Topic{
		ID:   uint(topic.ID),
		Name: topic.Name,
	}

	return parse, nil
}

func (r *PostgresTopicRepo) Exists(ctx context.Context, name string) (bool, error) {
	exists, err := r.db.ExistsTopic(ctx, name)
	if err != nil {
		log.Println(err)
		return false, err
	}

	return exists, nil
}
