CREATE TABLE IF NOT EXISTS topics (
    id BIGSERIAL PRIMARY KEY,
    name text UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS notifications (
    id BIGSERIAL PRIMARY KEY,
    type text NOT NULL,
    topic_id INTEGER NOT NULL REFERENCES topics(id),
    message TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS subscriptions (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS subscription_topics (
    subscription_id INTEGER REFERENCES subscriptions(id),
    topic_id INTEGER REFERENCES topics(id),
    PRIMARY KEY (subscription_id, topic_id)
);

CREATE TABLE IF NOT EXISTS types (
    id BIGSERIAL PRIMARY KEY,
    body TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS operators (
    id BIGSERIAL PRIMARY KEY,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL
);
