package api

import (
	"net/http"

	"github.com/ccallazans/match-notification/internal/utils"
	"github.com/gin-gonic/gin"
)

func (a *Api) Notify(c *gin.Context) {
	var notification Notification
	err := c.ShouldBind(&notification)
	if err != nil {
		HTTPErrorHandler(c, &utils.ValidationErr{Message: "bad request"})
		return
	}

	err = a.notifyUsecase.Create(c.Request.Context(), notification.Type, notification.Topic, notification.Body)
	if err != nil {
		HTTPErrorHandler(c, err)
		return
	}

	c.JSON(http.StatusOK, "OK")
}
