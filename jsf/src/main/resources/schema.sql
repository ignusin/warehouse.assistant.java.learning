create table "items" (
	"id"			serial				primary key,
	"item_no"		varchar(100)		not null,
	"name"			varchar(200)		not null,
	"description"	text				not null,
	"unit"			varchar(100)		not null,
	"price"			decimal(10, 2)		not null,
	"in_stock"		decimal(10, 2)		not null
);


create table "orders" (
	"id"			serial				primary key,
	"name"			varchar(100)		not null
);


create table "order_items" (
	"id"			serial				primary key,
	"order_id"		int					not null,
	"item_id"		int					not null,
	"item_no"		varchar(100)		not null,
	"name"			varchar(200)		not null,
	"unit"			varchar(100)		not null,
	"price"			decimal(10, 2)		not null,
	"quantity"		decimal(10, 2)		not null,
	
	foreign key ("order_id") references "orders" ("id")
);
