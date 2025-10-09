create table books (
    id bigserial primary key,
    title varchar(255) not null,
    author varchar(255) not null,
    year int,
    genre varchar(255) not null,
    is_available boolean not null default true
);

create index idx_books_title on books(title);
create index idx_books_author on books(author);
create index idx_books_genre on books(genre);

create table readers (
    id bigserial primary key,
    name varchar(255) not null,
    email varchar(255) not null unique,
    registered_on timestamp not null,
    constraint uk_readers_email unique(email)
);

create table rentals (
    id bigserial primary key,
    book_id bigint not null references books(id),
    reader_id bigint not null references readers(id),
    taken_on timestamp not null,
    due_on timestamp not null,
    returned_on timestamp
);

create index idx_rental_due on rentals(due_on);