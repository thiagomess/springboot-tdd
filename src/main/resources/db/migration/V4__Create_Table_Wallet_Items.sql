CREATE TABLE wallet_items (
	id serial NOT NULL,
	wallet integer,
	date date,
	type varchar(2),
	description varchar(500),
	value numeric(10,2),
	PRIMARY KEY (id),
	FOREIGN KEY(wallet) REFERENCES wallet(id)
);