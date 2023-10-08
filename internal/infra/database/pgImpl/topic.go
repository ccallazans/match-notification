package pgimpl

import (
	"context"

	"github.com/ccallazans/match-notification/internal/domain/entity"
	"gorm.io/gorm"
)

type PostgresTopicRepo struct {
	db *gorm.DB
}

func NewPostgresTopicRepo(db *gorm.DB) entity.TopicRepo {
	return &PostgresTopicRepo{
		db: db,
	}
}

func (r *PostgresTopicRepo) FindByName(ctx context.Context, name string) (*entity.Topic, error) {
	var topic entity.Topic

	err := r.db.First(&topic, "name = ?", name).Error
	if err != nil {
		return nil, err
	}

	return &topic, nil
}
