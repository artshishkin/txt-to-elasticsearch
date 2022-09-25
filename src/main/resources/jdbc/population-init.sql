set FOREIGN_KEY_CHECKS = 0;
drop table if exists region;
drop table if exists population;
set FOREIGN_KEY_CHECKS = 1;

create table region
(
    id   varchar(255) not null,
    name varchar(255) not null,
    primary key (id)
);

create table population
(
    id        bigint       not null auto_increment,
    region_id varchar(255) not null,
    age       int          not null,
    men       bigint       not null,
    women     bigint       not null,
    primary key (id),
    foreign key (region_id) references region (id)
);