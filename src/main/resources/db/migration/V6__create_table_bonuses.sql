create table bonuses (
    id bigint not null,
    employee_id bigint not null,
    month_reference numeric(4) not null,
    year_reference numeric(4) not null,
    code varchar(5),
    description varchar(50) not null,
    cost numeric(10,2) default 0,
    constraint bonuses_pkey primary key (id),
    constraint bonuses_fkey_employee_id FOREIGN KEY (employee_id)
        references employees (id)
);

alter table bonuses
drop constraint if exists UK_bonuses;

create sequence seq_bonuses start 1 increment 1;

create index idx_bonuses_1 on bonuses (employee_id);