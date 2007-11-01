SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

-- 
-- Database: `theMetaCity`
-- 
CREATE DATABASE `themetacity` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `themetacity`;

-- --------------------------------------------------------

-- 
-- Table structure for table `articles`
-- 

CREATE TABLE `articles` (
  `articleid` smallint(6) NOT NULL auto_increment COMMENT 'The ID used for indexing and ordering',
  `title` varchar(50) NOT NULL COMMENT 'The title of this article',
  `datetime` date NOT NULL COMMENT 'The date this article was published',
  `author` varchar(15) NOT NULL COMMENT 'The author who posed this article. References users.username',
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

-- 
-- Dumping data for table `users`
-- 

INSERT INTO `users` VALUES ('dmg', 'somepassword', 'Dingo McGraw', 'dmg@themetacity.com', '<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Vivamus pellentesque congue odio. Donec elementum volutpat nisi. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Fusce mauris orci, tristique ac, facilisis nec, fermentum eu, tortor. Duis commodo massa rutrum libero. Morbi id risus. Duis bibendum. Maecenas nisi. Proin consectetuer viverra quam. Phasellus facilisis sapien eu pede. Curabitur ante. Fusce at sapien ut nulla porta molestie. Integer ac elit venenatis quam ultricies placerat. Mauris a dolor. Fusce at ligula ac lacus gravida ultricies. Cras suscipit lacus vitae ipsum. Ut lectus sem, imperdiet ut, scelerisque sed, ullamcorper et, enim.</p>\r\n\r\n<p>Donec et justo. Pellentesque vitae dui a nulla lacinia bibendum. Mauris ut leo. Aenean libero risus, euismod vel, facilisis vel, eleifend ac, diam. Sed in sapien. Etiam odio libero, sagittis sit amet, feugiat in, condimentum eu, erat. Cras id tellus. Fusce sed metus. Nunc nibh. Duis metus ligula, tincidunt in, bibendum et, ultricies quis, urna. Nam id lorem et lacus sodales posuere. Vivamus sit amet nisl. Aenean erat tortor, rutrum eget, sagittis et, egestas vitae, nibh. Maecenas sit amet leo. Pellentesque euismod nibh nec neque. Phasellus libero mauris, faucibus vel, placerat sed, volutpat a, pede. Cras velit lectus, malesuada sed, hendrerit id, iaculis in, mauris. Nunc euismod. Aliquam malesuada, nunc eget lacinia egestas, sapien massa dictum enim, dictum mollis felis lectus ac mauris.</p>');
INSERT INTO `users` VALUES ('fredface', 'someotherpassword', 'Fred Face', 'ff@themetacity.com', '<p>Pellentesque et nisi in erat vehicula auctor. Vivamus cursus. Suspendisse ac nisi nec lorem nonummy varius. Maecenas tellus pede, aliquet vitae, lacinia ut, tincidunt vitae, turpis. Praesent fringilla elit in nibh. Pellentesque tincidunt enim at tellus. Praesent at ligula. Morbi augue. Proin sollicitudin eleifend ante. In orci ligula, sagittis ac, accumsan ac, vehicula vitae, ante. Sed at ipsum in nunc luctus lacinia. In hac habitasse platea dictumst.</p>\r\n\r\n<p>Maecenas tincidunt ante nec metus. Pellentesque nec erat. Duis urna tortor, commodo at, aliquet at, condimentum ac, mi. Quisque aliquam lorem quis felis. In vehicula orci eu augue consequat dictum. Nunc porttitor feugiat velit. Pellentesque elementum venenatis metus. Aliquam id diam at diam luctus eleifend. Praesent eget metus. Phasellus neque ipsum, laoreet congue, ultrices ac, rutrum vitae, ipsum. Nam fermentum. Sed vulputate, turpis sed tempor viverra, orci est tristique nisi, non venenatis pede sem a magna. Nullam id justo. Nam ipsum turpis, sodales vel, imperdiet vitae, aliquam at, dui.</p>');

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
