package pgimpl

import (
	"context"

	"github.com/ccallazans/match-notification/internal/domain/entity"
	"gorm.io/gorm"
)

type PostgresUserRepo struct {
	db *gorm.DB
}

func NewPostgresUserRepo(db *gorm.DB) entity.UserRepo {
	return &PostgresUserRepo{
		db: db,
	}
}

func (r *PostgresUserRepo) Save(ctx context.Context, user *entity.User) error {
	err := r.db.Create(user).Error
	if err != nil {
		return err
	}

	return nil
}

func (r *PostgresUserRepo) FindByEmail(ctx context.Context, email string) (*entity.User, error) {
	var user entity.User

	err := r.db.First(&user, "email = ?", email).Error
	if err != nil {
		return nil, err
	}

	return &user, nil
}
