[q]Stage 1: 55[/q]
[q]Stage 2: 15[/q]
[q]Stage 3: 15[/q]
[q]Stage 4: 15[/q]

Stage 1 out of 55: 


 * [5] Reads the light source direction and all polygons from file.

onload() method
polygon class - 


 * [10] Marks all the polygons that are facing away from the viewer.

// initialise the Z depth of all pixels to minimum
render()  for-loop



 * [10] Computes normal and reflected light intesity of every non-hidden polygon.


(getNormal() method)
getShading()

 * [10] Finds the edge lists of polygons.

-EdgeList class
-computeEdgeList(Polygon poly)     @return -EdgeList


 * [10] Renders the image to an array of colours using a Z-buffer.

-computeZbuffer()
-render()

 * [5] Displays the array of colours.

-convertBitmapToImage(Color[][] bitmap)


 * [5] A report that describes what the code does/doesn't do, any bugs, and how it was tested.

 -computerEdgeList() ---ymin - ymax
 -computerZbuffer()  --- y -- boundary of array



Stage 2 out of 15 (up to 70):
 * [10] The renderer works correctly; No glitches or holes, the ambient light level is correctly used, and the light source is correctly used.

--see the result of the programme


 * [4] Code is clean and readable.

 --code is clean

 * [1] Report is informative and clear.

--check the report

Stage 3 out of 15 (up to 85):

 * [15] The user can navigate the render, i.e.\ change the viewing direction.
 	- Polygons are rotated based on the viewing direction.

--rotate -- i/j/k/l   create viewPosition


 	- Polygons are translated and scaled to fit in the window.

--render() --> adjustTofit() --- > processScene



Stage 4 out of 15 (up to 100, with 5 spare marks):
 * [10] Allows multiple light sources to be added (and possibly modified) dynamically.

--add directly light 
--getShading()  cos>0   reflectance is relevant to the direct light
--GUI add direct light -- change color by changing slide


 * [10] Gouraud shading is used to make smooth, curved surfaces. Alternatively, Phong shading is used for an over-the-top answer.

Final mark calculation:

[q]Stage 1: 55[/q] + [q]Stage 2: 15[/q] + [q]Stage 3: 15[/q] + [q]Stage 4: 15[/q]
