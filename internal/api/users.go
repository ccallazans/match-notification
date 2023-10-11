package api

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

func (a *Api) FindUsersByTopic(c *gin.Context) {
	topic := c.Query("topic")

	users, err := a.userUsecase.FindUsersByTopic(c.Request.Context(), topic)
	if err != nil {
		HTTPErrorHandler(c, err)
		return
	}

	c.JSON(http.StatusOK, users)
}
