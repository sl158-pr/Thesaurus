IMPORTANT: READ THIS FILE THOTHOUGHLY BEFORE USING Thesaurus

Developed by
Name: Sumanth Lakshminarayana
Student Id: 1000830230


1. A Thesaurus Client & Server
==================================================================================
Thesaurus is a client/server system. The client process will have a GUI interface and will
allow the user to select a word in a block of text and have the system send a query to a
server to look up the word in a thesaurus file and return a list of alternative words from
the server. The client will allow the user to replace the initial word in the text box with 
the selected alternative and allow the user to select another word.

The server process also have a GUI interface. It includes a text box which
display incoming client requests. 

2. Development of Thesaurus
=================================================================================
a) Java is the programming langauge used to develop the application
b) Socket programming is used for client server programming
c) Server runs @port number 1235.
d) SQL database has been developed and connected to it with JDBC

3. Implementations
==================================================================================
a) Client and server processes connect using socket programming
b) Client Process and its user interface work as specified
c) Server Process works accordingly
d) Sever User interface shows incoming client messages
e) Sever works correctly with messages from multiple clients
g) Server maintains connections after each response

4. Extra Credit Implementations  
==================================================================================
a) Use of database package like Access or SQL and connect to it with JDBC
b) Making the server multithreaded

5. Software Requirements
==================================================================================
1) Wampserver
2) Above JDK 5
3) Eclipse IDE

6. Instalation
==================================================================================
a) mysql-connector-java-5.1.6.jar copied in lib folder of Thesaurus project folder has to be added 
into the build path
b) thesaurus.sql- My Sql Database to be imported into Wamp server

Rest of the code resides in src folder of project
appLogo.jpg is a Image used in user interface for client and should reside in the project main folder

7. Steps to run the application(Using Eclipse IDE)
==================================================================================
In the Zip folder Thesaurus is the project folder to be imported into eclipse
to test the application. thesaurus.sql has to be imported into Wampserver

1) Run the com.client.server.Server.java to run the server.
2) Run the com.client.server.Client.java to run the client.
3) Start the wampserver having the thesaurus.sql database file
Note:- Running the client without server results in error and closes the client window

8. Testing
==================================================================================
1)Enter the text in client GUI,
a) Selecting a word in the text block and clicking on 'Get Synonyms' button would result in synonmys 
of the word in the text area below the 'Get Synonyms' button if the word has an entry 
in database otherwise error pops up saying 'No synonym found for the selected word'
b) Selecting nothing in the text block and clicking the 'Get Synonyms' button would result in
error pop up saying 'Please select a word to find synonym'
2) Once the synonyms are populated selected word can be replaced with any of the synonyms listed
by just clicking on synonym word.
3) Selected word in the client is displayed in the server.(No word selected is displayed if nothing 
in the text block of client is selected and 'Get Synonyms' button is clicked)

8. Test Data
==================================================================================
 Below is the Database entries, i.e. my application displays synonyms for below set of words
	test 	    trial,examination,experiment,assay,try,proof
	Brave 	    courageous,fearless,dauntless,intrepid,plucky
	Calm 	    quiet,peaceful,still,tranquil,mild,serene,smooth
	Dangerous 	perilous,hazardous,risky,uncertain,unsafe
	Decide  	determine,settle,choose,resolve
	Eager 	    keen,fervent,enthusiastic,involved,interested
	Explain  	elaborate,clarify,define,interpret,justify
	Wrong 	    incorrect,inaccurate,mistaken,erroneous,improper
    Valid 	    authorized,legitimate

10. Limitations
==================================================================================	
Thesaurus application doesn't have the complete set of word list, Only nine words are 
been inserted in the database and hence we can get the synonyms of those.
