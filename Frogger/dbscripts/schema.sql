create table users (
	id IDENTITY,
	username VARCHAR(20) NOT NULL,
	password VARCHAR(40) NOT NULL,
	max_level INTEGER NOT NULL
);