-- user テーブル定義
CREATE TABLE user (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    birthday DATE NOT NULL,
    CONSTRAINT PRIMARY KEY (id)
);
