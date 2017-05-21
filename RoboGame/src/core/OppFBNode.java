package core;

public class OppFBNode implements SENNode {

    @Override
    public int evaluate(Robot robot) {
        return robot.getOpponentFB();
    }

    @Override
    public String toString() {
        return "oppFB";
    }
}
