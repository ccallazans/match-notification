package api

import (
	"net/http"

	"github.com/ccallazans/match-notification/internal/utils"
	"github.com/gin-gonic/gin"
)

func (a *Api) Subscribe(c *gin.Context) {
	var subscribe Subscribe
	err := c.ShouldBindJSON(&subscribe)
	if err != nil {
		HTTPErrorHandler(c, &utils.ValidationErr{Message: "bad request"})
		return
	}

	err = a.subscriptionUsecase.Subscribe(c.Request.Context(), subscribe.Email, subscribe.Topic)
	if err != nil {
		HTTPErrorHandler(c, err)
		return
	}

	c.JSON(http.StatusOK, "OK")
}
