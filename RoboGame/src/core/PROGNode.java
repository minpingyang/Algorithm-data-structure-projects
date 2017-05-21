package core;

import java.util.ArrayList;

public class PROGNode implements RobotProgramNode {

    private ArrayList<STMTNode> statements;

    public PROGNode() {
        statements = new ArrayList<>();
    }

    @Override
    public void execute(Robot robot) {
        for (STMTNode stmt : statements) {
            stmt.execute(robot);
        }
    }

    public void addSTMTNode(STMTNode node) {
        statements.add(node);
    }

    @Override
    public String toString() {
        String s = "";
        for (STMTNode n : statements) {
            s += n.toString() + '\n';
        }
        return s;
    }
}
