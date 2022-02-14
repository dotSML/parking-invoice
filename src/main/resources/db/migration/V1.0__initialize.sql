CREATE TABLE customer
(
    id         bigint PRIMARY KEY AUTO_INCREMENT,
    email      varchar(255) NOT NULL UNIQUE,
    tier       varchar(255) NOT NULL DEFAULT 'STD',
    first_name varchar(255),
    last_name  varchar(255)
);

CREATE TABLE parking
(
    id         bigint PRIMARY KEY AUTO_INCREMENT,
    start_ts      TIMESTAMP,
    end_ts       TIMESTAMP,
    customer_id bigint,
    property_id bigint,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
)
