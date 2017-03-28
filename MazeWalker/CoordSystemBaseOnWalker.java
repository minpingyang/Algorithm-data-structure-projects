package swen221.assignment1;


import maze.Direction;


/**
 * A coordinator system based on walker as origin.
 *
 * Initially, set the coordinator of walker as (0,0)
 * x axis -------------left->right, increasing
 * y axis -------------downward , increasing
 *
 * Created by minpingyang on 28/03/17.
 */
public class CoordSystemBaseOnWalker {
    private int x;
    private int y;
    public CoordSystemBaseOnWalker(int x, int y){
        this.x = x;
        this.y = y;
    }
    /***
     * override hashCode() method
     * narrow the hashCode() method,
     * force two CoordSystemBaseOnWalker objects return same hashCode
     * if they have same cooridanator(x,y)
     * time x,y by two random odd number;
     * */
    @Override
    public int hashCode(){
        return x*317 + y*13;
    }
    /***
     * override equals() method
     * narrow the equals() method,
     * force two CoordSystemBaseOnWalker objects should equals each other
     * if they have same cooridanator(x,y)
     *
     * */
    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object instanceof CoordSystemBaseOnWalker){
            CoordSystemBaseOnWalker otherCoord = (CoordSystemBaseOnWalker) object;
            int otherX = otherCoord.x;
            int otherY = otherCoord.y;
            return (otherX == this.x) && (otherY== this.y);
        }
        return false;
    }

    /***
     *
     * @param direction ...
     * @return the coordinator of the walker will arrive
     * ***/
    public CoordSystemBaseOnWalker moveTo(Direction direction){
        CoordSystemBaseOnWalker c = null;
        switch (direction){
            case NORTH:
                c = new CoordSystemBaseOnWalker(x,y-1);
                break;
            case SOUTH:
                c = new CoordSystemBaseOnWalker(x,y+1);
                break;
            case EAST:
                c = new CoordSystemBaseOnWalker(x+1,y);
                break;
            case WEST:
                c = new CoordSystemBaseOnWalker(x-1,y);
                break;
            default:
                System.out.println("current direction invalid");
        }
        return c;
    }
}

