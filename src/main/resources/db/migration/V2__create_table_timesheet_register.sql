create table timesheet_register (
    id int8 not null,
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
    primary key (id)
);

alter table timesheet_register
drop constraint if exists UK_r43af9ap4edm43mmtq01oddj6;

create sequence seq_timesheet_register start 1 increment 1;