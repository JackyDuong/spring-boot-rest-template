-- Before running this script, create the database on postgresql and add user
-- create database #DATABASE_NAME;
-- create user #APP_ABBREVIATION with password '#APP_ABBREVIATION';                -- password is not the same on production - check keepass !

drop schema if exists public cascade;

create schema if not exists public;
SET search_path TO public;

GRANT USAGE ON SCHEMA public TO #APP_ABBREVIATION;
ALTER DATABASE #DATABASE_NAME SET search_path = public;

ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON TABLES TO #APP_ABBREVIATION;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON SEQUENCES TO #APP_ABBREVIATION;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON TYPES TO #APP_ABBREVIATION;

------------------------------------------------------------------------
-- Create statements for public schema
------------------------------------------------------------------------

set schema 'public';

create type history_action as enum ( 'NONE', 'CREATE', 'UPDATE', 'CANCEL', 'DELETE', 'UNDEFINED');

create table role (
    id          serial primary key,
    name        varchar(50) not null,
    description varchar(250)
                  );

create table administrator (
    nip        integer not null constraint administrator_pkey primary key,
    first_name varchar(45),
    last_name  varchar(45),
    is_active  boolean default true
                               );


create table history (
    id              serial         not null constraint history_pkey primary key,
    intervention_id varchar(15)    not null,
    sdis_code       varchar(15)    not null,
    action          history_action not null,
    action_time     timestamp      not null,
    description     text           not null,
    nip             integer        not null,
    object_type     varchar(45)    not null
                     );


create table app_configuration (
    id      serial               not null constraint app_configuration_pkey primary key,
    key     varchar(2048) unique not null,
    value   varchar(2048)        not null,
    comment text
                               );

create table blacklisted_token (
    id              serial        not null constraint blacklisted_token_pkey primary key,
    token           varchar(2048) not null,
    nip             int           not null,
    added_at        timestamp     not null,
    expiration_date timestamp     not null
                               );

------------------------------------------------------------------------
-- Create indexes
------------------------------------------------------------------------

create index idx_administrator_nip on public.administrator (nip);

