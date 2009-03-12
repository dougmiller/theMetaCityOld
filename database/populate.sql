USE 'themetacity';

-- Users 
-- Populate the users table

INSERT INTO users VALUES ('dougmiller', 'e273c045363d245ffc7fb8db51a68b3571b9dd83f835b419df46b1b4412d99ecca130c4212315311441f536fc32de4de419ef045d7ea30f9bf892a4b7891b7a3', 'Douglas Miller', 'dougmiller@themetacity.com', '<p></p>');
		
-- Article
-- Populate the article table
INSERT INTO articles (author, title, url, date_time, article_text) VALUES ('dougmiller','First Post LOL!!!','First-Post-LOL',NOW(),'
<p>Welcome to yet another site on the interwebz. My name is Douglas Miller. I am an <a href="http://en.wikipedia.org/wiki/Interaction_design">Interaction Designer</a> from Brisbane, Australia.</p>
<p>I use this place as a scrapbook for my projects, ideas, thoughts and reviews.</p>
<p>I muck about with a couple of different languages, talk to and about sometimes interesting people, make bigoted comments on events and issues and generally spew forth whatever is in my brain when I am able to sit down for more than five minutes in one stretch.  Fun for the whole family.</p>
<p>If you want to know more then (and honestly, who wouldn’t?) <a href="/about">go see my about page</a>.</p>
<p>Is this serious work? Sometimes. Is it helpful? Maybe. Is it coarse, condescending, irreverent, ignorant and pointless? Defiantly!</p>
');

INSERT INTO articles (author, title, url, date_time, article_text) VALUES ('dougmiller','Tomcat Guide','Tomcat-Guide',NOW()+1,'
<p>This is a rough guide to setting up and deploying a non-trivial web application on Apache Tomcat using Java. I am doing this because I believe almost all of the current examples on the Internet suck. They are trivial, for the most part outdated and often wrong.</p>
<p>The application that I will be making is the very one that you are using at the moment: a *shudder* blog-thingy.</p>
<p>This guide is designed for people that are experienced with unix (file system, cli, permissions etc), Java (or some other language and are learning), are happy to read and interpret error messages. I try to explain things as best I can without being condescending. Well more than usual.</p>
<h2>Table of Contents</h2>
<p>This is an ongoing project and gets updated when it does.</p>
<ol>
    <li>Setup tomcat 5.5 and 6
        <ul>
            <li>Virtual Hosts
                <ul>
                    <li>Deployment structure</li>
                    <li>web.xml</li>
                    <li>Context</li>
                </ul>
            </li>
            <li>Difference in versions</li>
        </ul>
    </li>
</ol>
');

-- Populate the articletags table
INSERT INTO articletags (id, tag) VALUES (1, 'administration');
INSERT INTO articletags (id, tag) VALUES (2, 'tomcat');
INSERT INTO articletags (id, tag) VALUES (2, 'guide');
INSERT INTO articletags (id, tag) VALUES (2, 'java');

-- Populate the projects database
INSERT INTO projects (URL, desc_text, is_valid) VALUES ('Tomcat-Guide', 'Tomcat Guide', 1);

-- Important notices 
-- Nothing for default