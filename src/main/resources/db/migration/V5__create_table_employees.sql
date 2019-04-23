create table employees (
    id bigint not null,
    name varchar(50) not null,
    record_number varchar(10) not null,
    position_id bigint not null,
    company_id bigint not null,
    cost_center varchar(20),
    cost_hour numeric(10,2) default 0,
    constraint employees_pkey primary key (id),
    constraint employees_fkey_position_id FOREIGN KEY (position_id)
        references positions (id),
    constraint employees_fkey_company_id FOREIGN KEY (company_id)
        references companies (id)
);

alter table employees
drop constraint if exists UK_employees_1;

alter table employees
    add constraint UK_employees_1 unique (record_number);

create sequence seq_employees start 1 increment 1;

create index idx_employees_1 on employees (position_id);
create index idx_employees_2 on employees (company_id);