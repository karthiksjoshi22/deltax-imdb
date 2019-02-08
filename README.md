# deltax-imdb
DeltaX IMDB assignment Project.
This application is similar in kind to imdb website.Entites present in the apllication are 
1)Actor
2)Movies
3)Producer

Backend services are as follows:
1)Add a new actor
2)Fetch all actors using pagination
3)Update actor based on id
4)Fetch actor by id
5)Fetch all actor for drop-down data
6)Delete an actor

Producer related services:-
1)Add a new producer
2)Fetch all producers
3)Fetch producer for drop-down
4)Update producer by id
5)Delete a producer

Movie related services:-
1)Add a movie :- To add a movie actor,producer are mandatory fields.More than one actor could be added to a movie.
2)Fetch list of all movies and the actors associated to it.Here an optional parameter serachParam is used in http RequestParam if 
value is sent to it search will be based on the movie name.
3)Update movie based on id
4)Delete a particular movie

Technology stack used:-
1)JAVA 8
2)Spring boot
3)MySQL
4)Maven
5)Spring data JPA

Related Data base scripts id in db-scripts directory of the project.
All the source code is in /src/main/java/com.deltax.imdb package.
Datasource connection properties is in profiles/local/application.properties file where in we need to have db username and password accordingly and the file.saving.path should be given for uplading movie poster.
example:- file.saving.path=/home/path/

Commands to start the appliation:-
This application comes with embedded tomcat server,hence no need to externally map the JAR file to server location.
Maven related commands to build the JAR are as follows:-
1)mvn clean
2)mvn install
After the above two commands executing successfully navigate to the /target folder inside the project structure.
There JAR will be built with .jar extension.
Command to run the jar
java -jar jar-name
Once the JAR is started application will be started in port number 5060
All we need to hit the swagger-ui to start accessing the rest api services
Swagger link:- http://localhost:5060/swagger-ui.html#/
