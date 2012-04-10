-- 
-- Database: "themetacitycom"
-- 

DROP DATABASE IF EXISTS  "themetacitycom";

CREATE DATABASE "themetacitycom";
DROP USER tmcSelector;
--  Connect to the newly created database
\c "themetacitycom";

-- --------------------------------------------------------

--
-- Create users with specific privileges.
-- The create user line is not necessary but is there for completness
--

-- A pseudoroot for special needs
-- GRANT is not included in ALL for security reasons. -> You cant grant yourself as ROOT
-- If you do not have permission on the security table then you will need to log in manually to set the password

-- Dont really need him for now
-- CREATE USER tmcRoot;
-- GRANT ALL ON themetacitycom.* TO "tmcRoot"@"%" IDENTIFIED BY PASSWORD "*AABD2FA4187FD3CE56D5592E116CA3A39BE3D86F";

-- A user for selecting data only. Extra safety net.
CREATE USER tmcSelector;
-- A user for updating and inserting records.
-- CREATE USER tmcAdmin;
-- GRANT SELECT, INSERT, UPDATE, DELETE ON themetacitycom.* TO "tmcAdmin"@"localhost" IDENTIFIED BY PASSWORD "*AABD2FA4187FD3CE56D5592E116CA3A39BE3D86F";

-- 
-- Table structure for table "articles"
-- 

CREATE TABLE articles (
  id int UNIQUE NOT NULL,
  author varchar(30) NOT NULL,
  title varchar(100) NOT NULL,
  url varchar(100) NOT NULL,
  article_text text NOT NULL,
  date_time timestamp NOT NULL,
  timestamp timestamp NOT NULL,
  PRIMARY KEY (id)
);

-- 
-- Table structure for table "articletags"
-- 

CREATE TABLE articletags (
  id int UNIQUE NOT NULL,
  tag varchar(20) UNIQUE NOT NULL,
  PRIMARY KEY (id, tag)
);

-- 
-- Table structure for table "users"
-- 

CREATE TABLE users (
  username varchar(30) UNIQUE NOT NULL,
  password varchar(128) NOT NULL,
  pseudonym varchar(25) NOT NULL,
  contact varchar(40) NOT NULL,
  about text NOT NULL,
  PRIMARY KEY (username)
);

-- 
-- Table structure for table "importantNotices"
-- 

CREATE TABLE importantnotices (
  id int UNIQUE NOT NULL,
  username varchar(30) NOT NULL,
  message varchar(500) NOT NULL,
  date_from timestamp NOT NULL,
  date_to timestamp NOT NULL,
  PRIMARY KEY (id)
);

--
-- An entry in the workshop, both the small and large view
--
CREATE TABLE workshop (
  id int UNIQUE NOT NULL,
  author varchar(30) NOT NULL,
  title varchar(100) NOT NULL,
  blurb text NOT NULL,
  article_text text NOT NULL,
  date_time timestamp NOT NULL,
  timestamp timestamp NOT NULL,
  PRIMARY KEY (id)
);

-- 
-- Table structure for table "articletags"
-- 
CREATE TABLE workshoptags (
  id int NOT NULL,
  tag char(20) NOT NULL,
  PRIMARY KEY  (id, tag)
);

-- These foreign keys come last to make sure that all tables exist before making references to them.
-- 
-- Constraints for table "articles"
-- 
ALTER TABLE articles
  ADD CONSTRAINT "articles_fk" FOREIGN KEY ("author") REFERENCES "users" ("username") ON DELETE CASCADE ON UPDATE CASCADE;

-- 
-- Constraints for table "articleTags"
-- 
ALTER TABLE articletags
  ADD CONSTRAINT "articletags_fk" FOREIGN KEY ("id") REFERENCES "articles" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- 
-- Constraints for table "workshop"
-- 
ALTER TABLE workshop
  ADD CONSTRAINT "workshop_fk" FOREIGN KEY ("author") REFERENCES "users" ("username") ON DELETE CASCADE ON UPDATE CASCADE;

-- 
-- Constraints for table "workshoptags"
-- 
ALTER TABLE workshoptags
  ADD CONSTRAINT "workshoptags_fk" FOREIGN KEY ("id") REFERENCES "workshop" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table "importantNotices"
--
ALTER TABLE importantnotices
  ADD CONSTRAINT "users_fk" FOREIGN KEY ("username") REFERENCES "users" ("username") ON DELETE CASCADE ON UPDATE CASCADE;
  
  
  
  
GRANT SELECT ON ALL TABLES IN SCHEMA public TO public;
