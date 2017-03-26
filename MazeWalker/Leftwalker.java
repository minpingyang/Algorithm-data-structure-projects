package ass1.swen221;

import maze.Coordinate;
import maze.Direction;
import maze.View;
import maze.Walker;

/**
 * Created by minpingyang on 21/03/17.
 */
public class Leftwalker extends Walker {
    //the direction of walker heading in
    private Direction currentDirection;

    private boolean isFrontWall, isRightWall,isLeftWall,isBackWall;
    private boolean isFindingWall; // if the walker is looking for a wall on his left side
    private Coordinate coordinate;

    public Leftwalker(){
        super("Left Walker");
        currentDirection = Direction.NORTH;
        isFindingWall = true;
        coordinate = new Coordinate(0,0);
        

    }
    @Override
    protected Direction move(View view) {
        return null;
    }
    //

}
