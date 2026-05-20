CREATE TABLE products (
    id          BIGINT          GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        VARCHAR(255)    NOT NULL,
    description VARCHAR(1000),
    price       NUMERIC(19, 2)  NOT NULL
);
