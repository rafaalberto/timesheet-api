create table companies (
    id bigint not null,
    document varchar(18) not null,
    name varchar(50) not null,
    constraint companies_pkey primary key (id)
);

alter table companies
drop constraint if exists UK_companies_1;

alter table companies
    add constraint UK_companies_1 unique (document);

create sequence seq_companies start 1 increment 1;