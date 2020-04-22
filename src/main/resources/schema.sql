DROP TABLE IF EXISTS customer_accounts;

CREATE TABLE customer_accounts
(
    customer_id BIGSERIAL,
    balance DOUBLE PRECISION,
    CONSTRAINT PK_customer_account PRIMARY KEY (customer_id)
);