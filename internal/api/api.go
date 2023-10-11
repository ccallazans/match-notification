package api

import (
	"database/sql"

	"github.com/ccallazans/match-notification/config"
	"github.com/ccallazans/match-notification/internal/db"
	"github.com/ccallazans/match-notification/internal/usecase"
	"github.com/gin-gonic/gin"
)

type Api struct {
	notifyUsecase       *usecase.Notify
	subscriptionUsecase *usecase.Subscription
	userUsecase         *usecase.UserUsecase
}

func NewApi(conn *sql.DB) *gin.Engine {
	sqsClient := config.NewSQSClient()

	repository := db.New(conn)

	notifyUsecase := usecase.NewNotify(repository, sqsClient)
	subscriptionUsecase := usecase.NewSubscription(repository)
	userUsecase := usecase.NewUserUsecase(repository)

	api := &Api{
		notifyUsecase:       notifyUsecase,
		subscriptionUsecase: subscriptionUsecase,
		userUsecase:         userUsecase,
	}

	router := gin.Default()
	router.POST("/subscribe", api.Subscribe)
	router.POST("/notify", api.Notify)
	router.GET("/users", api.FindUsersByTopic)

	return router
}
