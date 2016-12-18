create table "items" (
	"id"			integer			primary key		autoincrement,
	"item_no"		text			not null,
	"name"			text			not null,
	"description"	text			not null,
	"unit"			text			not null,
	"in_stock"		real			not null
);