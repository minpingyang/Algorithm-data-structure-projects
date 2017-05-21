package core;

public interface OPNode extends EXPNode {

    @Override
    public int evaluate(Robot robot);

    @Override
    public String toString();

}
