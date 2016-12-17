create table "items" (
	"id"			integer			not null		primary key		autoincrement,
	"item_no"		text			not null,
	"name"			text			not null,
	"description"	text			not null,
	"unit"			text			not null,
	"in_stock"		integer			not null
);