create table t_api_entry
(
    id          serial primary key,

    name        varchar(255) unique not null,
    prefix      varchar(255) unique not null,
    description text,
    tags        text,

    created_by  varchar(100) not null,
    created_at  timestamp(0) not null default CURRENT_TIMESTAMP,
    updated_by  varchar(100)          default null::character varying,
    updated_at  timestamp(0)          default null::timestamp(0)
);

create table t_api_host
(
    id           serial primary key,
    ref_entry_id bigint       not null,

    active       boolean      not null default false,
    profile      int          not null default 1,
    host         varchar(255) not null,
    description  text,

    created_by   varchar(100) not null,
    created_at   timestamp(0) not null default CURRENT_TIMESTAMP,
    updated_by   varchar(100)          default null::character varying,
    updated_at   timestamp(0)          default null::timestamp(0),

    constraint fk_ref_entry_id foreign key (ref_entry_id) references t_api_entry (id)
);

create table t_api_route
(
    id           serial primary key,
    ref_entry_id bigint       not null,

    method       varchar(15)  not null,
    path         varchar(255) not null,
    variables    text,

    created_by   varchar(100) not null,
    created_at   timestamp(0) not null default CURRENT_TIMESTAMP,
    updated_by   varchar(100)          default null::character varying,
    updated_at   timestamp(0)          default null::timestamp(0),

    constraint fk_ref_entry_id foreign key (ref_entry_id) references t_api_entry (id)
);