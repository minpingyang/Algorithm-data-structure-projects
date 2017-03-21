package swen221.lab2.rules;

import swen221.lab2.model.*;
import swen221.lab2.util.CellDecayAbstractRule;
/**
 * CellDecayOverpopulationRule should implements Cell Decay version of Conway's rule for overproduction;
 * 
 * "Any live cell with more than three live neighbours gets older, as if by over-population"
 * @author David J. Pearce
 * */
public class CellDecayOverpopulationRule extends CellDecayAbstractRule {

	@Override
	public int apply(int x,int y, int neighbours){
		if (neighbours > 3) {
			
			return CellDecayAbstractRule.boardView.getCellState(x, y) + STATE_INCRESE;
		}else{
			return Rule.NOT_APPLICABLE;
		}
		
	}
}
