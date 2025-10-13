-- liquibase formatted sql

--changeset ivan:1
create index name_index on student  (name);
create index n_c_index on faculty (name, color);

--changeset ivan:2 (<rename indexes>)
drop index name_index;
create index name_student_index on student (age);
drop index n_c_index;
create index name_color_faculty_index on faculty (name,color);