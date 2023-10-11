package api

// Notify

type Notification struct {
	Type  string `json:"type" binding:"required"`
	Topic string `json:"topic" binding:"required"`
	Body  string `json:"body" binding:"required"`
}

// Subscription

type Subscribe struct {
	Email string `json:"email" binding:"required"`
	Topic string `json:"topic" binding:"required"`
}
