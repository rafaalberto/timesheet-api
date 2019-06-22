create table timesheet_register (
    id bigint not null,
    employee_id bigint not null,
    month_reference numeric(4) not null,
    year_reference numeric(4) not null,
    type varchar(2) not null,
    time_in timestamp without time zone,
    lunch_start timestamp without time zone,
    lunch_end timestamp without time zone,
    time_out timestamp without time zone,
    hours_worked bigint default 0,
    hours_journey bigint default 0,
    extra_hours bigint default 0,
    weekly_rest bigint default 0,
    sumula_90 bigint default 0,
    night_shift bigint default 0,
    paid_night_time bigint default 0,
    constraint timesheet_register_pkey primary key (id),
    constraint timesheet_register_fkey_employee_id FOREIGN KEY (employee_id)
        references positions (id)
);

alter table timesheet_register
drop constraint if exists UK_timesheet_register;

create sequence seq_timesheet_register start 1 increment 1;

create index idx_timesheet_register_1 on timesheet_register (employee_id);