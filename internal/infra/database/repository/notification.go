package repository

import (
	"context"
	"log"

	"github.com/ccallazans/match-notification/internal/domain/entity"
	"github.com/ccallazans/match-notification/internal/infra/database/pgImpl"
)

type PostgresNotificationRepo struct {
	db *pgImpl.Queries
}

func NewPostgresNotificationRepo(db *pgImpl.Queries) entity.NotificationRepo {
	return &PostgresNotificationRepo{
		db: db,
	}
}

func (r *PostgresNotificationRepo) Save(ctx context.Context, notification *entity.Notification) error {
	parse := pgImpl.SaveNotificationParams{
		Type:    string(notification.Type),
		TopicID: int32(notification.TopicID),
		Body:    notification.Body,
	}

	_, err := r.db.SaveNotification(ctx, parse)
	if err != nil {
		log.Println(err)
		return err
	}

	return nil
}
