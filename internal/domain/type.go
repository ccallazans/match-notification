package domain

import "errors"

const (
	MATCH NotificationType = "MATCH"
	GOAL  NotificationType = "GOAL"
)

type NotificationType string

func (e *NotificationType) Validate() error {
	switch *e {
	case MATCH:
		return nil
	case GOAL:
		return nil
	default:
		return errors.New("notification type is not valid")
	}
}
