CREATE TABLE users(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	name varchar(50) NOT NULL,
	password varchar(100) NOT NULL,
	email varchar(100) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (email)
);