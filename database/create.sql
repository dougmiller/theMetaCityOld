SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

-- 
-- Database: `theMetaCity`
-- 
# Uncomment the next two lines if creating from scratch
# CREATE DATABASE `themetacity` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
# USE `themetacity`;

-- --------------------------------------------------------

-- 
-- Table structure for table `articles`
-- 

CREATE TABLE `articles` (
  `articleid` smallint(6) NOT NULL auto_increment COMMENT 'The ID used for indexing and ordering',
  `title` varchar(50) NOT NULL COMMENT 'The title of this article',
  `datetime` date NOT NULL COMMENT 'The date this article was published',
  `author` varchar(15) NOT NULL COMMENT 'The author who posed this article. References users.username',
  'text' varchar(5000) NOT NULL COMMENT 'The actual content of the article.',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='The tags describing the article';

-- 
-- Table structure for table `users`
-- 

CREATE TABLE `users` (
  `username` varchar(15) NOT NULL COMMENT 'The users username, used for loggining in etc and for references',
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
