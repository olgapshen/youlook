drop database if exists youlook;
create database youlook;

use youlook;

create table `strings`(
	`id` mediumint not null auto_increment,
	`string` varchar(255),
	`count` mediumint,
	primary key (`id`)
);
