CREATE TABLE users(
	id serial NOT NULL,
	name varchar(50) NOT NULL,
	password varchar(100) NOT NULL,
	email varchar(100) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (email)
);