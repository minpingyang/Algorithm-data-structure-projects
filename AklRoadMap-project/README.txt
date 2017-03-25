Introduce about this programme to allow you to understand my code

Q1– describe what I code does and doesn’t do.

Currently, I have achieved all requirements of completion core part and 2 of challenge part which one of them is about loading polygon files and draw colour polygon on the map, other one is that user can zoom in/out and drag the map by using mouse without affecting onClick() method. But I have not figured out how to implement quadtree structure to find the closest node of clicking location.

Q2– describe the important data structures I used.

Graph and Tries data structure is used in my programme. 
Basically, Graph data structure is used to draw the map, in particular, linked all of road segments and intersections properly. Map structure is used to implement road and node by indexing their ID. Set is used to store and read road segment and polygon. Tries data structure is used to find all road with same prefix. Comments have shown on all methods and class to briefly describe how the code work.

Q3– outline how I tested that my program worked. 

By follow the requirements, several tests which were used to sort issue of different requirements will be describe on the report. 
Test case1: read the data and draw the map.
Printing-out value of variables is used to print all the data was loaded on every load methods of Graph to check if the data is read correctly, before draw() method is used. For node and road files is relatively easier to deal with compare to polygon and segment file, the data only is needed to check on their constructor method, since both their information are shown line by line for each road or node. However, segment and polygon file is a bit complicated, since their information shown chunk by chunk. Similarly, use print-out to check if all data of the load method in Graph class before they are passed to polygon and segment constructor. 
For drawing method. First issue happened to me which is the order of drawing different things. The road segment and node were disappeared on the map, when I tried to draw polygons. By commenting draw polygon method out temporally, the result reminder me that the polygon show show be draw before others to avoid covering small graph.
Second issue I experienced is about only segment drawing is limited less than display panel, (only shown segment 2/3 of total panel)
