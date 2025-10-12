create table people (
    people_id serial primary key,
    people_name text not null,
    people_age integer check (people_age > 0),
    drive_license boolean default false
    )

create table cars (
    car_id serial primary key,
    car_brand text not null,
    car_model text not null,
    car_price numeric (11,2) check (car_price > 0.00)
)

create table people_cars (
    id serial primary key,
    people_id serial references people (people_id),
    own_car_id  serial references people (people_id),
    drive_car_id serial  references cars (car_id),

    unique (people_id, own_car_id)
)


