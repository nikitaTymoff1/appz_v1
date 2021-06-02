create table clients
(
    id serial not null
        constraint clients_pkey
            primary key,
    address varchar(255),
    name varchar(255),
    phone_number varchar(255)
);

create table deliverymen
(
    id      serial not null
        constraint deliverymen_pkey
            primary key,
    age integer not null,
    name varchar(255),
    surname varchar(255),
    wages integer not null
);

create table cars
(
    id serial not null
        constraint cars_pkey
            primary key,
    car_status varchar(255),
    colour varchar(255),
    model varchar(255),
    deliveryman_id serial 
        constraint fkaghxpod6gfwiheka29dnlckat
            references deliverymen
);

create table orders
(
    id serial not null
        constraint orders_pkey
            primary key,
    bonus serial ,
    description varchar(255),
    payment_option varchar(255),
    price serial ,
    client_id serial  not null
        constraint fkm2dep9derpoaehshbkkatam3v
            references clients
);

create table calls
(
    id serial not null
        constraint calls_pkey
            primary key,
    call_status varchar(255),
    deliveryman_id serial  not null
        constraint fkr1x4m9k3tgr3vq2rkow7n58pe
            references deliverymen,
    order_id serial  not null
        constraint uk_eummsjcarijp0xe6aoens0jfi
            unique
        constraint fk5kw1qft7ko32kl2crfpvsy98c
            references orders
);

