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
