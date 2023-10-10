package models

import (
	"fmt"

	"github.com/ccallazans/match-notification/internal/utils"
)

const (
	MATCH NotificationType = "MATCH"
	GOAL  NotificationType = "GOAL"
)

type NotificationType string

var NotificationTypeMap = map[string]NotificationType{
	"MATCH": MATCH,
	"GOAL":  GOAL,
}

func ValidNotificationType(str string) error {
	_, ok := NotificationTypeMap[str]
	if !ok {
		return &utils.ValidationErr{Message: fmt.Sprintf("invalid type: %s", str)}
	}

	return nil
}
