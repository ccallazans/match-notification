package utils

type ValidationErr struct {
	Message string `json:"message"`
}

func (e *ValidationErr) Error() string {
	return e.Message
}
