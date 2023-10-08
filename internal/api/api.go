package api

import (
	"github.com/ccallazans/match-notification/internal/domain/usecase"

	"github.com/ccallazans/match-notification/internal/infra/database/pgImpl"
	"github.com/ccallazans/match-notification/internal/infra/queue"
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
)

type Api struct {
	notifyUsecase       *usecase.Notify
	subscriptionUsecase *usecase.Subscription
}

func NewApi(db *gorm.DB) *gin.Engine {
	sqsClient := queue.NewSQSClient()

	userRepository := pgimpl.NewPostgresUserRepo(db)
	topicRepository := pgimpl.NewPostgresTopicRepo(db)
	notificationRepository := pgimpl.NewPostgresNotificationRepo(db)

	notifyUsecase := usecase.NewNotify(notificationRepository, topicRepository, sqsClient)
	subscriptionUsecase := usecase.NewSubscription(userRepository, topicRepository)

	api := &Api{
		notifyUsecase:       notifyUsecase,
		subscriptionUsecase: subscriptionUsecase,
	}

	router := gin.Default()
	router.POST("/subscribe", api.Subscribe)
	router.POST("/notify", api.Notify)

	return router
}
