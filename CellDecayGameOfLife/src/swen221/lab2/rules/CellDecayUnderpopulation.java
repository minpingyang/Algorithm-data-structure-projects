package swen221.lab2.rules;

import swen221.lab2.model.*;
import swen221.lab2.util.CellDecayAbstractRule;
/**
 * CellDecayUnderpopulationRule should implements Cell Decay version of Conway's rule for under-production;
 * 
 * "Any live cell with fewer than 2 live neighbours gets older, as if by under-population"
 * @author David J. Pearce
 * */
public class CellDecayUnderpopulation  extends CellDecayAbstractRule {

	@Override
	public int apply(int x,int y, int neighbours){
		if (neighbours < 2) {
			
			return CellDecayAbstractRule.boardView.getCellState(x, y) + STATE_INCRESE;
		}else{
			return Rule.NOT_APPLICABLE;
		}
		
	}
}
