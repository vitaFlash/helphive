-- Minimal demo seed
CREATE TABLE IF NOT EXISTS hello (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  msg VARCHAR(100) NOT NULL
);

INSERT INTO hello (msg) VALUES ('Hello, HelpHive!');
