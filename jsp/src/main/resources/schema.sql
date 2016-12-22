create table "items" (
	"id"			integer			primary key		autoincrement,
	"item_no"		text			not null,
	"name"			text			not null,
	"description"	text			not null,
	"unit"			text			not null,
	"price"			real			not null,
	"in_stock"		real			not null
);


create table "orders" (
	"id"			integer			primary key		autoincrement,
	"name"			text			not null
);


create table "order_items" (
	"id"			integer			primary key		autoincrement,
	"order_id"		integer			not null,
	"item_no"		text			null,
	"name"			text			null,
	"unit"			text			null,
	"price"			real			null,
	"quantity"		real			not null,
	
	foreign key ("order_id") references "orders" ("id")
);
