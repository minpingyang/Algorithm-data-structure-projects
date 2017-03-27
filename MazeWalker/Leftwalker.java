package ass1.swen221;

import maze.Direction;
import maze.Walker;

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
        pause(3000);
        if(!unvisitedDirections.containsKey(coordinateOfwalker)){
            List<Direction> alternativeDirections = judgePotentialDirections(view);
            unvisitedDirections.put(coordinateOfwalker,alternativeDirections);
        }
        resetWalls(view);
        if(isFindingWall)
            lookLeftWall();
        else
            clockwiseFollowWall();

        memorise();
        return currentDirection;
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
