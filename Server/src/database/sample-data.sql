INSERT INTO users VALUES('lenka', 'Lenka Orincakova', 'lenka10', '0');
INSERT INTO users VALUES('javi', 'Javier Candeira', 'javi10', '2');
insert into users values('admin','system_user','pass',0);
insert into users values('customer','system_user','pass',1);
insert into users values('picker','system_user','pass',2);
insert into users values('driver','system_user','pass',3);

INSERT INTO orders VALUES(DEFAULT,1,0, '33C', 'Calle Serrano', 'lenka', 'javi');
--ID
--ITEM COUNT
--STATUS

INSERT INTO jobs VALUES( DEFAULT, 1, NULL,'false');
--ID
--order id
--assigend picker
--completed


INSERT INTO products VALUES(DEFAULT, 'apples in a big very very big box', 72.99,'43A0302');
--	product_id SERIAL,
--	description description,
--	price price,
--	location location,
--	PRIMARY KEY(product_id)

--DELETE FROM users WHERE users.username = 'lenka';