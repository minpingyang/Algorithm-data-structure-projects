package core;

public interface SENNode extends EXPNode {

    @Override
    public int evaluate(Robot robot);

    @Override
    public String toString();

}
