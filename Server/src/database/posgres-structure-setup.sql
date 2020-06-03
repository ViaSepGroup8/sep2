CREATE SCHEMA warehouse;
SET search_path to warehouse;

--Users
CREATE DOMAIN username AS VARCHAR(16);
CREATE DOMAIN password AS VARCHAR(16);
CREATE DOMAIN fullName AS VARCHAR(32);
CREATE DOMAIN role AS SMALLINT CHECK(value >=0 AND value<4);

--Products
CREATE DOMAIN product_id AS INTEGER;
CREATE DOMAIN description AS VARCHAR(128);
CREATE DOMAIN location AS VARCHAR (7);
CREATE DOMAIN price AS DECIMAL(6,2);

--Orders
CREATE DOMAIN order_id AS INTEGER;
CREATE DOMAIN gate AS VARCHAR(3);
CREATE DOMAIN delivery_address AS VARCHAR(256);
CREATE DOMAIN item_count AS SMALLINT CHECK(value>0);
CREATE DOMAIN status AS SMALLINT CHECK(value>=0 AND value<6);

--Jobs
CREATE DOMAIN job_id AS INTEGER;

--Items
CREATE DOMAIN quantity AS SMALLINT;

CREATE TABLE IF NOT EXISTS users(
	username username,
	fullName fullName,
	password password,
	role role,
	PRIMARY KEY(username)
);

CREATE TABLE IF NOT EXISTS orders(
	order_id SERIAL,
	item_count item_count,
	status status,
	gate gate,
	delivery_address delivery_address,
	customer username,
	driver username,
	PRIMARY KEY(order_id),
	FOREIGN KEY(customer) REFERENCES users(username),
	FOREIGN KEY(driver) REFERENCES users(username)
);

CREATE TABLE IF NOT EXISTS jobs(
	job_id SERIAL,
	order_id order_id,
	picker username,
	completed BOOL,
	PRIMARY KEY(job_id),
	FOREIGN KEY(order_id) REFERENCES orders(order_id),
	FOREIGN KEY(picker) REFERENCES users(username)
);

CREATE TABLE IF NOT EXISTS products(
	product_id SERIAL,
	description description,
	price price,
	location location,
	PRIMARY KEY(product_id)
);

CREATE TABLE IF NOT EXISTS items(
	product_id product_id,
	order_id order_id,
	job_id job_id,
	quantity quantity,
	FOREIGN KEY(product_id) REFERENCES products(product_id),
	FOREIGN KEY(order_id) REFERENCES orders(order_id),
	FOREIGN KEY(job_id) REFERENCES jobs(job_id)
);
