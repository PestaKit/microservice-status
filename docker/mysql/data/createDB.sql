DROP DATABASE IF EXISTS status;

CREATE DATABASE status;

USE status;

CREATE TABLE service_entity (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(64) NOT NULL,
	state VARCHAR(64),
	description VARCHAR(128),
	contact VARCHAR(64),
	status_address VARCHAR(256)
);
