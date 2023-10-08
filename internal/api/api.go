package api

import (
	"database/sql"

	"github.com/ccallazans/match-notification/internal/domain/usecase"

	"github.com/ccallazans/match-notification/internal/infra/database/pgImpl"
	"github.com/ccallazans/match-notification/internal/infra/database/repository"
	"github.com/ccallazans/match-notification/internal/infra/queue"
	"github.com/gin-gonic/gin"
)

type Api struct {
	notifyUsecase       *usecase.Notify
	subscriptionUsecase *usecase.Subscription
}

func NewApi(db *sql.DB) *gin.Engine {
	sqsClient := queue.NewSQSClient()

	sqlcGen := pgImpl.New(db)

	userRepository := repository.NewPostgresUserRepo(sqlcGen)
	topicRepository := repository.NewPostgresTopicRepo(sqlcGen)
	userTopicsRepository := repository.NewPostgresPostgresUserTopics(sqlcGen)
	notificationRepository := repository.NewPostgresNotificationRepo(sqlcGen)

	notifyUsecase := usecase.NewNotify(notificationRepository, topicRepository, sqsClient)
	subscriptionUsecase := usecase.NewSubscription(userRepository, topicRepository, userTopicsRepository)

	api := &Api{
		notifyUsecase:       notifyUsecase,
		subscriptionUsecase: subscriptionUsecase,
	}

	router := gin.Default()
	router.POST("/subscribe", api.Subscribe)
	router.POST("/notify", api.Notify)

	return router
}
