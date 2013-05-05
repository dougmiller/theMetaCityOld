-- Drop the database all at once to make it easy
-- DROP DATABASE IF EXISTS  "themetacitycom";

--
-- OR if no permission to create database
--


\c "themetacitycom";

DROP TRIGGER IF EXISTS update_articles_modified_date_to_now ON articles;
DROP TRIGGER IF EXISTS update_workshop_modified_date_to_now ON workshop;

DROP TABLE IF EXISTS importantnotices;
DROP TABLE IF EXISTS workshoptags;
DROP TABLE IF EXISTS workshop;
DROP TABLE IF EXISTS articletags;
DROP TABLE IF EXISTS articles;

DROP SEQUENCE IF EXISTS article_id_seq;
DROP SEQUENCE IF EXISTS importantnotices_id_seq;
DROP SEQUENCE IF EXISTS workshop_id_seq;

DROP TYPE projectstatus;

DROP FUNCTION update_modified_date_to_now();



-- DROP USER tmcRoot;
DROP USER tmcSelector;
-- DROP USER tmcAdmin;
