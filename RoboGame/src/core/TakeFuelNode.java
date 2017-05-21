package core;

public class TakeFuelNode extends STMTNode {

    @Override
    public void execute(Robot robot) {
        robot.takeFuel();
    }

    @Override
    public String toString() {
        return "takeFuel;";
    }
}
