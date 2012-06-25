\c 'themetacitycom';

-- Users 
-- Populate the users table

INSERT INTO users VALUES ('dougmiller', 'e273c045363d245ffc7fb8db51a68b3571b9dd83f835b419df46b1b4412d99ecca130c4212315311441f536fc32de4de419ef045d7ea30f9bf892a4b7891b7a3', 'Douglas Miller', 'dougmiller@themetacity.com', '<p></p>');


INSERT INTO workshop (author, title, blurb, article_text) VALUES ('dougmiller', 'Tomcat application development: table of contents', 'The table of contents for the piecemeal guide to developing Tomcat (JSP) applications. It is updated as each section is written adn published in my blog.',
					  '<p>This is a rough guide to setting up and deploying a non-trivial web application on Apache Tomcat using Java. I am doing this because I believe almost all of the current examples on the Internet suck. They are trivial, for the most part outdated and often wrong.</p>

<p>The application that we will be making is the very one that you are using at the moment: a blog-thingy. It not only covers the code of how make the site work, it also covers associated aspects of working with code and managing a growing project.</p>

<p>This guide is designed for people that are experienced with unix (file system, cli, permissions etc), Java (or some other language and are learning) and are happy to read and interpret error messages. I try to explain things as best I can without being condescending. Well more than usual.</p>

<h3>Table of Contents</h3>
<p>This is an ongoing project and gets updated when it does.</p>

<ol>
    <li>Description of the problem space; what are we doing?</li>
	<li>
		<p>By the end of this chapter you will have Tomcat up and running, a brief understanding what and where each part of it is and does and have a space ready for the application that you will write in future chapters.</p>
		<ul>
			<li>Unpacking and running</li>
			<li>Virtual Hosts</li>
			<li>Difference in versions</li>
        </ul>
    </li>

    <li>Build automation
		<p>This chapter deals with how to package applications for deployment, strategies for doing this and the advantages and disadvantages of different methods.</p>
		<ul>
			<li>How
			<ul>
				<li>WAR files and you</li>
				<li>Ant</li>
				<li>Projects</li>
			</ul>
			</li>
			<li>Why automate?
				<ul>
					<li>Lazy</li>
					<li>Consistent</li>
					<li>Multiple deployers</li>
				</ul>
			</li>
		</ul>
	</li>

    <li>Version control
		<p>Here we deal with organising and managing access to and management of a source code repository.<p>
		<ul>
			<li>Why?
			<ul>
				<li>Backup</li>
				<li>Multiple users</li>
				<li>Consistency</li>
			</ul>
			</li>
			<li>When should I use version control?</li>
			<li>How should I integrate it into my workflow?</li>
			<li>Which system should I use?</li>
		</ul>
    </li>

	<li>Bug tracking
	<p>Fixing problems in your program can be hard. Tracking and managing them is not.</p>
		<ul>Bug Database
			<li>
			<ul>
				<li>Why?<li>
				<li>When?</li>
				<li>How?</li>
			</ul>
			</li>
		</ul>
	</li>

	<li>Project Layout
		<p>This chapter shows how applications are laid out. Where do files of a certain type go? What do regular users have access to? How should I do this?</p>
		<ul>
			<li>Packages</li>
	            <ul>
	                <li>Deployment structure</li>
	                <li>web.xml</li>
	                <li>Context</li>
				</ul>
			<li>Classes</li>
			<li>MVC</li>
		</ul>
    </li>

    <li>
		<h3>Intermission: Dancing electrons.</h3>
    	<p>You will notice that up until this point that we have not made anything.</p>
    </li>

	<li>
		<p>JSP 1000</p>
		<ul>
			<li>What is JSP</li>
			<li>JSP vs HTML</li>
		</ul>
	</li>

	<li>
		<p>JSP 1001</p>
		<ul>
			<li>Build a page</li>
			<li>Parameters</li>
		</ul>
	</li>


	<li>
		<p></p>
		<ul>
			<li></li>
			<li></li>
			<li></li>
		</ul>
	</li>


Pages
	Doing stuff
	Beans
	Logic
	Usability of output
	Includes differences
    </li>
    <li>Libraries
        <ul>
			<li>JSTL</li>
			<li>Database</li>
			<li>URLConf</li>
			<li>JDBC</li>
        </ul>
    </li>


	<li>
		<p>Prettyifiing URLs</p>
		<ul>
			<li>Filters</li>
			<li>Install</li>
			<li>Examples</li>
		</ul>
	</li>



	<li>
		<p>Databases: At some point you will want to save data</p>
		<ul>
			<li></li>
			<li></li>
			<li></li>
		</ul>
	</li>



Databases
	Defining connections
		Context
			Properties
				war
				folder
		web.xml
	Connection classes
	Prepared Statements
	Simple example
		Self contained
		Abstraction
		In production
    </li>
    <li>

Security
	Methods
		Passwords
			SHA
			Hashes only in database
			Hashing method
	Concerns
		Rainbow
		User engineering
	Better to put out to a third party.
		OpenID
    </li>

    <li>
Example of pages.
	Security or a login.
	Self referential pages
	Problems
		Recursive pages
    		Error on error pages
    </li>

    <li> 
        preprocessing imports
    </li>

    <li>profiling for performance</li>

</ol>');
