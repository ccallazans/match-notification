-- name: SaveUser :one
INSERT INTO users (email) VALUES ($1) RETURNING *;

-- name: FindUserByEmail :one
SELECT * FROM users WHERE email = $1 LIMIT 1;

-- name: FindAllUsersByTopicName :many
SELECT u.id, u.email 
FROM users u
JOIN user_topics ut ON u.id = ut.user_id
JOIN topics t ON ut.topic_id = t.id
WHERE t.name = $1;

-- name: ExistsUser :one
SELECT EXISTS (SELECT 1 FROM users WHERE email = $1);

-- name: SaveTopic :one
INSERT INTO topics (name) VALUES ($1) RETURNING *;

-- name: FindTopicByName :one
SELECT * FROM topics WHERE name = $1 LIMIT 1;

-- name: FindAllTopicsByUserID :many
SELECT t.id, t.name
FROM topics t
JOIN user_topics ut ON t.id = ut.topic_id
WHERE ut.user_id = $1;

-- name: ExistsTopic :one
SELECT EXISTS (SELECT 1 FROM topics WHERE name = $1);

-- name: SaveNotification :one
INSERT INTO notifications (type, topic_id, body) VALUES ($1, $2, $3) RETURNING *;

-- name: FindNotificationByID :one
SELECT * FROM notifications WHERE id = $1 LIMIT 1;

-- name: SaveUserTopics :one
INSERT INTO user_topics (user_id, topic_id) VALUES ($1, $2) RETURNING *;

-- name: FindUserTopicsByTopicID :one
SELECT * FROM user_topics WHERE user_id = $1 LIMIT 1;

-- name: FindUserTopicsByUserID :one
SELECT * FROM user_topics WHERE topic_id = $1 LIMIT 1;