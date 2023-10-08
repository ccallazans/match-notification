package repository

import (
	"context"
	"log"

	"github.com/ccallazans/match-notification/internal/domain/entity"
	"github.com/ccallazans/match-notification/internal/infra/database/pgImpl"
)

type PostgresUserTopics struct {
	db *pgImpl.Queries
}

func NewPostgresPostgresUserTopics(db *pgImpl.Queries) entity.UserTopicsRepo {
	return &PostgresUserTopics{
		db: db,
	}
}

func (r *PostgresUserTopics) Save(ctx context.Context, userTopics *entity.UserTopics) error {
	parse := pgImpl.SaveUserTopicsParams{
		UserID:  int32(userTopics.UserID),
		TopicID: int32(userTopics.TopicID),
	}

	_, err := r.db.SaveUserTopics(ctx, parse)
	if err != nil {
		log.Println(err)
		return err
	}

	return nil
}
