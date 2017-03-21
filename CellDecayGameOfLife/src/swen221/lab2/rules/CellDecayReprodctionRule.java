package swen221.lab2.rules;

import swen221.lab2.model.*;
import swen221.lab2.util.CellDecayAbstractRule;
/**
 * This should implement Conway's rule for reproduction:
 * 
 * "Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction"
 * 
 * @author David J. Pearce
 *
 */
public class CellDecayReprodctionRule extends CellDecayAbstractRule {

	@Override
	public int apply(int x,int y, int neighbours){
		if (neighbours == 3) {
			
			return CellDecayAbstractRule.boardView.getCellState(x, y) + STATE_DECRESE;
		}else{
			return Rule.NOT_APPLICABLE;
		}
		
	}
}
