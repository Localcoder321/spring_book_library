create table books (
    id bigserial primary key,
    title varchar(255) not null,
    author varchar(255) not null,
    year int,
    genre varchar(255) not null,
    is_available boolean not null default true
);

create table readers (
    id bigserial primary key,
    name varchar(255) not null,
    email varchar(255) not null unique,
    registered_on timestamp not null
);

create table rentals (
    id bigserial primary key,
    book_id bigint not null references books(id),
    reader_id bigint not null references readers(id),
    taken_on timestamp not null,
    due_on timestamp not null,
    returned_on timestamp
);