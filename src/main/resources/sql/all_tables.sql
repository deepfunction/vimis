create table mis_mo
(
    id bigserial
        constraint mis_mo_pkey
            primary key,
    deleted boolean default false not null,
    disabled_for_hour boolean default false not null,
    disabled_for_long boolean default false not null,
    disabled_from timestamp,
    disabling_reason text,
    mis_uuid varchar(255),
    oid varchar(255) not null
        constraint uk_dc03x3gfucuuv7ga26chgrwln
            unique,
    short_name varchar(255) not null
        constraint uk_1ic64g43lb18208cy1jem5rdi
            unique,
    url varchar(255)
);

create table send_document_result
(
    id bigserial
        constraint send_document_result_pkey
            primary key,
    creation_date timestamp default now() not null,
    description text,
    message_id varchar(255)
        constraint uk_1mjitnfabuoa2ryd6ykne0ppw
            unique,
    mis_result_delivery_error varchar(255),
    mis_result_delivery_status varchar(255),
    status_async varchar(255),
    status_sync varchar(255)
);

create table signature
(
    id bigserial
        constraint signature_pkey
            primary key,
    checksum integer not null,
    creation_date timestamp default now() not null,
    data varchar(255) not null
);

create table document
(
    id bigserial
        constraint document_pkey
            primary key,
    creation_date timestamp default now() not null,
    doc_type integer not null,
    doc_type_version integer not null,
    encoded_document text not null,
    mis_id varchar(255) not null,
    mo_oid varchar(255) not null,
    vimis_type integer not null,
    send_document_result_id bigint
        constraint fk9lsyrjlf6a5vcr7189c2h4yld
            references send_document_result,
    signature_id bigint
        constraint fkfyuu2hbwudyyn73l0rlgsqar6
            references signature
);

create unique index document_mis_id_uindex
    on document (mis_id);

create table requests
(
    id bigserial
        constraint requests_pk
            primary key,
    creation_date timestamp default now() not null,
    request_type varchar(25) not null,
    mis_id varchar not null,
    message_id varchar,
    mo_oid varchar not null,
    vimis_type integer not null,
    doc_type integer not null,
    request_time timestamp not null
);

create unique index requests_id_uindex
    on requests (id);
