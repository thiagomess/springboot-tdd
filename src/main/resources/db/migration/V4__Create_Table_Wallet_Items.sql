CREATE TABLE wallet_items (
	id bigint(20) NOT NULL AUTO_INCREMENT,
	wallet bigint(20),
	date date,
	type varchar(2),
	description varchar(500),
	value numeric(10,2),
	PRIMARY KEY (id),
	FOREIGN KEY(wallet) REFERENCES wallet(id)
);