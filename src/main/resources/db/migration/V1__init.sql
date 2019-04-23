create table users (
    id bigint not null,
    name varchar(50) not null,
    password varchar(100) not null,
    profile varchar(2) not null,
    username varchar(20) not null,
    constraint users_pkey primary key (id)
);

alter table users
drop constraint if exists UK_users;

alter table users
    add constraint UK_users unique (username);

create sequence seq_users start 1 increment 1;