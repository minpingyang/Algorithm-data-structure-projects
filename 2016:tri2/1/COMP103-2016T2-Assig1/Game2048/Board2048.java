// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 103 Assignment 1
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;

public class Board2048 {

    private static final int LIMIT = 7; // to determine the ratio of 2s over 4s (the closer to 10, the more 2s)
    private static final int TARGET = 2048; // number value the player needs to reach

    private static final int ROWS = 4;
    private static final int COLUMNS = 4;
    private int [][] board;

    public Board2048 () {
        board = new int [ROWS][COLUMNS];
        setColor();
    }

    /** Return whether (at least) the magic target number has been achieved.  
     */
    public boolean hasReachedTarget() {
        for(int r=0;r<ROWS;r++){
            for(int c=0;c<COLUMNS;c++){
                if(this.board[r][c]==TARGET){
                    return true;
                }

            }
        }
        return false;
    }

    /** Return whether the game is over (true) or not (false). 
     *  If there is some space on the board left, the game is not over.
     *  If there is no space left, the game is still not over, if adjacent tiles hold the same value, 
     *  as they could be compressed to fewer tiles by a player move.
     */
    public boolean isGameOver() {
        int ifEmpty=this.numEmptyTiles();
        if(ifEmpty!=0){
            return false;
        }
        for(int r=0;r<ROWS;r++){
            for(int c=0;c<COLUMNS-1;c++){
                if(this.board[r][c]==this.board[r][c+1]){
                    return false;
                }

            }
        }
        for(int c=0;c<COLUMNS;c++){
            for(int r=0;r<ROWS-1;r++){
                if(this.board[r][c]==this.board[r+1][c]){
                    return false;
                }

            }
        }
        return true;
    }

    /** Return the number of empty tiles. 
     *  An empty tile is one which holds the value 0.
     */
    private int numEmptyTiles() {
        int account=0;
        for(int r=0;r<ROWS;r++){
            for(int c=0;c<COLUMNS;c++){
                if(this.board[r][c]==0){
                    account++;
                }

            }
        }
        return account;
    }

    /** Insert a random number (either 2 or 4) at a randon empty tile.
     *  Note that 7 out of 10 times the number should be 2.
     *  An empty tile is one which holds the value 0.
     */
    public void insertRandomTile() {
        if(!isGameOver()&&!hasReachedTarget()){
            double amount=Math.random();

            int ro=(int)(ROWS*amount);
            int co=(int)(COLUMNS*amount);
            while(this.board[ro][co]!=0){
                ro=(int)(ROWS*Math.random());
                co=(int)(COLUMNS*Math.random());
            }
            if(this.board[ro][co]==0){

                if(amount<=0.7){
                    this.board[ro][co]=2;
                }else{
                    this.board[ro][co]=4;
                }
            }
        }
        return;
    }
    /** Move the tiles left. 
    Each time two tiles with the same number touch, the numbers are added and the two tiles merge on 
    the left side. An empty tile is then added on the right hand side of the board.

    Examples: 
    2 2 4 2 will give 4 4 2 0 (the first 2s merge into a 4. Then the remaining
    4 and 2 follow, and the board is completed on the right with a 0)

    4 4 2 2 will give 8 4 0 0 (4 and 4 merge into a 8, 2 and 2 merge into a 4, 
    completing with zeros on the right)

    4 4 4 4 will give 8 8 0 0 (First two 4s merge together, the last two 4s merge together)

    For each row, do the following:
    1. Shift all non-empty tiles to the left as far as possible, making sure that all empty tiles are on the right.
    2. From left to right, merge any two tiles with the same number by adding them, discarding
    the second one, and adding an empty tile on the right of the board.
     */
    public void left() {
        if(!isGameOver()&&!hasReachedTarget()){
            for(int r = 0; r < ROWS; r++){
                for (int c = 0; c < COLUMNS-1; c++) { 
                    int [][] tempBoard = new int [ROWS][COLUMNS]; 
                    //put all non-empty tiles of values into temporary arrays from left to right
                    for(int r1 = 0; r1 < ROWS; r1++){
                        int x = 0;
                        for (int c1 = 0; c1 < COLUMNS; c1++) {
                            if(board[r1][c1]!=0){  
                                tempBoard[r1][x]=board[r1][c1];
                                x++;
                            } 
                        }
                    }
                    //board=temboard;
                    for(int r2 = 0; r2 < ROWS; r2++){
                        for (int c2 = 0; c2 < COLUMNS; c2++) {
                            board[r2][c2]=tempBoard[r2][c2];
                        }
                    }
                    //add same number to the left
                    if(board[r][c+1]==board[r][c]){
                        board[r][c]+=board[r][c+1];
                        board[r][c+1]=0;
                    }

                }
            }
        }

    }

    /** Move the tiles right. 
     * Each time 2 tiles with the same number touch, the numbers are added and the two tiles merge on 
     * the right side. An empty tile is then added on the left hand side of the board.

     * Examples:  
     *   2 2 4 2 will give 0 4 4 2 (2 and 4 remain unchanged, then the last leftmost 2s merge 
     *     into a 4, completing with a zero on the left.)
     *     
     *   4 4 2 2 will give 0 0 8 4 (2 and 2 merge into a 4, 4 and 4 merge into a 8)
     *   4 4 4 4 will give 0 0 8 8 (First two 4s merge together, the last two 4s merge together)
     *   
     * For each row, do the following:
     *   1. Shift all non-empty tiles to the right, making sure that all the empty tiles are on the left.
     *   2. From right to left, merge any two tiles with the same number by adding them, discarding 
     *      the second one, and adding an empty tile on the left of the board.
     */
    public void right() {
        if(!isGameOver()&&!hasReachedTarget()){
            for(int r = 0; r < ROWS; r++){
                for (int c = COLUMNS-1; c>0; c--) { 
                    int [][] tempBoard = new int [ROWS][COLUMNS]; 
                    //put all non-empty tiles of values into temporary arrays from right to left
                    for(int r1 = 0; r1 < ROWS; r1++){
                        int x = COLUMNS-1;
                        for (int c1 = COLUMNS-1; c1 >=0; c1--) {
                            if(board[r1][c1]!=0){  
                                tempBoard[r1][x]=board[r1][c1];
                                x--;
                            } 
                        }
                    }
                    //board=tempboard
                    for(int r2 = 0; r2 < ROWS; r2++){
                        for (int c2 = 0; c2 < COLUMNS; c2++) {
                            board[r2][c2]=tempBoard[r2][c2];
                        }
                    }
                    //add same number to the right  
                    if(board[r][c]==board[r][c-1]){
                        board[r][c]+=board[r][c-1];
                        board[r][c-1]=0;
                    }

                }
            }

        }

    }

    /** Move the tiles up. 
    Each time 2 tiles with the same number touch, the numbers are added and the two tiles merge on 
    the up side. An empty tile is then added at the bottom of the board.

    For each column, do the following:
    1. Move all non-empty tiles up, making sure that all empty tiles are at the bottom.
    2. From top to down, merge any two adjacent tiles with the same number by adding them, discarding
    the second one, and adding an empty tile at the bottom of the board.
     */
    public void up() {
        if(!isGameOver()&&!hasReachedTarget()){
            for(int c = 0; c < COLUMNS; c++){
                for (int r = 0; r< ROWS-1; r++) { 
                    int [][] tempBoard = new int [ROWS][COLUMNS]; 
                    //put all non-empty tiles of values into temporary arrays from Up to down
                    for(int c1 = 0; c1 < COLUMNS; c1++){
                        int x = 0;
                        for (int r1 = 0; r1 < ROWS; r1++) {
                            if(board[r1][c1]!=0){  
                                tempBoard[x][c1]=board[r1][c1];
                                x++;
                            } 
                        }
                    }
                    //board=temboard;
                    for(int r2 = 0; r2 < ROWS; r2++){
                        for (int c2 = 0; c2 < COLUMNS; c2++) {
                            board[r2][c2]=tempBoard[r2][c2];
                        }
                    }
                    //add same number to the Up
                    if(board[r+1][c]==board[r][c]){
                        board[r][c]+=board[r+1][c];
                        board[r+1][c]=0;
                    }

                }
            }

        }

    }

    /** Move the tiles down. 
    Each time 2 tiles with the same number touch, the numbers are added and the two tiles merge at
    the bottom. An empty tile is then added at the top of the board.

    For each column, do the following:
    1. Move all non-empty tiles down, making sure that all empty tiles are at the top.
    2. From bottom to top, merge any two adjacent tiles with the same number by adding them, discarding
    the second one, and adding an empty tile at the top of the board.
     */
    public void down() {
        if(!isGameOver()&&!hasReachedTarget()){
            for(int c = 0; c < COLUMNS; c++){
                for (int r = ROWS-1; r>0; r--) { 
                    int [][] tempBoard = new int [ROWS][COLUMNS]; 
                    //put all non-empty tiles of values into temporary arrays from down to Up
                    for(int c1 = 0; c1 < COLUMNS; c1++){
                        int x = COLUMNS-1;
                        for (int r1 = ROWS-1; r1 >=0; r1--) {
                            if(board[r1][c1]!=0){  
                                tempBoard[x][c1]=board[r1][c1];
                                x--;
                            } 
                        }
                    }
                    //board=tempboard
                    for(int r2 = 0; r2 < ROWS; r2++){
                        for (int c2 = 0; c2 < COLUMNS; c2++) {
                            board[r2][c2]=tempBoard[r2][c2];
                        }
                    }
                    //add same number to the down  
                    if(board[r][c]==board[r-1][c]){
                        board[r][c]+=board[r-1][c];
                        board[r-1][c]=0;
                    }

                }
            }
        }

    }

    public String toString() {
        String tiles = "";
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) 
                tiles += "  " + board[row][col];

            tiles += "\n"; // row ends here
        }
        return tiles;
    }

    // layout of the board
    private static final int boardLeft = 80;    // left edge of the board
    private static final int boardTop = 40;     // top edge of the board
    private static final int tileSize = 80;     // width of tiles in the board
    private static final int padding = 5;       // size of the padding;
    private static final Color colorPad = new Color (187,173,160);

    public static int getHeight() {
        return boardTop*2 + ROWS*tileSize+(ROWS+1)*padding + 100;
    }

    public static int getWidth() {
        return boardLeft*2 + COLUMNS*tileSize+(COLUMNS+1)*padding;
    }

    public void displayMessage(String txt){
        // Clear up any eventual previous message first
        UI.setColor(Color.white);
        UI.fillRect(boardLeft, boardTop + 50 + tileSize * ROWS, tileSize * ROWS, 200);

        // Display the message
        UI.setFontSize(40);
        UI.setColor(Color.red);
        UI.drawString(txt, boardLeft, boardTop + 50 + tileSize * (ROWS + 1 ));
    }

    public void redraw() {
        // draw the padding
        UI.setColor(colorPad);

        double width = COLUMNS*tileSize+(COLUMNS+1)*padding;
        double height  = ROWS*tileSize+(ROWS+1)*padding;
        UI.fillRect(boardLeft, boardTop, width, height);

        // Draw the tiles
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) 
                drawTile(row, col);

        }

        displayScore();
    }

    private void drawTile(int row, int col) {
        double left = boardLeft+padding+col*(tileSize+padding);
        double top = boardTop+padding+row*(tileSize+padding);

        // Fill the rectangle with a colour matching the value of the tile
        UI.setColor(getColor(board[row][col]));
        UI.fillRect(left,top,tileSize,tileSize);

        // Display the number
        if (board[row][col] == 0) 
            return;

        if (board[row][col] >= 8) 
            UI.setColor(Color.white);
        else 
            UI.setColor(new Color (119,110,101));

        int fontSize = getFontSize(board[row][col]);
        UI.setFontSize(fontSize);

        double x = left + getXPos(board[row][col]);;
        double y = top + tileSize * 0.55;
        UI.drawString(""+board[row][col], x, y);
    }

    // mapping from tile values to colours
    Map <Integer, Color> colors = new HashMap <Integer, Color> ();
    private void setColor() {
        colors = new HashMap <Integer, Color> ();
        colors.put(new Integer(0), new Color (205,192,180));
        colors.put(new Integer(2), new Color (238,228,218));
        colors.put(new Integer(4), new Color (241,225,202));
        colors.put(new Integer(8), new Color (242,177,121));
        colors.put(new Integer(16), new Color (245,149,99));
        colors.put(new Integer(32), new Color (246,124,95));
        colors.put(new Integer(64), new Color (246,94,59));
        colors.put(new Integer(128), new Color (237,207,114));
        colors.put(new Integer(256), new Color (237,204,97));
        colors.put(new Integer(512), new Color (237,200,80));
        colors.put(new Integer(1024), new Color (237,197,63));
        colors.put(new Integer(2048), new Color (237,194,46));
        colors.put(new Integer(4096), new Color (243,102,107));
        colors.put(new Integer(8192), new Color (241,76,86));
        colors.put(new Integer(16384), new Color (247,63,62));
        colors.put(new Integer(32768), new Color (113,180,216));
        colors.put(new Integer(65536), new Color (92,160,227));
        colors.put(new Integer(131072), new Color (20,131,208));

    }

    private Color getColor(int value) {
        Color c = colors.get(new Integer(value));

        if (c == null) // has an unknown value been supplied?
            return Color.black;  // return "black" as a default

        return c;
    }

    // compute font size depending on number value
    private int getFontSize(int value) {
        if (value < 100) 
            return 28;

        if (value < 1000) 
            return 24;

        return 22;
    }

    private double getXPos(int value) {
        if (value < 10)       return (tileSize * 0.45);
        if (value < 100)      return (tileSize * 0.35);
        if (value < 1000)     return (tileSize * 0.3);
        if (value < 10000)    return (tileSize * 0.2);
        if (value < 100000)   return (tileSize * 0.14);
        else                  return (tileSize * 0.05);
    }

    private void displayScore() {
        double x = boardLeft + tileSize * COLUMNS /2.;
        double y = boardTop/2;

        // Clear up previous score first
        UI.setColor(Color.white);
        UI.fillRect(x-5, 0, 150, 30);

        int score = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) 
                score += board[row][col];

        }

        UI.setFontSize(20);
        UI.setColor(Color.blue);
        UI.drawString("" + score, x, y);
    }
}
