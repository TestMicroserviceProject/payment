DROP TABLE IF EXISTS customer_accounts;
DROP TABLE IF EXISTS customer_history;
DROP TABLE IF EXISTS payment_rollback_history;

CREATE TABLE customer_accounts
(
    customer_id BIGSERIAL,
    balance     DOUBLE PRECISION,
    CONSTRAINT PK_customer_account PRIMARY KEY (customer_id)
);

CREATE TABLE customer_history
(
    id          BIGSERIAL        NOT NULL,
    customer_id BIGSERIAL        NOT NULL,
    order_id    BIGSERIAL        NOT NULL,
    order_price DOUBLE PRECISION NOT NULL,
    CONSTRAINT PK_customer_history PRIMARY KEY (id)
);

CREATE TABLE payment_rollback_history
(
    id          BIGSERIAL NOT NULL,
    customer_id BIGSERIAL NOT NULL,
    order_id    BIGSERIAL NOT NULL,
    CONSTRAINT PK_payment_rollback_history PRIMARY KEY (id)
);
