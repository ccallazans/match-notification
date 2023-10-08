package pgimpl

import (
	"context"

	"github.com/ccallazans/match-notification/internal/domain/entity"
	"gorm.io/gorm"
)

type PostgresNotificationRepo struct {
	db *gorm.DB
}

func NewPostgresNotificationRepo(db *gorm.DB) entity.NotificationRepo {
	return &PostgresNotificationRepo{
		db: db,
	}
}

func (r *PostgresNotificationRepo) Save(ctx context.Context, notification *entity.Notification) error {
	err := r.db.Create(notification).Error
	if err != nil {
		return err
	}

	return nil
}
