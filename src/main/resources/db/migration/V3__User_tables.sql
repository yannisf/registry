CREATE TABLE users (
  username varchar(250) NOT NULL,
  password varchar(250) NOT NULL,
  enabled bool NOT NULL,
  CONSTRAINT users_pkey PRIMARY KEY (username)
);

CREATE TABLE authorities (
  username varchar(250) NOT NULL,
  authority varchar(250) NOT NULL,
  CONSTRAINT authorities_pkey PRIMARY KEY (username, authority),
  CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);

INSERT INTO users(username, password, enabled) VALUES ('user', '$2a$10$c/rhkeAoE1lFKIY8lDoJbunPfAZ2KDhjSyghvT5J0N8Mtospnkbha', TRUE);
INSERT INTO authorities(username, authority) VALUES ('user', 'USER');

