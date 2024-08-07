CREATE TABLE IF NOT EXISTS people (
    id BIGSERIAL PRIMARY KEY,
    first_name TEXT not null,
    last_name TEXT not null
);

CREATE TABLE IF NOT EXISTS sales (
    id BIGSERIAL PRIMARY KEY,
    price NUMERIC(10,2) not null,
    date_stamp TIMESTAMPTZ DEFAULT NOW(),
    person_id BIGINT not null,
    FOREIGN key (person_id) references people(id)
    on delete set null
);

CREATE TABLE IF NOT EXISTS commission (
    id BIGSERIAL PRIMARY KEY,
    commission_type text not null,
    payment NUMERIC(10,2) not null,
    sales_id BIGINT  not null,
    foreign key (sales_id) references sales(id)
    ON DELETE CASCADE
);

