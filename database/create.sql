SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
-- 
-- Database: `theMetaCity`
-- 

--DROP DATABASE `themetacity`;
-- Uncomment the next lines if creating from scratch
--CREATE DATABASE `themetacity` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
--USE `themetacity`;

-- --------------------------------------------------------

--
-- Create users with specific privileges.
-- The create user line is not necessary but is there for completness
--

-- A pseudoroot for special needs
-- GRANT is not included in ALL for security reasons. -> You cant grant yoursef as ROOT
-- If you do not have permission on the security table then you will need to log in manually to set the password

-- Dont really need him for now
--CREATE USER tmcRoot;
--GRANT ALL ON theMetaCity.* TO 'tmcRoot'@'%' IDENTIFIED BY PASSWORD '*AABD2FA4187FD3CE56D5592E116CA3A39BE3D86F';

-- A user for selecting data only. Extra safety net.
-- CREATE USER tmcSelector;
--GRANT SELECT ON themetacity.* TO 'tmcSelector'@'localhost' IDENTIFIED BY PASSWORD '*9AF6DB6DC277180622AC3BD28DD928DE5B8CBD3F';
-- A user for updating and inserting records.
-- CREATE USER tmcAdmin;
--GRANT SELECT, INSERT, UPDATE, DELETE ON themetacity.* TO 'tmcAdmin'@'localhost' IDENTIFIED BY PASSWORD '*AABD2FA4187FD3CE56D5592E116CA3A39BE3D86F';

-- 
-- Table structure for table `articles`
-- 

CREATE TABLE `articles` (
  `id` smallint(6) NOT NULL auto_increment COMMENT 'The ID used for indexing and ordering',
  `author` varchar(30) NOT NULL COMMENT 'The author who posed this article. References users.username',
  `title` varchar(100) NOT NULL COMMENT 'The title of this article',
  `url` varchar(100) NOT NULL COMMENT 'The urls for this post. Destructively generated from the title using a strip function.',
  `article_text` text(50000) NOT NULL COMMENT 'The actual content of the article.',
  `date_time` datetime NOT NULL COMMENT 'The date this article was published',
  `timestamp` timestamp NOT NULL COMMENT 'Auto timestamp',
  PRIMARY KEY  (`id`),
  KEY `author` (`author`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Article information. Autoincrement articleid.' AUTO_INCREMENT=1;

-- 
-- Table structure for table `articletags`
-- 

CREATE TABLE `articletags` (
  `id` smallint(6) NOT NULL COMMENT 'References an article by articleid',
  `tag` varchar(20) NOT NULL COMMENT 'The text tag',
  PRIMARY KEY  (`id`,`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='The tags describing the articles';

-- 
-- Table structure for table `users`
-- 

CREATE TABLE `users` (
  `username` varchar(30) NOT NULL COMMENT 'The users username, used for loggining in etc and for references',
  `password` varchar(128) NOT NULL COMMENT 'The users password. Stored as an SHA-512 hash. the salt is the SHA-512 of the username)',
  `pseudonym` varchar(25) NOT NULL COMMENT 'The users pseudonym that is displayed instead of the username',
  `contact` varchar(40) NOT NULL COMMENT 'The email/website users wish to be contacted at.',
  `about` varchar(20000) NOT NULL COMMENT 'The about text of a user.',
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Information about users. Passwords, email etc.';

-- 
-- Table structure for table `importantNotices`
-- 

CREATE TABLE `importantnotices` (
  `id` smallint(6) NOT NULL auto_increment COMMENT 'The ID used for indexing and ordering',
  `username` varchar(30) NOT NULL COMMENT 'The user who this message relates to.',
  `message` varchar(500) NOT NULL COMMENT 'The actual important message.',
  `date_from` datetime NOT NULL COMMENT 'The date this notice starts from.',
  `date_to` datetime NOT NULL COMMENT 'The date this notice finished.',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Important notices' AUTO_INCREMENT=1;

--
--
--
CREATE TABLE `workshop` (
  `id` smallint(6) NOT NULL auto_increment COMMENT 'The ID used for indexing and ordering',
  `URL` varchar(150) NOT NULL COMMENT 'The title',
  `desc_text` varchar(150) NOT NULL COMMENT '',
  `is_valid` boolean NOT NULL COMMENT 'Wether or not this is approved context.',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Sidebar Projects' AUTO_INCREMENT=1;

--
-- 
--
CREATE TABLE `projects` (
  `id` smallint(6) NOT NULL auto_increment COMMENT 'The ID used for indexing and ordering',
  `URL` varchar(150) NOT NULL COMMENT 'The URL to link to',
  `desc_text` varchar(150) NOT NULL COMMENT 'Desc text that will show up that user click on',
  `is_valid` boolean NOT NULL COMMENT 'Wether or not this is approved context.',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Sidebar Projects' AUTO_INCREMENT=1;

-- These come last to make sure that all tables exist before making references to them.
-- 
-- Constraints for table `articles`
-- 
ALTER TABLE `articles`
  ADD CONSTRAINT `articles_fk` FOREIGN KEY (`author`) REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

-- 
-- Constraints for table `articleTags`
-- 
ALTER TABLE `articletags`
  ADD CONSTRAINT `articletags_fk` FOREIGN KEY (`id`) REFERENCES `articles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `importantNotices`
--
ALTER TABLE `importantnotices`
  ADD CONSTRAINT `users_fk` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;
