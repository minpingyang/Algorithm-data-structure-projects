Introduce about this programme to allow you to understand my code

Q1– describe what I code does and doesn’t do?

Currently, I have achieved all requirements of completion core part and 2 of challenge part which one of them is about loading polygon files and draw colour polygon on the map, other one is that use can use scroll of mouse to zooming map， and move the map by dragging mouse without affecting onClick() method. But I have not figured out how to implement quadtree structure to find the closest node of clicking location.

Q2– describe the important data structures I used?

Graph and Tries data structure is used in my programme. 
Basically, Graph data structure is used to draw the map, in particular, linked all of road segments and intersections properly. Map structure is used to supper graph strucuture

Q3– outline how I tested that my program worked?

MainMap class is the main class of this programme which also extend GUI class. By implement onload() method, passing the four File parameters of the method, and initialise filed of MainMap , then passing the four File parameters to onload method of Graph class. In this onload method, Filed of Graph should be initialised again and then passing different File parameter to corresponding load methods, but before polygonFile used, if-statement to check if there is polygon file in chosen folder. 
There are four different load methods for different data files. They are loadNodes, loadRoads
