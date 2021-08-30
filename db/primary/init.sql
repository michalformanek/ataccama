DROP DATABASE IF EXISTS `ataccama`;
CREATE DATABASE `ataccama` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `ataccama`;

DROP TABLE IF EXISTS `tenants`;
CREATE TABLE `tenants` (
    `name` varchar(255) NOT NULL,
    `database_name` varchar(255) NOT NULL,
    `hostname` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `port` varchar(255) NOT NULL,
    `username` varchar(255) NOT NULL,
     PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `tenants` (`name`, `database_name`, `hostname`, `password`, `port`, `username`) VALUES
    ('mariadb-1',	'test',	'localhost',	'root',	'root',	'3307');
-- 2021-08-30 20:31:19