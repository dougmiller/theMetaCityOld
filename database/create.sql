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

CREATE SEQUENCE article_id_seq;
CREATE TABLE articles (
  id int UNIQUE NOT NULL DEFAULT nextval('article_id_seq'),
  author varchar(30) NOT NULL,
  title varchar(100) NOT NULL,
  url varchar(100) NOT NULL,
  article_text text NOT NULL,
  date_created timestamp NOT NULL DEFAULT now(),
  date_modified timestamp NOT NULL DEFAULT now(),
  PRIMARY KEY (id)
);

-- 
-- Table structure for table "articletags"
-- 
CREATE SEQUENCE articletags_id_seq;
CREATE TABLE articletags (
  id int UNIQUE NOT NULL DEFAULT nextval('articletags_id_seq'),
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
CREATE SEQUENCE importantnotices_id_seq;
CREATE TABLE importantnotices (
  id int UNIQUE NOT NULL DEFAULT nextval('importantnotices_id_seq'),
  username varchar(30) NOT NULL,
  message varchar(500) NOT NULL,
  date_from timestamp NOT NULL,
  date_to timestamp NOT NULL,
  PRIMARY KEY (id)
);

--
-- An entry in the workshop, both the small and large view
--
CREATE SEQUENCE workshop_id_seq;
CREATE TABLE workshop (
  id int UNIQUE NOT NULL DEFAULT nextval('workshop_id_seq'),
  author varchar(30) NOT NULL,
  title varchar(100) NOT NULL,
  blurb text NOT NULL,
  article_text text NOT NULL,
  date_created timestamp NOT NULL DEFAULT now(),
  date_modified timestamp NOT NULL DEFAULT now(),
  PRIMARY KEY (id)
);

-- 
-- Table structure for table "articletags"
--
CREATE SEQUENCE workshoptags_id_seq;
CREATE TABLE workshoptags (
  id int NOT NULL DEFAULT nextval('workshoptags_id_seq'),
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
  

--
-- Automatically update the modified timestamps on update (all tables that use the modified timestamp)
--

CREATE FUNCTION update_modified_timestamp_to_now()
RETURNS TRIGGER AS $$
BEGIN
   NEW.changetimestamp = now(); 
   RETURN NEW;
END;
$$ language 'plpgsql';

-- Articles table
CREATE TRIGGER update_articles_modified_timestamp_to_now BEFORE UPDATE
ON articles FOR EACH ROW EXECUTE PROCEDURE 
update_modified_timestamp_to_now();

-- Workshop table
CREATE TRIGGER update_workshop_modified_timestamp_to_now BEFORE UPDATE
ON workshop FOR EACH ROW EXECUTE PROCEDURE 
update_modified_timestamp_to_now();

-- Give users their permissions
GRANT SELECT ON articles, articletags, workshop, workshoptags, importantnotices, users  TO tmcSelector;
