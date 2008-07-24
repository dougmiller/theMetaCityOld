USE 'themetacitycom';

## Users 
# Populate the users table

INSERT INTO users VALUES ('dougmiller', 'e273c045363d245ffc7fb8db51a68b3571b9dd83f835b419df46b1b4412d99ecca130c4212315311441f536fc32de4de419ef045d7ea30f9bf892a4b7891b7a3', 'Douglas Miller', 'dougmiller@themetacity.com', '<p></p>');
		
## Article
# Populate the article table
INSERT INTO articles (author, title, url, date_time, article_text) VALUES ('dougmiller','This is the first post','',NOW(),'<p>This is the content of the post. Here is some more for the road.</p>');
INSERT INTO articles (author, title, url, date_time, article_text) VALUES ('dougmiller','Second LOL','',NOW(),'<p>This is the content of the post. Here is some more for the road.</p>');
INSERT INTO articles (author, title, url, date_time, article_text) VALUES ('dougmiller','How about a third','',NOW(),'<p>This is the content of the post. Here is some more for the road.</p>');
INSERT INTO articles (author, title, url, date_time, article_text) VALUES ('dougmiller','Onto four posts','',NOW(),'<p>This is the content of the post. Here is some more for the road.</p>');
INSERT INTO articles (author, title, url, date_time, article_text) VALUES ('dougmiller','Five A new record','',NOW(),'<p>This is the content of the post. Here is some more for the road.</p>');
INSERT INTO articles (author, title, url, date_time, article_text) VALUES ('dougmiller','Six. Unheard of','',NOW(),'<p>This is the content of the post. Here is some more for the road.</p>');

# Populate the articletags table
INSERT INTO articletags (id, tag) VALUES (1, 'test');
INSERT INTO articletags (id, tag) VALUES (1, 'first');
INSERT INTO articletags (id, tag) VALUES (2, 'test');
INSERT INTO articletags (id, tag) VALUES (3, 'test');
INSERT INTO articletags (id, tag) VALUES (4, 'test');
INSERT INTO articletags (id, tag) VALUES (5, 'test');
INSERT INTO articletags (id, tag) VALUES (6, 'test');

# Important notices
INSERT INTO importantnotices (username, message, date_from, date_to) VALUES ('dougmiller','<p>This is a very important message. Everyone should pay attenton to it.</p>',NOW(),NOW()+10000);