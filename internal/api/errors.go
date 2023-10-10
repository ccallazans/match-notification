package api

import (
	"net/http"

	"github.com/ccallazans/match-notification/internal/utils"
	"github.com/gin-gonic/gin"
)

func HTTPErrorHandler(c *gin.Context, err error) {
	switch err.(type) {
	case *utils.ValidationErr:
		apiError := err.(*utils.ValidationErr)
		c.JSON(http.StatusBadRequest, gin.H{"message": apiError.Message})

	default:
		c.JSON(http.StatusInternalServerError, gin.H{"message": "InternalServerError"})
	}
}
