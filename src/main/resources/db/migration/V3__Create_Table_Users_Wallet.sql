CREATE TABLE users_wallet (
	id serial NOT NULL,
	wallet integer,
	users integer,
	PRIMARY KEY (id),
	FOREIGN KEY(users) REFERENCES users(id),
	FOREIGN KEY(wallet) REFERENCES wallet(id)
);