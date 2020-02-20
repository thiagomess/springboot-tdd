CREATE TABLE users_wallet (
id bigint(20) NOT NULL AUTO_INCREMENT,
	wallet bigint(20),
	users bigint(20),
	PRIMARY KEY (id),
	FOREIGN KEY(users) REFERENCES users(id),
	FOREIGN KEY(wallet) REFERENCES wallet(id)
);