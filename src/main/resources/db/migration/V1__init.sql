create table users (
    id int8 not null,
    name varchar(50) not null,
    password varchar(100) not null,
    profile varchar(2) not null,
    username varchar(20) not null,
    primary key (id)
);

alter table users
drop constraint if exists UK_r43af9ap4edm43mmtq01oddj6;

alter table users
    add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);

create sequence seq_users start 1 increment 1;