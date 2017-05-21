package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;
import javax.swing.JFileChooser;

/**
 * The parser and interpreter. The top level parse function, a main method for
 * testing, and several utility methods are provided. You need to implement
 * parseProgram and all the rest of the parser.
 */
public class Parser {

  

    public static HashMap<String, VARNode> variables = new HashMap<>();

    // =======================================================================
    /**
     * Top level parse method, called by the World
     */
    public static RobotProgramNode parseFile(File code) {
        Scanner scan = null;
        try {
            scan = new Scanner(code);

            // the only time tokens can be next to each other is
            // when one of them is one of (){},;
            scan.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");

            RobotProgramNode n = parseProgram(scan); // You need to implement

            scan.close();
            return n;
        } catch (FileNotFoundException e) {
            System.out.println("Robot program source file not found");
        } catch (ParserFailureException e) {
            System.out.println("Parser error:");
            System.out.println(e.getMessage());
            scan.close();
        }
        return null;
    }

    /** For testing the parser without requiring the world */

    public static void main(String[] args) {
        if (args.length > 0) {
            for (String arg : args) {
                File f = new File(arg);
                if (f.exists()) {
                    System.out.println("Parsing '" + f + "'");
                    RobotProgramNode prog = parseFile(f);
                    System.out.println("Parsing completed ");
                    if (prog != null) {
                        System.out.println("================\nProgram:");
                        System.out.println(prog);
                    }
                    System.out.println("=================");
                } else {
                    System.out.println("Can't find file '" + f + "'");
                }
            }
        } else {
            while (true) {
                JFileChooser chooser = new JFileChooser(".");// System.getProperty("user.dir"));
                int res = chooser.showOpenDialog(null);
                if (res != JFileChooser.APPROVE_OPTION) {
                    break;
                }
                RobotProgramNode prog = parseFile(chooser.getSelectedFile());
                System.out.println("Parsing completed");
                if (prog != null) {
                    System.out.println("Program: \n" + prog);
                }
                System.out.println("=================");
            }
        }
        System.out.println("Done");
    }
    
    // Useful Patterns
    public static final Pattern NUMPAT = Pattern.compile("-?\\d+"); // ("-?(0|[1-9][0-9]*)");
    public static final Pattern OPENPAREN = Pattern.compile("\\(");
    public static final Pattern CLOSEPAREN = Pattern.compile("\\)");
    public static final Pattern OPENBRACE = Pattern.compile("\\{");
    public static final Pattern CLOSEBRACE = Pattern.compile("\\}");

    // Action patterns
    public static final Pattern ACTIONS = Pattern.compile("move|takeFuel|turnL|turnR|wait|turnAround|shieldOn|shieldOff");
    public static final Pattern MOVE = Pattern.compile("move");
    public static final Pattern TAKEFUEL = Pattern.compile("takeFuel");
    public static final Pattern TURNL = Pattern.compile("turnL");
    public static final Pattern TURNR = Pattern.compile("turnR");
    public static final Pattern WAIT = Pattern.compile("wait");
    public static final Pattern TURNAROUND = Pattern.compile("turnAround");
    public static final Pattern SHIELDON = Pattern.compile("shieldOn");
    public static final Pattern SHIELDOFF = Pattern.compile("shieldOff");
    
    // Statement patterns
    public static final Pattern VARIABLE = Pattern.compile("\\$[A-Za-z][A-Za-z0-9]*");
    public static final Pattern ASSIGN = Pattern.compile("\\=");
    public static final Pattern LOOP = Pattern.compile("loop");
    public static final Pattern IF = Pattern.compile("if");
    public static final Pattern WHILE = Pattern.compile("while");
    public static final Pattern ELIF = Pattern.compile("elif");
    public static final Pattern ELSE = Pattern.compile("else");


    // Condition nodes
    public static final Pattern CONDITIONS = Pattern.compile("and|or|not|lt|gt|eq");
    public static final Pattern AND = Pattern.compile("and");
    public static final Pattern OR = Pattern.compile("or");
    public static final Pattern NOT = Pattern.compile("not");
    public static final Pattern LESSTHAN = Pattern.compile("lt");
    public static final Pattern GREATERTHAN = Pattern.compile("gt");
    public static final Pattern EQUAL = Pattern.compile("eq");

    // Expression nodes
    public static final Pattern OPERATIONS = Pattern.compile("add|sub|mul|div");
    public static final Pattern ADD = Pattern.compile("add");
    public static final Pattern SUB = Pattern.compile("sub");
    public static final Pattern MUL = Pattern.compile("mul");
    public static final Pattern DIV = Pattern.compile("div");

    // Sensor nodes
    public static final Pattern SENSORS = Pattern.compile("fuelLeft|oppLR|oppFB|numBarrels|barrelLR|barrelFB|wallDist");
    public static final Pattern FUELLEFT = Pattern.compile("fuelLeft");
    public static final Pattern OPPLR = Pattern.compile("oppLR");
    public static final Pattern OPPFB = Pattern.compile("oppFB");
    public static final Pattern NUMBARRELS = Pattern.compile("numBarrels");
    public static final Pattern BARRELLR = Pattern.compile("barrelLR");
    public static final Pattern BARRELFB = Pattern.compile("barrelFB");
    public static final Pattern WALLDIST = Pattern.compile("wallDist");
    /**
     * PROG ::= STMT+
     */
    public static RobotProgramNode parseProgram(Scanner s) {

        PROGNode progNode = new PROGNode();

        while (s.hasNext()) {
            STMTNode statement = parseStatement(s);
            progNode.addSTMTNode(statement);
        }

        return progNode;
    }

    private static STMTNode parseStatement(Scanner s) {
    	// action
        if (s.hasNext(ACTIONS)) {
            return parseAction(s);
        }// loop 
        else if (s.hasNext(LOOP)) {
            return parseLoop(s);
        }// if
        else if (s.hasNext(IF)) {
            return parseIf(s);
        }//while
        else if (s.hasNext(WHILE)) {
            return parseWhile(s);
        }//variable 
        else if (s.hasNext(VARIABLE)) {
            return parseAssign(s);
        } else {
            fail("Unkown statement", s);
            return null; // dead code
        }
    }

    private static STMTNode parseAction(Scanner s) {

        String token = require(ACTIONS, "Unknown Action command", s);
        STMTNode statement;

        switch (token) {
        case "move":
            EXPNode expression1 = null;
            if (checkFor(OPENPAREN, s)) {
                expression1 = parseExpression(s);
                require(CLOSEPAREN, "Needed a close parenthesis after the argument", s);
            }
            statement = new MoveNode(expression1);
            break;
        case "takeFuel":
            statement = new TakeFuelNode();
            break;
        case "turnL":
            statement = new TurnLNode();
            break;
        case "turnR":
            statement = new TurnRNode();
            break;
        case "wait":
            EXPNode expression2 = null;
            if (checkFor(OPENPAREN, s)) {
                expression2 = parseExpression(s);
                require(CLOSEPAREN, "Needed a close parenthesis after the argument", s);
            }
            statement = new WaitNode(expression2);
            break;
        case "turnAround":
            statement = new TurnAroundNode();
            break;
        case "shieldOn":
            statement = new ShieldOnNode();
            break;
        case "shieldOff":
            statement = new ShieldOffNode();
            break;
        default:
            fail("Unknown Action command", s);
            statement = null; // dead code
            break;
        }

        require(";", "All ACT has to end with a \";\"", s);

        return statement;
    }

    private static STMTNode parseLoop(Scanner s) {
        require(LOOP, "LOOP has to start with \"loop\"", s);
        BLOCKNode blockNode = parseBlock(s);
        return new LOOPNode(blockNode);
    }

    private static STMTNode parseIf(Scanner s) {

        //Firstly :  if ( COND ) BLOCK
        require(IF, "IF has to start with \"if\"", s);
        require(OPENPAREN, "Needed an open parenthesis after \"if\"", s);
        CONDNode condition = parseConditional(s);
        require(CLOSEPAREN, "Needed a close parenthesis after the conditional expression", s);
        BLOCKNode ifBlock = parseBlock(s);

        //secondly :  [elif ( COND ) BLOCK]*
        ArrayList<CONDNode> elifConditions = new ArrayList<>();
        ArrayList<BLOCKNode> elifBlocks = new ArrayList<>();
        while (checkFor(ELIF, s)) {
            require(OPENPAREN, "Needed an open parenthesis after \"elif\"", s);
            CONDNode elifCondition = parseConditional(s);
            require(CLOSEPAREN, "Needed a close parenthesis after the conditional expression", s);
            BLOCKNode elifBlock = parseBlock(s);
            elifConditions.add(elifCondition);
            elifBlocks.add(elifBlock);
        }

        //Finally:  [else BLOCK]
        BLOCKNode elseBlock = null;
        if (checkFor(ELSE, s)) {
            elseBlock = parseBlock(s);
        }

        return new IFNode(condition, ifBlock, elifConditions, elifBlocks, elseBlock);
    }

    private static STMTNode parseWhile(Scanner s) {

        require(WHILE, "WHILE has to start with \"while\"", s);
        require(OPENPAREN, "Needed an open parenthesis after \"while\"", s);

        CONDNode condition = parseConditional(s);

        require(CLOSEPAREN, "Needed a close parenthesis after the conditional expression", s);

        BLOCKNode block = parseBlock(s);

        return new WHILENode(condition, block);
    }

    private static STMTNode parseAssign(Scanner s) {
        String varName = require(VARIABLE, "No valid variable found", s);

        VARNode varNode = variables.get(varName);
        if (varNode == null) {
            varNode = new VARNode(varName, 0);
            variables.put(varName, varNode);
        }

        require("=", "Needed a \"=\" after the variable name in an assignment", s);

        EXPNode expNode = parseExpression(s);

        require(";", "All assignments has to end with a \";\"", s);

        return new ASSGNNode(varNode, expNode);
    }

    private static BLOCKNode parseBlock(Scanner s) {

        require(OPENBRACE, "BLOCK has to start with \"{\"", s);

        BLOCKNode blockNode = new BLOCKNode();

        while (s.hasNext() && !s.hasNext(CLOSEBRACE)) {
            STMTNode statement = parseStatement(s);
            blockNode.addSTMTNode(statement);
        }

        if (blockNode.isEmpty()) {
            fail("BLOCK cannot be empty", s);
        }

        require(CLOSEBRACE, "BLOCK has to end with a \"}\"", s);

        return blockNode;
    }

    private static EXPNode parseExpression(Scanner s) {
    	//number
        if (s.hasNext(NUMPAT)) {
            return new NUMNode(requireInt(NUMPAT, "Needed an integer", s));
        }//sensor 
        else if (s.hasNext(SENSORS)) {
            return parseSensor(s);
        }//varibale
        else if (s.hasNext(VARIABLE)) {
            String varName = require(VARIABLE, "Invalid variable name", s);
            VARNode var = variables.get(varName);
            if (var == null) {
                var = new VARNode(varName, 0);
                variables.put(varName, var);
            }
            return var;
        }//operator 
        else if (s.hasNext(OPERATIONS)) {
            return parseOperation(s);
        } else {
            fail("Unkown EXP statement", s);
            return null; // dead code
        }
    }

    private static SENNode parseSensor(Scanner s) {

        String token = require(SENSORS, "Unknown Sensor label", s);

        switch (token) {
        case "fuelLeft":
            return new FuelLeftNode();
        case "oppLR":
            return new OppLRNode();
        case "oppFB":
            return new OppFBNode();
        case "numBarrels":
            return new NumBarrelsNode();
        case "barrelLR":
            EXPNode expression1 = null;
            if (checkFor(OPENPAREN, s)) {
                expression1 = parseExpression(s);
                require(CLOSEPAREN, "Needed a close parenthesis after the argument", s);
            }
            return new BarrelLRNode(expression1);
        case "barrelFB":
            EXPNode expression2 = null;
            if (checkFor(OPENPAREN, s)) {
                expression2 = parseExpression(s);
                require(CLOSEPAREN, "Needed a close parenthesis after the argument", s);
            }
            return new BarrelFBNode(expression2);
        case "wallDist":
            return new WallDistNode();
        default:
            fail("Unknown Sensor label", s);
            return null; // dead code
        }
    }

    private static OPNode parseOperation(Scanner s) {

        String token = require(OPERATIONS, "Unknown Operation label", s);
        require(OPENPAREN, "Needed an open parenthesis before two arguments", s);
        EXPNode exp1 = parseExpression(s);
        require(",", "Needed a \",\" between two arguments", s);
        EXPNode exp2 = parseExpression(s);
        require(CLOSEPAREN, "Needed a close parenthesis after two arguments", s);

        switch (token) {
        case "add":
            return new AddNode(exp1, exp2);
        case "sub":
            return new SubNode(exp1, exp2);
        case "mul":
            return new MulNode(exp1, exp2);
        case "div":
            return new DivNode(exp1, exp2);
        default:
            fail("Unknown Operation label", s);
            return null; // dead code
        }
    }

    private static CONDNode parseConditional(Scanner s) {

        String token = require(CONDITIONS, "Unknown Condition label", s);
        require(OPENPAREN, "Needed an open parenthesis before two arguments", s);

        CONDNode condArgument1 = null;
        CONDNode condArgument2 = null;
        EXPNode expArgument1 = null;
        EXPNode expArgument2 = null;

        switch (token) {
        case "lt":
        case "gt":
        case "eq":
            expArgument1 = parseExpression(s);
            require(",", "Needed a \",\" between two arguments", s);
            expArgument2 = parseExpression(s);
            break;
        case "and":
        case "or":
            condArgument1 = parseConditional(s);
            require(",", "Needed a \",\" between two arguments", s);
            condArgument2 = parseConditional(s);
            break;
        case "not":
            condArgument1 = parseConditional(s);
            break;
        default:
            break;
        }

        require(CLOSEPAREN, "Needed a close parenthesis after two arguments", s);

        switch (token) {
        case "lt":
            return new LessThanNode(expArgument1, expArgument2);
        case "gt":
            return new GreaterThanNode(expArgument1, expArgument2);
        case "eq":
            return new EqualNode(expArgument1, expArgument2);
        case "and":
            return new AndNode(condArgument1, condArgument2);
        case "or":
            return new OrNode(condArgument1, condArgument2);
        case "not":
            return new NotNode(condArgument1);
        default:
            return null; // dead code
        }
    }

    // utility methods for the parser
    /**
     * Report a failure in the parser.
     */
    private static void fail(String message, Scanner s) {
        String msg = message + "\n   @ ...";
        for (int i = 0; i < 5 && s.hasNext(); i++) {
            msg += " " + s.next();
        }
        throw new ParserFailureException(msg + "...");
    }

    /**
     * Requires that the next token matches a pattern if it matches, it consumes
     * and returns the token, if not, it throws an exception with an error
     * message
     */
    private static String require(String p, String message, Scanner s) {
        if (s.hasNext(p)) {
            return s.next();
        }
        fail(message, s);
        return null;
    }

    private static String require(Pattern p, String message, Scanner s) {
        if (s.hasNext(p)) {
            return s.next();
        }
        fail(message, s);
        return null;
    }

    /**
     * Requires that the next token matches a pattern (which should only match a
     * number) if it matches, it consumes and returns the token as an integer if
     * not, it throws an exception with an error message
     */
    @SuppressWarnings("unused")
    private static int requireInt(String p, String message, Scanner s) {
        if (s.hasNext(p) && s.hasNextInt()) {
            return s.nextInt();
        }
        fail(message, s);
        return -1;
    }

    private static int requireInt(Pattern p, String message, Scanner s) {
        if (s.hasNext(p) && s.hasNextInt()) {
            return s.nextInt();
        }
        fail(message, s);
        return -1;
    }

    /**
     * Checks whether the next token in the scanner matches the specified
     * pattern, if so, consumes the token and return true. Otherwise returns
     * false without consuming anything.
     */
    @SuppressWarnings("unused")
    private static boolean checkFor(String p, Scanner s) {
        if (s.hasNext(p)) {
            s.next();
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkFor(Pattern p, Scanner s) {
        if (s.hasNext(p)) {
            s.next();
            return true;
        } else {
            return false;
        }
    }
}
// You could add the node classes here, as long as they are not declared public
// (or private)
