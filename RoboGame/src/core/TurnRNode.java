package core;

public class TurnRNode extends STMTNode {

    @Override
    public void execute(Robot robot) {
        robot.turnRight();
    }

    @Override
    public String toString() {
        return "turnR;";
    }
}
