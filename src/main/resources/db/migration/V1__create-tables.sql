CREATE TABLE IF NOT EXISTS topics (
    id BIGSERIAL PRIMARY KEY,
    name text UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS notifications (
    id BIGSERIAL PRIMARY KEY,
    type text NOT NULL,
    message TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS notification_topics (
    notification_id BIGSERIAL REFERENCES notifications(id),
    topic_id BIGSERIAL REFERENCES topics(id),
    PRIMARY KEY (notification_id, topic_id)
);

CREATE TABLE IF NOT EXISTS subscriptions (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS subscription_topics (
    subscription_id BIGSERIAL REFERENCES subscriptions(id),
    topic_id BIGSERIAL REFERENCES topics(id),
    PRIMARY KEY (subscription_id, topic_id)
);

CREATE TABLE IF NOT EXISTS types (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);
