drop database if exists wheredb;
create database wheredb;

use wheredb;

CREATE TABLE users (
    id BINARY(16) NOT NULL,
    loginid VARCHAR(15) NOT NULL UNIQUE,
    password BINARY(16) NOT NULL,
    email VARCHAR(255) NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_roles (
    userid BINARY(16) NOT NULL,
    role ENUM ('registered','admin','owner'),
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    PRIMARY KEY (userid, role)
);

CREATE TABLE auth_tokens (
    userid BINARY(16) NOT NULL,
    token BINARY(16) NOT NULL,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    PRIMARY KEY (token)
);

CREATE TABLE restaurants (
    id BINARY(16) NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL,
    avgprice FLOAT NOT NULL,
    owner VARCHAR(100) NOT NULL,
    likes INT NOT NULL,
    address VARCHAR(100) NOT NULL,
    phone VARCHAR(9) NOT NULL,
    lat FLOAT( 10, 6 ) NOT NULL,
    lng FLOAT( 10, 6 ) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE comments(
    id BINARY(16) NOT NULL,
    creator BINARY(16) NOT NULL,
    restaurant BINARY(16) NOT NULL,
    title VARCHAR(100) NOT NULL,
    comment VARCHAR(500) NOT NULL,
    response VARCHAR(500) NOT NULL,
    creation_timestamp DATETIME not null default current_timestamp,
    FOREIGN KEY (creator) REFERENCES users(id) on delete cascade,
    FOREIGN KEY (restaurant) REFERENCES restaurants(id) on delete cascade,
    PRIMARY KEY (id)
);

CREATE TABLE like_restaurant(
    restaurant BINARY(16) NOT NULL,
    user BINARY(16) NOT NULL,
    FOREIGN KEY (user) REFERENCES users(id) on delete cascade,
    FOREIGN KEY (restaurant) REFERENCES restaurants(id) on delete cascade,
    PRIMARY KEY (restaurant, user)
);


# Create user admin
insert into users (id, loginid, password, email, fullname) values (UNHEX(REPLACE(UUID(),'-','')), 'admin', UNHEX(MD5('admin')), 'admin@admin', 'I am the admin');
select @idspongebob := id from users where loginid='admin';
insert into user_roles (userid, role) values (@idspongebob, 'admin');
insert into auth_tokens (userid, token) values (@idspongebob, UNHEX(REPLACE(UUID(),'-','')));

# Create user owner --> Alex
insert into users (id, loginid, password, email, fullname) values (UNHEX(REPLACE(UUID(),'-','')), 'alex', UNHEX(MD5('alex')), 'alex@owner', 'Hi, I am Alex');
select @idalex := id from users where loginid='alex';
insert into user_roles (userid, role) values (@idalex, 'owner');
insert into auth_tokens (userid, token) values (@idalex, UNHEX(REPLACE(UUID(),'-','')));

# Create user owner --> Carolina
insert into users (id, loginid, password, email, fullname) values (UNHEX(REPLACE(UUID(),'-','')), 'carolina', UNHEX(MD5('carolina')), 'carolina@owner', 'Hi, I am Carolina');
select @idcarolina := id from users where loginid='carolina';
insert into user_roles (userid, role) values (@idcarolina, 'owner');
insert into auth_tokens (userid, token) values (@idcarolina, UNHEX(REPLACE(UUID(),'-','')));

# Create user owner --> Paulina
insert into users (id, loginid, password, email, fullname) values (UNHEX(REPLACE(UUID(),'-','')), 'paulina', UNHEX(MD5('paulina')), 'paulina@owner', 'Hi, I am Paulina');
select @idpaulina := id from users where loginid='paulina';
insert into user_roles (userid, role) values (@idpaulina, 'owner');
insert into auth_tokens (userid, token) values (@idpaulina, UNHEX(REPLACE(UUID(),'-','')));

# Create user owner --> Agnieszka
insert into users (id, loginid, password, email, fullname) values (UNHEX(REPLACE(UUID(),'-','')), 'agnieszka', UNHEX(MD5('agnieszka')), 'agnieszka@owner', 'Hi, I am Agnieszka');
select @idagnieszka := id from users where loginid='agnieszka';
insert into user_roles (userid, role) values (@idagnieszka, 'owner');
insert into auth_tokens (userid, token) values (@idagnieszka, UNHEX(REPLACE(UUID(),'-','')));
