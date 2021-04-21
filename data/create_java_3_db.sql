drop database if exists java_3_db;
create database java_3_db;

use java_3_db;

drop table if exists insurance_policies;
drop table if exists insured_members;
drop table if exists companies;

create table companies (
id int(9) unsigned auto_increment primary key,
name varchar(255) not null
);

create table insured_members (
id int(9) unsigned auto_increment primary key,
first_name varchar(255) not null,
last_name varchar(255) not null,
company_id int(9) unsigned not null
);

create table insurance_policies (
id int(9) unsigned auto_increment primary key,
company_id int(9) unsigned not null,
type varchar(30) not null
);

alter table insured_members add foreign key (company_id) references companies(id);
alter table insurance_policies add foreign key (company_id) references companies(id);

-- companies
insert into companies (name) values
   ('Book and Cookie'),
   ('Winter\'s Wonderland'),
   ('Bouncy Castle Playland');

-- policies
insert into insurance_policies (company_id, type)
values (1, 'dental'),
       (1, 'life'),
       (1, 'vision'),
       (2, 'dental'),
       (2, 'life'),
       (3, 'vision');

-- members
insert into insured_members (company_id, first_name, last_name)
values (1, 'Sarah', 'Gilbert'),
       (1, 'Lacy', 'Dickson'),
       (1, 'Deloras', 'Durgan'),
       (2, 'Bibi', 'Hills'),
       (2, 'Shad', 'Toy'),
       (3, 'Everett', 'Morrison'),
       (3, 'Anton', 'Wolff');
