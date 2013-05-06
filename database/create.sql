-- 
-- Database: "themetacitycom"
-- 

-- Create only. Clean is in another file (clean.sql).

-- CREATE DATABASE "themetacitycom";

--  Connect to the newly created database
-- \c "themetacitycom";

-- -------------------------
-- -------------------------

--
-- Create users with specific privileges.
-- The create user line is not necessary but is there for completeness
--

-- A pseudoroot for special needs
-- GRANT is not included in ALL for security reasons. -> You cant grant yourself as ROOT
-- If you do not have permission on the security table then you will need to log in manually to set the password

-- Don't really need him for now
-- CREATE USER tmcRoot;
-- GRANT ALL ON themetacitycom.* TO "tmcRoot"@"%" IDENTIFIED BY PASSWORD "*AABD2FA4187FD3CE56D5592E116CA3A39BE3D86F";

-- A user for selecting data only. Extra safety net.
CREATE USER tmcselector;

-- A user for updating and inserting records.
-- CREATE USER tmcAdmin;
-- GRANT SELECT, INSERT, UPDATE, DELETE ON themetacitycom.* TO "tmcAdmin"@"localhost" IDENTIFIED BY PASSWORD "*AABD2FA4187FD3CE56D5592E116CA3A39BE3D86F";

-- 
-- Table structure for table "articles"
--
CREATE SEQUENCE article_id_seq;
CREATE TABLE articles (
  id int UNIQUE NOT NULL DEFAULT nextval('article_id_seq'),
  title varchar(100) NOT NULL,
  url varchar(100) NOT NULL,
  article_text text NOT NULL,
  date_created date NOT NULL DEFAULT current_date,
  date_modified date NOT NULL DEFAULT current_date,
  PRIMARY KEY (id)
);

-- 
-- Table structure for table "articletags"
-- 
CREATE TABLE articletags (
  id int NOT NULL,
  tag varchar(20) NOT NULL,
  PRIMARY KEY (id, tag)
);

-- 
-- Table structure for table "importantNotices"
-- 
CREATE SEQUENCE importantnotices_id_seq;
CREATE TABLE importantnotices (
  id int UNIQUE NOT NULL DEFAULT nextval('importantnotices_id_seq'),
  message varchar(500) NOT NULL,
  date_from date NOT NULL DEFAULT current_date,
  date_to date NOT NULL DEFAULT current_date + 7,     -- 7 days
  PRIMARY KEY (id)
);

--
-- An entry in the workshop, both the small and large view
--
CREATE SEQUENCE workshop_id_seq;
CREATE TABLE workshop (
  id int UNIQUE NOT NULL DEFAULT nextval('workshop_id_seq'),
  title varchar(100) NOT NULL,
  blurb text NOT NULL,
  article_text text NOT NULL,
  complete BOOLEAN NULL DEFAULT FALSE,
  date_created date NOT NULL DEFAULT current_date,
  date_modified date NOT NULL DEFAULT current_date,
  PRIMARY KEY (id)
);

-- 
-- Table structure for table "articletags"
--
CREATE TABLE workshoptags (
  id int NOT NULL,
  tag varchar(20) NOT NULL,
  PRIMARY KEY  (id, tag)
);

-- These foreign keys come last to make sure that all tables exist before making references to them.
--
-- 
-- Constraints for table "articleTags"
-- 
ALTER TABLE articletags
  ADD CONSTRAINT "articletags_fk" FOREIGN KEY ("id") REFERENCES "articles" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- 
-- Constraints for table "workshoptags"
-- 
ALTER TABLE workshoptags
  ADD CONSTRAINT "workshoptags_fk" FOREIGN KEY ("id") REFERENCES "workshop" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Automatically update the modified date on update (all tables that use the modified date)
--
CREATE FUNCTION update_modified_date_to_now()
  RETURNS TRIGGER AS $$
BEGIN
  NEW.date_modified := current_date;
  RETURN NEW;
END;
$$ language 'plpgsql';

-- Articles table
CREATE TRIGGER update_articles_modified_date_to_now AFTER UPDATE
ON articles FOR EACH ROW EXECUTE PROCEDURE 
  update_modified_date_to_now();

-- Workshop table
CREATE TRIGGER update_workshop_modified_date_to_now AFTER UPDATE
ON workshop FOR EACH ROW EXECUTE PROCEDURE 
  update_modified_date_to_now();

-- Give users their permissions
GRANT SELECT ON articles, articletags, workshop, workshoptags, importantnotices TO tmcselector;
ALTER USER tmcselector WITH CONNECTION LIMIT 10;
