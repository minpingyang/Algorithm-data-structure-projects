package core;

public class ShieldOnNode extends STMTNode {

    @Override
    public void execute(Robot robot) {
        robot.setShield(true);
    }

    @Override
    public String toString() {
        return "shieldOn;";
    }
}
