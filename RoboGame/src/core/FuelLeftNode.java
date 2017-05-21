package core;

public class FuelLeftNode implements SENNode {

    @Override
    public int evaluate(Robot robot) {
        return robot.getFuel();
    }

    @Override
    public String toString() {
        return "fuelLeft";
    }
}
