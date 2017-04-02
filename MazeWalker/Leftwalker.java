package swen221.assignment1;


import maze.Direction;
import maze.View;
import maze.Walker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by minpingyang on 21/03/17.
 */
public class LeftWalker extends Walker {
    //the currentDirection of walker heading in
    private Direction currentDirection;

    private boolean existForwardWall, existRightWall, existLeftWall, existBackWall;
    private boolean isFindingWall; // if the walker is looking for a wall on his left side
    private CoordSystemBaseOnWalker coordinateOfwalker;
    //a map collection of visited square ---> list of directions of unvisited corresponding to this square
    private HashMap<CoordSystemBaseOnWalker,List<Direction>> unvisitedDirections;
    /***constructor****/
    public LeftWalker(){
        super("Left Walker");
        currentDirection = Direction.NORTH;
        isFindingWall = true;
        coordinateOfwalker = new CoordSystemBaseOnWalker(0,0);
        unvisitedDirections = new HashMap<>();
    }

    public Direction move(View view){
        pause(1000);//used for testing 
        //store possible choices of directions the walker could move from current viwe
        if(!unvisitedDirections.containsKey(coordinateOfwalker)){
            List<Direction> alternativeDirections = judgePotentialDirections(view);
            unvisitedDirections.put(coordinateOfwalker,alternativeDirections);
        }
        //Reset boolean values that are the four different walls from walker view
        checkAllCurrentWallsStatus(view);
        //By following left hand rule to find the correct direction
        if(isFindingWall)
            changeCurrentDirection_toAvaiableDirection();
        else
            clockwiseFollowWall();
        //avoid the walker going same direction on the same position
        smartMove();
        return currentDirection;
    }
    private void smartMove(){
        //store all available directions as a list
        List<Direction> availableDirections = unvisitedDirections.get(coordinateOfwalker);
        //check if contain 
        if(!availableDirections.contains(currentDirection) && !availableDirections.isEmpty()){
            currentDirection = availableDirections.get(0);
            isFindingWall = true;
        }
        unvisitedDirections.get(coordinateOfwalker).remove(currentDirection);
        coordinateOfwalker = coordinateOfwalker.moveTo(currentDirection);
    }
    /**
     * change currentDirection to avaiableDirection
     * by searching left hand rule(clock-wise searching)
     * ***/
    private void changeCurrentDirection_toAvaiableDirection(){
        isFindingWall = !existForwardWall && !existBackWall && !existLeftWall && !existLeftWall;
        // front wall is available && left wall not available 
        if(existLeftWall && !existForwardWall ){
         //keep forward
        }
        //last case fail, then check if rightwall exist assuming forwardWall exist
        else if(existForwardWall && !existRightWall){
            //turn right
            currentDirection = getDirectionValue(currentDirection,"right");
        }//once last two case failed,
        else if(existRightWall && !existBackWall){
            currentDirection = getDirectionValue(currentDirection,"opposite");
        }else if(existBackWall && !existLeftWall){
            currentDirection =getDirectionValue(currentDirection,"left");
        }
    }
    /*follow left wall to walk**/
    private void clockwiseFollowWall(){
        if(!existLeftWall)
            currentDirection = getDirectionValue(currentDirection,"left");
        else if (!existForwardWall){

        }
        else if(!existRightWall){
            currentDirection =getDirectionValue(currentDirection,"right");


        }else if (!existBackWall){
            currentDirection =getDirectionValue(currentDirection,"opposite");
        }

    }
    
    /**
     * This method is used to get all potential available currentDirection from walker view
     * @param view reflects the view the walker has from the current position in the maze
     *  @return all potential available currentDirection from walker view
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
    private void checkAllCurrentWallsStatus(View view){
        existLeftWall = !view.mayMove(getDirectionValue(currentDirection,"left"));
        existRightWall = !view.mayMove(getDirectionValue(currentDirection,"right"));
        existBackWall = !view.mayMove(getDirectionValue(currentDirection,"opposite"));
        existForwardWall = !view.mayMove(currentDirection);

    }


   /**
    * get the left/right direciton value of passing currentDirection
    * @param direction given currentDirection
    * @return  left/right direciton value of passing currentDirection
    * **/
    private Direction getDirectionValue(Direction direction,String flag){
        Direction tempdirection = null;
        //get the left currentDirection value of passing currentDirection
        if(flag.equals("left")){
            switch (direction){
                case EAST:
                    tempdirection = Direction.NORTH;
                    break;
                case NORTH:
                    tempdirection = Direction.WEST;
                    break;
                case SOUTH:
                    tempdirection = Direction.EAST;
                    break;
                case WEST:
                    tempdirection = Direction.SOUTH;
                    break;
                default:
                    System.out.println("current currentDirection does not exist");
            }
        }
        // get the right currentDirection value of passing currentDirection
        else if (flag.equals("right")){
            switch (direction){
                case EAST:
                    tempdirection = Direction.SOUTH;
                    break;
                case NORTH:
                    tempdirection = Direction.EAST;
                    break;
                case SOUTH:
                    tempdirection = Direction.WEST;
                    break;
                case WEST:
                    tempdirection = Direction.NORTH;
                    break;
                default:
                    System.out.println("current currentDirection does not exist");
            }

        }
        // get the opposite currentDirection value of passing currentDirection
        else {
            switch (direction){
                case EAST:
                    tempdirection = Direction.WEST;
                    break;
                case NORTH:
                    tempdirection = Direction.SOUTH;
                    break;
                case SOUTH:
                    tempdirection = Direction.NORTH;
                    break;
                case WEST:
                    tempdirection = Direction.EAST;
                    break;
                default:
                    System.out.println("current currentDirection does not exist");
            }

        }

        return tempdirection;
    }


}
