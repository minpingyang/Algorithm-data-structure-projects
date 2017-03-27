package ass1.swen221;

import maze.Direction;
import maze.View;
import maze.Walker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by minpingyang on 21/03/17.
 */
public class Leftwalker extends Walker {
    //the direction of walker heading in
    private Direction currentDirection;

    private boolean isFrontWall, isRightWall,isLeftWall,isBackWall;
    private boolean isFindingWall; // if the walker is looking for a wall on his left side
    private coordSystemBaseOnWalker coordinateOfwalker;
    //a map collection of visited square ---> list of directions of unvisited corresponding to this square
    private HashMap<coordSystemBaseOnWalker,List<Direction>> unvisitedDirections;
    /***constructor****/
    public Leftwalker(){
        super("Left Walker");
        currentDirection = Direction.NORTH;
        isFindingWall = true;
        coordinateOfwalker = new coordSystemBaseOnWalker(0,0);
        unvisitedDirections = new HashMap<>();
    }

    public Direction move(View view){
        pause(3000);//able to clearly watch simulation of walking in the maze
        if(!unvisitedDirections.containsKey(coordinateOfwalker)){
            List<Direction> alternativeDirections = judgePotentialDirections(view);
            unvisitedDirections.put(coordinateOfwalker,alternativeDirections);
        }
        resetDirections(view);
        if(isFindingWall)
            lookLeftWall();
        else
            clockwiseFollowWall();

        memorise();
        return currentDirection;
    }

    /**
     * This method is used to get all potential available direction from walker view
     * @param view reflects the view the walker has from the current position in the maze
     *  @return all potential available direction from walker view
     * **/
    private List<Direction> judgePotentialDirections(View view){
        Direction[] directions = Direction.values();
        List<Direction> potentialDirections = new ArrayList<Direction>();
        for (Direction direction : directions){
            if(view.mayMove(direction)) //judge if move successfully
                potentialDirections.add(direction);
        }
        return potentialDirections;
    }
    private void resetDirections(View view){
        isBackWall =
    }

   /**
    * get the left/right direciton value of passing direction
    * @param direction given direction
    * @return  left/right direciton value of passing direction
    * **/
    private Direction getDirectionValue(Direction direction,boolean left){
        Direction tempdirection = null;
        get the left direciton value of passing direction
        if(left){
            switch (currentDirection){
                case EAST:
                    tempdirection = Direction.NORTH;
                    break;
                case NORTH:
                    tempdirection = Direction.WEST;
                    break;
                case SOUTH:
                    tempdirection = Direction.EAST;
                    break;;
                case WEST:
                    tempdirection = Direction.SOUTH;
                    break;
                default:
                    System.out.println("current direction does not exist");
            }
        }
        // get the right direciton value of passing direction
        else{
            switch (currentDirection){
                case EAST:
                    tempdirection = Direction.SOUTH;
                    break;
                case NORTH:
                    tempdirection = Direction.EAST;
                    break;
                case SOUTH:
                    tempdirection = Direction.WEST;
                    break;;
                case WEST:
                    tempdirection = Direction.NORTH;
                    break;
                default:
                    System.out.println("current direction does not exist");
            }

        }



        return tempdirection;
    }
   



    private class coordSystemBaseOnWalker {
        private int x;
        private int y;
        public coordSystemBaseOnWalker(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

}
