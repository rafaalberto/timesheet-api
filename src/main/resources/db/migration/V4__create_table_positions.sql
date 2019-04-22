create table positions (
    id int8 not null,
    title varchar(50) not null,
    primary key (id)
);

alter table positions
drop constraint if exists UK_positions_1;

alter table positions
    add constraint UK_positions_1 unique (title);

create sequence seq_positions start 1 increment 1;