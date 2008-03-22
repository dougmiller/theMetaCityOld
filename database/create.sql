SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
-- 
-- Database: `theMetaCity`
-- 
# Uncomment the next lines if creating from scratch
# CREATE DATABASE `themetacity` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
# USE `themetacity`;

-- --------------------------------------------------------

--
-- Create users with specific privileges.
-- The create user line is not necessary but is there for completness
--

-- A pseudoroot for special needs
-- GRANT is not included in ALL for security reasons.
CREATE USER tmcRoot;
GRANT ALL ON theMetaCity.* TO 'tmcRoot'@'%' IDENTIFIED BY PASSWORD '*AABD2FA4187FD3CE56D5592E116CA3A39BE3D86F';

-- A user for selecting data only. Extra safety net.
CREATE USER tmcSelector;
GRANT SELECT ON theMetaCity.* TO 'tmcSelector'@'%' IDENTIFIED BY 'mypass';

-- A user for undating and inserting records.
CREATE USER tmcAdmin;
GRANT SELECT, INSERT, UPDATE, DELETE ON theMetaCity.* TO 'tmcAdmin'@'%' IDENTIFIED BY PASSWORD '*AABD2FA4187FD3CE56D5592E116CA3A39BE3D86F';

-- 
-- Table structure for table `articles`
-- 

CREATE TABLE `articles` (
  `articleid` smallint(6) NOT NULL auto_increment COMMENT 'The ID used for indexing and ordering',
  `author` varchar(30) NOT NULL COMMENT 'The author who posed this article. References users.username',
  `title` varchar(100) NOT NULL COMMENT 'The title of this article',
  `articletext` varchar(5000) NOT NULL COMMENT 'The actual content of the article.',
  `datetime` date NOT NULL COMMENT 'The date this article was published',
  PRIMARY KEY  (`articleid`),
  KEY `author` (`author`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Article information. Autoincrement articleid.' AUTO_INCREMENT=1 ;

-- 
-- Table structure for table `articletags`
-- 

CREATE TABLE `articletags` (
  `articleid` smallint(6) NOT NULL COMMENT 'References an article by articleid',
  `tag` varchar(20) NOT NULL COMMENT 'The text tag',
  PRIMARY KEY  (`articleid`,`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='The tags describing the articles';

-- 
-- Table structure for table `users`
-- 

CREATE TABLE `users` (
  `username` varchar(30) NOT NULL COMMENT 'The users username, used for loggining in etc and for references',
  `password` varchar(64) NOT NULL COMMENT 'The users password. Stored and an SHA-512 hash.',
  `pseudonym` varchar(25) NOT NULL COMMENT 'The users pseudonym that is displayed instead of the username',
  `contact` varchar(40) NOT NULL COMMENT 'The email/website users wish to be contacted at.',
  `about` varchar(5000) NOT NULL COMMENT 'The about text of a user.',
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Information about users. Passwords, email etc.';

# These come last to make sure that all tables exist before making references to them.
-- 
-- Constraints for table `articles`
-- 
ALTER TABLE `articles`
  ADD CONSTRAINT `articles_fk` FOREIGN KEY (`author`) REFERENCES `users` (`username`) ON UPDATE CASCADE;

-- 
-- Constraints for table `articletags`
-- 
ALTER TABLE `articletags`
  ADD CONSTRAINT `articletags_fk` FOREIGN KEY (`articleid`) REFERENCES `articles` (`articleid`) ON DELETE CASCADE ON UPDATE CASCADE;
