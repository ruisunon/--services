
CREATE TABLE verification_tokens (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	identifier VARCHAR(255) NOT NULL DEFAULT RANDOM_UUID(),
	token VARCHAR(255),
	expire_date TIMESTAMP,
	credential_id INT NOT NULL,
	created_at TIMESTAMP DEFAULT LOCALTIMESTAMP NOT NULL NULL_TO_DEFAULT,
	updated_at TIMESTAMP DEFAULT LOCALTIMESTAMP NOT NULL NULL_TO_DEFAULT
);



