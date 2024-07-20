create schema if not exists task_tracker;

create table if not exists tab_role(
    id bigserial primary key,
    name varchar(255) not null
    );
