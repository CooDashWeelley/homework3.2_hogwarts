-- liquibase formatted sql

--changeset ivan:1
create index name_index on student  (name);
create index n_c_index on faculty (name, color);