// Code generated by sqlc. DO NOT EDIT.
// versions:
//   sqlc v1.22.0

package pgImpl

import ()

type Notification struct {
	ID      int64
	Type    string
	TopicID int32
	Body    string
}

type Topic struct {
	ID   int64
	Name string
}

type User struct {
	ID    int64
	Email string
}

type UserTopic struct {
	UserID  int32
	TopicID int32
}
