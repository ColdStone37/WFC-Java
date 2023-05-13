package math.wfc.rules;
import math.wfc.Gridstate;
import math.wfc.Tile;
import math.wfc.Superposition;

/**
 * A rule that forces a certain amount of a tile to be in each column of the grid.
 */
public class FixedAmountPerColumn extends Rule {
	private final int amount;

	/**
	 * Constructer for a Rule that forces a certain count of tile to be in a certain row.
	 * @param  a The amount of tiles in each row
	 */
	public FixedAmountPerColumn(int a){
		amount = a;
	}

	public STATE getState(Gridstate grid, int x, int y, Tile t){
		countResult c = countColumn(grid, x, y, t);
		if(c.collapsedCount > amount || c.collapsedCount + c.possibleCount < amount-1)
			return STATE.FORBIDDEN;
		if(c.collapsedCount == amount)
			return STATE.IMPOSSIBLE;
		if(c.collapsedCount + c.possibleCount == amount-1)
			return STATE.FORCED;
		return STATE.POSSIBLE;
	}

	/**
	 * Counts the amount of a given tile in a column of the grid igonring the position the rule is calculated for
	 * @param  grid the current Gridstate
	 * @param  x    the x coordinate of the position to test for
	 * @param  y    the y coordinate of the position to test for
	 * @param  t    the tile to test for at that position
	 * @return      countResult object holding the amount of possible and collapsed Superpositions
	 */
	private countResult countColumn(Gridstate grid, int x, int y, Tile t){
		countResult counter = new countResult(); 
		for(int Y = 0; Y < grid.getHeight(); Y++){
			if(Y != y){
				Superposition s = grid.getPosition(x, Y);
				if(s.tilePossible(t)){
					if(s.isCollapsed()){
						counter.collapsedCount++;
					} else {
						counter.possibleCount++;
					}
				}
			}
		}
		return counter;
	}

	public Rule rotate(){
		return new FixedAmountPerRow(amount);
	}

	public Rule mirrorX(){
		return new FixedAmountPerColumn(amount);
	}
	
	public Rule mirrorY(){
		return new FixedAmountPerColumn(amount);
	}

	private class countResult{
		public int possibleCount;
		public int collapsedCount;

		countResult(){
			possibleCount = 0;
			collapsedCount = 0;
		}

		countResult(int pc, int cc){
			possibleCount = pc;
			collapsedCount = cc;
		}
	}
}