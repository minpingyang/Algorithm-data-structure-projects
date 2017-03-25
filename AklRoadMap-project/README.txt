Introduce about this programme to allow you to understand my code

Q1– describe what I code does and doesn’t do.

Currently, I have achieved all requirements of completion core part and 2 of challenge part which one of them is about loading polygon files and draw colour polygon on the map, other one is that user can zoom in/out and drag the map by using mouse without affecting onClick() method. But I have not figured out how to implement quadtree structure to find the closest node of clicking location.

Q2– describe the important data structures I used.

Graph and Tries data structure is used in my programme. 
Basically, Graph data structure is used to draw the map, in particular, linked all of road segments and intersections properly. Map structure is used to implement road and node by indexing their ID. Set is used to store and read road segment and polygon. Tries data structure is used to find all road with same prefix. Comments have shown on all methods and class to briefly describe how the code work.

Q3– outline how I tested that my program worked. 

By follow the requirements, several tests which were used to sort issue of different requirements will be describe on the report. 
Test1: read the data and draw the map.
Printing-out value of variables is used to print all the data was loaded on every load methods of Graph to check if the data is read correctly, before draw() method is used. For node and road files is relatively easier to deal with compare to polygon and segment file, the data only is needed to check on their constructor method, since both their information are shown line by line for each road or node. However, segment and polygon file is a bit complicated, since their information shown chunk by chunk. Similarly, use print-out to check if all data of the load method in Graph class before they are passed to polygon and segment constructor. 
For drawing method. First issue happened to me which is the order of drawing different things. The road segment and node were disappeared on the map, when I tried to draw polygons. By commenting draw polygon method out temporally, the result reminder me that the polygon show show be draw before others to avoid covering small graph.
Second issue I experienced is about only segment drawing is limited less than display panel, (only shown segment 2/3 of total panel)
if-continue is used to skip all the points out of screen within the drawing for loop.

Test2: Zooming and moving map by click button
Zooming and moving map is achieved by implementing onMove() method from GUI class. Switch statement is used to check what user want to do. The First issue is that the parameter enum type is only allowed to compare the value of enum type in switch statement instead on Move type. 
For example, I used to think use “case Move.North:” instead of “case North:”. Because the type of parameter is Move, I thought the type of variable after “case” should match the type of parameter, by testing, the programme does not work.
Second issue I sort out by testing is about how many pixels the map should move by each clicking. To make sure the moving pixels should correspond to current scale, each time the moving value should be divided by current scale, otherwise, the moving will not move properly under different scale. “FACTOR_MOVE” is constant which is used to make sure moving nice.
Third issue is about zooming centre. In this issue, I have to change drawing coordinator system of the programme, because zooming should work based the centre of display panel instead of the corner of left-top. By testing, I find the issue, then change all data points of drawing method become points based on the centre of display panel. Then, the zooming works well.

Test3: Click a road to show the information
Issue1: MouseEvent provided different coordinator system from coordinator of drawing nodes, so the coordinator of clicking point have to be changed to a “better point” which base on the centre of panel same as drawing nodes origin. By running programme, find the reason of the issue. 
Issue2: The previous click node should reset to null and change their colour back to default colour. By testing, if not, all the nodes which were clicked will keep the clicked_color. 
Issue3: the duplicate information of road segments happened when use click a node. 
By testing, there are same information of road segment appearing on the output text area. By fixing this issue. 
by adding an if statement to check if the information already existed. 

Test4 searching roads with same prefix by implement Tries structure
Issue: Typing random character does not show anything on text-out area.

Step1: print out if user input has been recorded and passed into findMatchingRoads() method. --------find it works.
Step2: print out “roads type” in add() method of RoadTrie class, check if add successful ---------------find it works
Step3: go back to findMatchingRoads() method, check if traverse each node successfully. print out if temRootNode really doing things. ---------------fail
Step4: fix …silly mistakes.  Change temRootNode = temRootNode.children.get(c);
