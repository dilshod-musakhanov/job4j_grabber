create table if not exists post(
    id serial,
    name varchar(255),
    text text,
    link varchar(255) unique,
    created timestamp,
)