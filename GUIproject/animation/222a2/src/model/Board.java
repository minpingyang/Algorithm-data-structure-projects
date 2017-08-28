package model;


import gameComponent.Piece;
import gameComponent.Piece.Type;
import gui.View;
import resources.SoundResource;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


/***
 * This class is the most important class in the game. because whatever the pieces change, the pieces are all inside the pieceBoard
 * Basically, the board does excute every command,except undo and pass command
 *
 *
 * @author minpingyang
 *
 * **/
public class Board extends Observable implements ActionListener {
    private LeftCemetery leftCemetery;
    private Timer timer = new Timer(20, this);
    private boolean isLeftTurn;
    private RightCemetery rightCemetery;
    private Piece[][] board;
    private int rows = 10; // the row of the board
    private int cols = 10;
    private Piece[][] piecesBoard;// the 10*10 2D array to store all the pieces of the board
    private Piece actPiece; // the pieces just now was being moved/rotated/created --> used fo checking reactions
    private int pCol = 0, pRow = 0; //
    private boolean hasCreate = false; // the boolean indicating if this turn, the player already created a piece
    private Stack<Character> moveQue;
    private Point[][] piecePoint = new Point[10][10];
    private int rowB, colB, moveDir;
    private boolean isRotationPanel = false;
    private boolean deadTimer = false, moveTimer = false;
    private Piece movingPiece;
    private String movingDir;

    public boolean getIsRotationPanel() {
        return isRotationPanel;
    }

    public void setIsRotationPanel(boolean b) {
        isRotationPanel = b;
    }

    public void setMoveDir(int i) {
        moveDir = i;
    }

    public int getMoveDir() {
        return moveDir;
    }

    public void setRowB(int i) {
        rowB = i;
    }

    public void setColB(int i) {
        colB = i;
    }

    public int getRowB() {
        return rowB;
    }

    public int getColB() {
        return colB;
    }

    public void setHasCreate(boolean b) {
        hasCreate = b;
    }

    public boolean getHasCreate() {
        return hasCreate;
    }

    public Point[][] getPiecePoint() {
        return piecePoint;
    }

    //    public void adjustPiecePoint(int row,int col){
//        piecePoint[row][col].translate();
//    }
    public void setPiecesBoard(Piece[][] temp) {
        piecesBoard = temp;
    }

    public Piece[][] getPiecesBoard() {
        return piecesBoard;
    }

    public boolean getIsleftTurn() {
        return isLeftTurn;
    }

    public void setIsLeftTurn(boolean turn) {
        isLeftTurn = turn;
        System.out.println("is left turn: " + isLeftTurn);
        setChanged();
        notifyObservers();
    }


    private List<ArrayList<Integer>> deadRowCol = new ArrayList<>();

    private List<Integer> moveRowCol = new ArrayList<>();

    public Piece[][] getBoard() {
        return board;
    }

    /***
     * construtor of the class
     * create a
     * arrange the board with initially correct Piece type.  For example, LefeFace, rightFace, grid, outBoard
     *
     * **/
    public Board(View view) {
        isLeftTurn = View.isGreenTurn;
        leftCemetery = view.getLeftCemetery();
        rightCemetery = view.getRightCemetery();

        moveQue = new Stack<Character>();
        piecesBoard = new Piece[rows][cols];
        board = new Piece[rows][cols];
        piecesBoard = new Piece[rows][cols];
        // initialise all pieces,fill all gray and while rectangle
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                piecesBoard[row][col] = new Piece(Piece.Type.NonePiece);
                if ((row < 2 && col < 2) || (row > 7 && col > 7)) {
                    board[row][col] = new Piece(Piece.Type.OutBoard);
                    piecesBoard[row][col] = new Piece(Piece.Type.OutBoard);
                } else if ((row % 2 == 0 && col % 2 == 0) || (row % 2 != 0 && col % 2 != 0)) {
                    board[row][col] = new Piece(Piece.Type.GrayGrid);
                } else {
                    board[row][col] = new Piece(Piece.Type.WhiteGrid);
                }
            }
        }
        piecesBoard[1][1] = board[1][1] = new Piece(Piece.Type.LeftFace);
        piecesBoard[8][8] = board[8][8] = new Piece(Piece.Type.RightFace);
        piecesBoard[2][2] = board[2][2] = new Piece(Piece.Type.LeftCreation);
        piecesBoard[7][7] = board[7][7] = new Piece(Piece.Type.RightCreation);


    }

    /***
     * This method is used to determining the action the neighbour piece and current piece, once one of them has a sword pointing
     * to each other, -> which means here definitely has a interaction between them
     *
     * @param -- neighBourWeapon --> pass the weapon of neighbour
     * @param- -> selfWeap -->> pass the weapon of current actived pieces
     * @return int -->0->both dead     1->neighbour move back     2->self dead  3->self move back  4->neighbourd dead
     * ***/


    public int weaponCompare(char neighbourWeap, char selfWeap) {
        if (neighbourWeap == '|' && selfWeap == '|') {
            return 0;
        } else if (neighbourWeap == '|' && selfWeap == '#') {
            return 1;
        } else if (neighbourWeap == '|' && selfWeap == ' ') {
            return 2;
        } else if (selfWeap == '|' && neighbourWeap == '#') {
            return 3;
        } else if (selfWeap == '|' && neighbourWeap == ' ') {
            return 4;
        }
        return 5;
    }

    /**
     * This method is also a helper method, which is used to indicate what action current piece or its neighbour piece should do
     * But, this method classfily the action depends on the neighbour relative postion of the current piece
     *
     * @param direction   --> the neighbour releative position of the actived piece
     * @param neighbour-> the neighbour piece
     * @param self->      the current actived piece
     * @return int -->0->both dead     1->neighbour move back     2->self dead  3->self move back  4->neighbourd dead
     **/
    public int checkReactionHelper(String direction, Piece neighbour, Piece self) {
        int act = 5;
        switch (direction) {
            case "left":
                act = weaponCompare(neighbour.getRightWeapon(), self.getLeftWeapon());
                break;
            case "right":
                act = weaponCompare(neighbour.getLeftWeapon(), self.getRightWeapon());
                break;
            case "top":
                act = weaponCompare(neighbour.getBottomWeapon(), self.getTopWeapon());
                break;
            case "bottom":
                act = weaponCompare(neighbour.getTopWeapon(), self.getBottomWeapon());
                break;
            default:
                break;
        }
        return act;
    }

    /**
     * This method is ued to check if there is reaction in the current board
     * Basically, The method just check whether current piece with its four direction neighbouring pieces, either of them has a sword to point to actived pieces
     * Or, the current pieces has a sword pointing to one of its neighbour.
     * If it is, which means there is a reaction in the current board
     * <p>
     * secondly, the method also change the current piece boar, if there is reaction happens in current board
     *
     * @return --boolean --> the boolean value indicates there is reaction happen in the current piece board
     **/
    public boolean checkReaction() throws InterruptedException {
        int xRow = 0, xCol = 0, act = 5, keySize = 0;
        ArrayList<Integer> selfCoord = new ArrayList<>();
        if (actPiece != null) {
            //System.out.println("name:"+actPiece.getName());
            selfCoord = findPieceCoord(actPiece.getName());
            if (selfCoord.size() == 2) {
                xRow = selfCoord.get(0);
                xCol = selfCoord.get(1);
                if (xCol > 0) {
                    Piece leftPiece = piecesBoard[xRow][xCol - 1];
                    //System.out.println("0000leftpiece: "+leftPiece.getName());
                    boolean b1 = (leftPiece.getType() == Type.GreenPiece || leftPiece.getType() == Type.YellowPiece) && (leftPiece.getRightWeapon() == '|' || actPiece.getLeftWeapon() == '|');
                    if (b1) {
                        //System.out.println("1111leftpiece: "+leftPiece.getName());
                        piecesBoard[xRow][xCol].addNeighbourPiece("left", leftPiece);
                    }
                }
                if (xCol < 9) {
                    Piece rightPiece = piecesBoard[xRow][xCol + 1];
                    //System.out.println("000rightPiece: "+rightPiece.getName()+"leftwep:"+rightPiece.getLeftWeapon()+"  "+actPiece+"rightwP"+actPiece.getRightWeapon());

                    boolean b2 = (rightPiece.getType() == Type.GreenPiece || rightPiece.getType() == Type.YellowPiece) && (rightPiece.getLeftWeapon() == '|' || actPiece.getRightWeapon() == '|');
                    if (b2) {
                        //System.out.println("1111rightPiece: "+rightPiece.getName());
                        piecesBoard[xRow][xCol].addNeighbourPiece("right", rightPiece);
                    }
                }
                if (xRow < 9) {
                    Piece botPiece = piecesBoard[xRow + 1][xCol];
                    //System.out.println("00000botPiece: "+botPiece.getName()+" topWP "+botPiece.getTopWeapon()+" "+actPiece+" botmWp "+actPiece.getBottomWeapon());
                    boolean b3 = (botPiece.getType() == Type.GreenPiece || botPiece.getType() == Type.YellowPiece) && (botPiece.getTopWeapon() == '|' || actPiece.getBottomWeapon() == '|');
                    if (b3) {
                        //System.out.println("1111botPiece: "+botPiece.getName());
                        piecesBoard[xRow][xCol].addNeighbourPiece("bottom", botPiece);
                    }
                }
                if (xRow > 0) {
                    Piece topPiece = piecesBoard[xRow - 1][xCol];

                    boolean b4 = (topPiece.getType() == Type.GreenPiece || topPiece.getType() == Type.YellowPiece) && (topPiece.getBottomWeapon() == '|' || actPiece.getTopWeapon() == '|');
                    if (b4) {
                        //System.out.println("1111topPiece: "+topPiece.getName());
                        piecesBoard[xRow][xCol].addNeighbourPiece("top", topPiece);
                    }
                }
            }
            //this below part is used to act the reaction, what kind of reaction will happen depends on their weapon and their
            //relative position
            if (!piecesBoard[xRow][xCol].getNeighbourPiece().isEmpty()) {
                Piece tempPiece = piecesBoard[xRow][xCol];
                //System.out.println("There is a interaction happen!!");

                Thread.sleep(1000);

                Set<String> keys = tempPiece.getNeighbourPiece().keySet();
                keySize = keys.size();
                for (String key : keys) {
                    //0->both dead     1->neighbour move back     2->self dead  3->self move back  4->neighbourd dead
                    Piece neighP = tempPiece.getNeighbourPiece().get(key);
                    act = checkReactionHelper(key, neighP, tempPiece);
                    ArrayList<Integer> neighCoord = findPieceCoord(neighP.getName());
                    int Nrow = neighCoord.get(0);
                    int Ncol = neighCoord.get(1);
                    switch (act) {
                        case 0:

                            ArrayList<Integer> firstList = new ArrayList<>();
                            firstList.add(Nrow);
                            firstList.add(Ncol);
                            ArrayList<Integer> secondList = new ArrayList<>();
                            secondList.add(xRow);
                            secondList.add(xCol);
                            deadRowCol.add(firstList);
                            deadRowCol.add(secondList);
                            deadTimer = true;
                            timer.start();


                            break;
                        case 1:
                            String dir1 = pushBackDir(key, false);

                            movePiece(neighP.getName(), dir1, true, false);


                            //System.out.println("111111"+neighP.getName()+" was pushed "+dir1);
                            break;
                        case 2:

                            ArrayList<Integer> neiList = new ArrayList<>();
                            neiList.add(xRow);
                            neiList.add(xCol);
                            deadRowCol.add(neiList);
                            deadTimer = true;
                            timer.start();


//                            wentToCemetery(piecesBoard[xRow][xCol]);
//                            piecesBoard[xRow][xCol] = removeHelper(xRow, xCol);
                            //System.out.println("222222"+piecesBoard[xRow][xCol].getName()+" dead");
                            break;
                        case 3:
                            String dir2 = pushBackDir(key, true);

                            movePiece(tempPiece.getName(), dir2, true, false);


                            //System.out.println("3333333"+tempPiece.getName()+" was pushed "+dir2);
                            break;
                        case 4:

                            ArrayList<Integer> seList = new ArrayList<>();
                            seList.add(Nrow);
                            seList.add(Ncol);
                            deadRowCol.add(seList);
                            deadTimer = true;
                            timer.start();
//                            wentToCemetery(piecesBoard[Nrow][Ncol]);
//                            piecesBoard[Nrow][Ncol] = removeHelper(Nrow, Ncol);
//                            //System.out.println("4444444"+piecesBoard[Nrow][Ncol].getName()+" dead");
                            break;
                        default:
                            break;
                    }
                }
            }

        }

        if (keySize > 0) {
            //if there is a interation, now change the pieceboard to the new board (after reation has done)
            piecesBoard[xRow][xCol].setNeighbourPiece(new HashMap<String, Piece>());
            setChanged();
            notifyObservers();
            return true;
        }
        setChanged();
        notifyObservers();
        return false;
    }


    public void wentToCemeteryHelper(int row, int col) {
        piecesBoard[row][col] = removeHelper(row, col);
        setChanged();
        notifyObservers();
    }

    public void wentToCemetery(Piece piece) {

        SoundResource.DISAPPEAR.sound.play();
        if (piece.getType() == Type.GreenPiece) {
            leftCemetery.addPieces(piece);
        } else if (piece.getType() == Type.YellowPiece) {
            rightCemetery.addPieces(piece);
        }
    }


    /***
     * This method is ued to
     * @param key -> the postion of its neighbour piece   right/left/botton/top
     * @param isSelf-> a boolean value which indicate its current piece be pushed back Or its neighbour piece will be pushed back
     * @return direation -> return the pushed direction which will be matched with the move method direction-->  up/down/right/left
     *
     * **/
    public String pushBackDir(String key, boolean isSelf) {
        String dir = "";
        if (isSelf) {
            switch (key) {
                case "left":
                    dir = "right";
                    break;
                case "right":
                    dir = "left";
                    break;
                case "top":
                    dir = "down";
                    break;
                case "bottom":
                    dir = "up";
                    break;
                default:
                    break;
            }
        } else {
            switch (key) {
                case "left":
                    dir = "left";
                    break;
                case "right":
                    dir = "right";
                    break;
                case "top":
                    dir = "up";
                    break;
                case "bottom":
                    dir = "down";
                    break;
                default:
                    break;
            }
        }
        return dir;


    }

    public Piece[][] getPieceBoard() {
        return piecesBoard;
    }

    /**
     * This method is used to set current 2D array piece board to correct piece type in corresponding coordinator...
     *
     * @param temp pass the 2D array piece board
     **/
    public void setPieceBoard(Piece[][] temp) {
        piecesBoard = temp;
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                piecesBoard[row][col].fillPieceBoard();


            }
        }

        setChanged();
        notifyObservers();
    }

    /***
     *
     * This is method is ued to create command. In this game, we only allow create once in one turn
     *
     * @return boolean -This method is used to check if current creation grid is empty or not
     *
     * **/
    public boolean isEmptyCreationArea() {
        if (View.isGreenTurn) {
            return piecesBoard[2][2].getType() == Piece.Type.LeftCreation;
        }

        return piecesBoard[7][7].getType() == Piece.Type.RightCreation;
    }

    /**
     * This method is used to create a piece to the piece board int the creation grid
     *
     * @param temp -> what piece the user want to create
     * @return boolean -> indicates if create the piece successfully
     **/
    public boolean createPiece(Piece temp) {
        if (hasCreate || !isEmptyCreationArea()) {
            return false;
        }
        if (temp.getType() == Piece.Type.GreenPiece) {
            actPiece = piecesBoard[2][2] = temp;

        } else if (temp.getType() == Piece.Type.YellowPiece) {
            actPiece = piecesBoard[7][7] = temp;

        }
        setHasCreate(true);
        setChanged();
        notifyObservers();
        return true;
    }

    /**
     * This method is used to rotate the given name piece, in the given particular degree
     *
     * @param pieceName pass the name of piece want to be created
     * @param degree    -> pass the initial degree of the the piece want to be created
     * @return boolean -> indicate if creation is successfully
     **/
    public boolean rotatePiece(char pieceName, String degree, boolean nonUndo) {
        Piece temp = findPieceOnBoard(pieceName);
        if ((temp == null || temp.getHasRotate() || temp.getHasMove()) && nonUndo) {

            return false;
        }
        temp.rotate(degree);

        temp.setHasRotate(true);
        piecesBoard[pRow][pCol] = temp;
        actPiece = temp;
        setChanged();
        notifyObservers();
        return true;
    }

    /**
     * A helper method is used to find the row and col of the give piece name in the piece board
     *
     * @param pieceName pass the piece name which want to be found
     * @return arrayList  a list which storing the row and the col of the given name pieces under in the 2D array piece board
     **/
    public ArrayList<Integer> findPieceCoord(char pieceName) {
        ArrayList<Integer> rowCol = new ArrayList<Integer>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if ((row < 2 && col < 2) || (row > 7 && col > 7))
                    continue;
                if (pieceName == piecesBoard[row][col].getName()) {
                    rowCol.add(row);
                    rowCol.add(col);

                    break;
                }
            }
        }


        return rowCol;
    }

    /**
     * This helper method is used to find the piece in the piece baord by passing the given piece name
     *
     * @param pieceName pass the piece name which want to be found
     * @return Piece  the piece in the pieceboard by the given name
     **/
    public Piece findPieceOnBoard(char pieceName) {
        Piece temp = null;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if ((row < 2 && col < 2) || (row > 7 && col > 7))
                    continue;
                if (pieceName == piecesBoard[row][col].getName()) {
                    temp = piecesBoard[row][col];
                    pCol = col;
                    pRow = row;
                }
            }
        }
        return temp;
    }

    public char checkNeighbourHelper2(int row, int col) {
        if (piecesBoard[row][col].getType() == Type.GreenPiece || piecesBoard[row][col].getType() == Type.YellowPiece) {
            return piecesBoard[row][col].getName();
        }
        return '0';
    }

    public Stack<Character> getMoveQueue() {
        return moveQue;
    }

    public void setMoveQueue(Stack<Character> temp) {
        moveQue = temp;

    }

    public boolean checkNeighbourHelper(int row, int col, String dir) {
        char neigName = '1';
        boolean hasNeighbour = false;
        switch (dir) {
            case "right":
                while (col < 9 && (neigName != '0')) {
                    neigName = checkNeighbourHelper2(row, ++col);
                    if (neigName != '0') {
                        moveQue.add(neigName);
                        System.out.println("right neigName(queue name):" + neigName);
                        hasNeighbour = true;
                    }
                }
                break;
            case "left":
                while (col > 0 && (neigName != '0')) {
                    neigName = checkNeighbourHelper2(row, --col);
                    if (neigName != '0') {
                        moveQue.add(neigName);
                        System.out.println("left neigName(queue name):" + neigName);
                        hasNeighbour = true;
                    }
                }
                break;
            case "up":
                while (row > 0 && (neigName != '0')) {
                    neigName = checkNeighbourHelper2(--row, col);
                    if (neigName != '0') {
                        moveQue.add(neigName);
                        hasNeighbour = true;
                    }
                }
                break;
            case "down":
                while (row < 9 && (neigName != '0')) {
                    neigName = checkNeighbourHelper2(++row, col);
                    if (neigName != '0') {
                        moveQue.add(neigName);
                        hasNeighbour = true;
                    }
                }
                break;

            default:
                break;
        }

        return hasNeighbour;
    }

    public boolean checkNeighbour(char pieceName, String dir) throws InterruptedException {

        if (checkReaction()) {
            return false;
        }
        ArrayList<Integer> rowCol = findPieceCoord(pieceName);
        int rowX = rowCol.get(0);
        int colX = rowCol.get(1);

        return checkNeighbourHelper(rowX, colX, dir);

    }

    /**
     * A helper method is uesd to help move method
     * after a piece moved, it's position pobably invalid, eg. out of border of the baord.
     *
     * @param row col the row and col of piece
     * @return boolean indicate if it is possible to move over there
     **/
    // return true-->al
    public boolean moveHelper(int row, int col) {
        boolean outborder = row > 9 || col > 9 || row < 0 || col < 0;// true
        // ->out
        // of
        // border
        boolean inForbiddenArea = (row < 2 && col < 2) || (row > 7 && col > 7);// true->in
        // forbidden
        return !(outborder || inForbiddenArea);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (deadTimer) {
            ArrayList<Integer> first = deadRowCol.get(0);
            ArrayList<Integer> second = new ArrayList<>();

            piecesBoard[first.get(0)][first.get(1)].rotate("2");
            piecesBoard[first.get(0)][first.get(1)].decreaseRoTimes();

            boolean stop1 = piecesBoard[first.get(0)][first.get(1)].getRoTimes() == 0;

            if (deadRowCol.size() > 1) {
                second = deadRowCol.get(1);
                piecesBoard[second.get(0)][second.get(1)].rotate("2");
                piecesBoard[second.get(0)][second.get(1)].decreaseRoTimes();
                stop1 = piecesBoard[first.get(0)][first.get(1)].getRoTimes() == 0 && piecesBoard[second.get(0)][second.get(1)].getRoTimes() == 0;
            }

            if (stop1) {
                timer.stop();

                wentToCemetery(piecesBoard[first.get(0)][first.get(1)]);
                wentToCemeteryHelper(first.get(0), first.get(1));
                if (deadRowCol.size() > 1) {
                    wentToCemetery(piecesBoard[second.get(0)][second.get(1)]);
                    wentToCemeteryHelper(second.get(0), second.get(1));
                }

                deadRowCol.clear();

                deadTimer = false;

            }
        }
        if (moveTimer) {
            int row = moveRowCol.get(0);
            int col = moveRowCol.get(1);
            int x = piecePoint[row][col].x;
            int y = piecePoint[row][col].y;
            boolean stop2 = false;
            switch (movingDir) {
                case "up":
                    piecePoint[row][col].translate(0, -1);
                    piecesBoard[row][col].setIsMoving(true);
                    piecesBoard[row][col].decreaseMovingStep();
                    stop2 = piecesBoard[row][col].getMovingStep() == 0;
                    break;
                case "down":
                    piecePoint[row][col].translate(0,1);
                    piecesBoard[row][col].setIsMoving(true);
                    piecesBoard[row][col].decreaseMovingStep();
                    stop2 = piecesBoard[row][col].getMovingStep() == 0;
                    break;
                case "right":
                    piecePoint[row][col].translate(1,0);
                    piecesBoard[row][col].setIsMoving(true);
                    piecesBoard[row][col].decreaseMovingStep();
                    stop2 = piecesBoard[row][col].getMovingStep() == 0;
                    break;
                case "left":
                    piecePoint[row][col].translate(-1,0);
                    piecesBoard[row][col].setIsMoving(true);
                    piecesBoard[row][col].decreaseMovingStep();
                    stop2 = piecesBoard[row][col].getMovingStep() == 0;
                    break;

            }


            if (stop2) {

                timer.stop();
                piecesBoard[row][col].setMovingStep(70);
                if (movingDir.equals("up")) {
                    piecePoint[row - 1][col] = piecePoint[row][col]; //add
                    piecePoint[row][col] = null; //remove
                    piecesBoard[row][col] = removeHelper(row, col); // REMOVE
                    piecesBoard[row - 1][col] = movingPiece; //add
                    piecesBoard[row - 1][col].setIsMoving(false);
                } else if (movingDir.equals("down")) {
                    piecePoint[row + 1][col] = piecePoint[row][col]; //add
                    piecePoint[row][col] = null; //remove
                    piecesBoard[row][col] = removeHelper(row, col); // REMOVE
                    piecesBoard[row + 1][col] = movingPiece; //add
                    piecesBoard[row + 1][col].setIsMoving(false);
                } else if (movingDir.equals("right")) {
                    piecePoint[row][col + 1] = piecePoint[row][col]; //add
                    piecePoint[row][col] = null; //remove
                    piecesBoard[row][col] = removeHelper(row, col); // REMOVE
                    piecesBoard[row][col + 1] = movingPiece; //add
                    piecesBoard[row][col + 1].setIsMoving(false);

                } else if (movingDir.equals("left")) {
                    piecePoint[row][col - 1] = piecePoint[row][col]; //add
                    piecePoint[row][col] = null; //remove
                    piecesBoard[row][col] = removeHelper(row, col); // REMOVE
                    piecesBoard[row][col - 1] = movingPiece; //add
                    piecesBoard[row][col - 1].setIsMoving(false);
                }


                moveTimer = false;
                moveRowCol.clear();
                movingDir = " ";


            }
        }

        setChanged();
        notifyObservers();

    }
    // return a boolean indicates if move the piece successfully

    /**
     * a remove helper method, which is used to check after a piece move to other position, what piece should be added again in
     * it's privous position, for example, if it's original position is creation grid, then should be add the creation piece
     *
     * @param row col -> the coordinator of the piece
     **/
    public Piece removeHelper(int row, int col) {
        if ((row == 2 && col == 2)) {
            return new Piece(Piece.Type.LeftCreation);
        }
        if ((row == 7 && col == 7)) {
            return new Piece(Piece.Type.RightCreation);
        }
        return new Piece(Piece.Type.NonePiece);
    }

    /**
     * The move method is used to do move action
     *
     * @param pieceName pass the piece name which will be used to find what piece should be moved
     * @param dir       -> the move direction -> up/right/left/down
     * @param isPushed  -> a boolean-> indicate the move is because of being pushed or actively moved
     * @return a boolean indicates if move the piece successfully
     **/

    public boolean movePiece(char pieceName, String dir, boolean isPushed, boolean mulitpleMove) {
        Piece temp = findPieceOnBoard(pieceName);
        if (temp == null) {
            return false;
        }
        if (temp.getHasMove() && !mulitpleMove) {
            return false;
        }
        if (temp.getHasRotate() && !isPushed) {
            return false;
        }
        if (dir.equals("up")) {
            if (!(moveHelper(pRow - 1, pCol))) {
                return false;
            } else {


                moveRowCol.add(pRow);
                moveRowCol.add(pCol);
                moveTimer = true;
                movingDir = dir;
                movingPiece = temp;
                timer.start();

//                piecesBoard[pRow][pCol] = removeHelper(pRow, pCol); // REMOVE
//                pRow = pRow - 1;
//                piecesBoard[pRow][pCol] = temp; // add
            }
        } else if (dir.equals("down")) {
            if (!(moveHelper(pRow + 1, pCol))) {
                return false;
            } else {
                moveRowCol.add(pRow);
                moveRowCol.add(pCol);
                moveTimer = true;
                movingDir = dir;
                movingPiece = temp;
                timer.start();
//                piecesBoard[pRow][pCol] = removeHelper(pRow, pCol); // REMOVE
//                pRow = pRow + 1;
//                piecesBoard[pRow][pCol] = temp; // add
            }
        } else if (dir.equals("left")) {
            if (!(moveHelper(pRow, pCol - 1))) {
                return false;
            } else {
                moveRowCol.add(pRow);
                moveRowCol.add(pCol);
                moveTimer = true;
                movingDir = dir;
                movingPiece = temp;
                timer.start();
//                piecesBoard[pRow][pCol] = removeHelper(pRow, pCol); // REMOVE
//                pCol = pCol - 1;
//                piecesBoard[pRow][pCol] = temp; // add
            }
        } else if (dir.equals("right")) {
            if (!(moveHelper(pRow, pCol + 1))) {
                return false;
            } else {
                moveRowCol.add(pRow);
                moveRowCol.add(pCol);
                moveTimer = true;
                movingDir = dir;
                movingPiece = temp;
                timer.start();
//                piecesBoard[pRow][pCol] = removeHelper(pRow, pCol); // REMOVE
//                pCol = pCol + 1;
//                piecesBoard[pRow][pCol] = temp; // add
            }
        }
        if (!isPushed) {
            temp.setHasMove(true);
        }
        actPiece = temp;
        setChanged();
        notifyObservers();
        return true;

    }

    public void printBoard() {

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                piecesBoard[row][col].fillPieceBoard();
            }
        }

        for (int row = 0; row < 10; row++) {
            for (int inRow = 0; inRow < 3; inRow++) {
                for (int col = 0; col < 10; col++) {
                    piecesBoard[row][col].drawWeaponToPiece(inRow);
                    //piecesBoard[row][col].fillPieceBoard();
                }
                System.out.println();
            }
        }
    }


    //1-left win.  2- right win 0--non one win
    public int doesWin() {
        Piece leftBot = this.piecesBoard[2][1];
        Piece leftRight = this.piecesBoard[1][2];
        Piece rightTop = this.piecesBoard[7][8];
        Piece rightLeft = this.piecesBoard[8][7];
        boolean checkLeft = leftBot.getTopWeapon() == '|' || leftRight.getLeftWeapon() == '|';
        boolean checkRight = rightLeft.getRightWeapon() == '|' || rightTop.getBottomWeapon() == '|';
        if (checkLeft) {
            return 1;
        } else if (checkRight) {
            return 2;
        }

        return 0;
    }


}



