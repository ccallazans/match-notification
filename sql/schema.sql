CREATE TABLE IF NOT EXISTS topics (
    id BIGSERIAL PRIMARY KEY,
    name text UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS notifications (
    id BIGSERIAL PRIMARY KEY,
    type text NOT NULL,
    topic_id INTEGER NOT NULL REFERENCES topics(id),
    body TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user_topics (
    user_id INTEGER REFERENCES users(id),
    topic_id INTEGER REFERENCES topics(id),
    PRIMARY KEY (user_id, topic_id)
);