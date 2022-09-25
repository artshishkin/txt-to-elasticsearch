create table if not exists population
(
    id          bigint          not null auto_increment,
    region_id   varchar(255)    not null,
    age         int             not null,
    men         bigint          not null,
    women       bigint          not null,
    primary key (id)
);