CREATE TABLE IF NOT EXISTS sales (
    id SERIAL PRIMARY KEY,
    price NUMERIC(10,2) NOT NULL,
    percentage FLOAT default 10.00,
    commission NUMERIC(10,2) not null
)