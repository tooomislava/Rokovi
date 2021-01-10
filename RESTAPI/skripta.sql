create database p3db default character set utf8mb4;
use tzilic_20;
create table rokovi(
    sifra int not null primary key auto_increment,
    datum date not null,
    kolegij varchar(255) not null,
    brojPrijavljenih int not null
);
insert into rokovi(datum,kolegij,brojPrijavljenih)
values ('2020-01-05','P3', 20);
