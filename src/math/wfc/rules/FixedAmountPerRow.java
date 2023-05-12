package math.wfc.rules;
import math.wfc.Gridstate;
import math.wfc.Tile;
import math.wfc.Superposition;

/**
 * A rule that forces a certain amount of a tile to be in each row of a grid.
 */
public class FixedAmountPerRow implements Rule {
	private final int amount;

	/**
	 * Constructer for a Rule that forces a certain count of tile to be in a certain row.
	 * @param  a The amount of tiles in each row
	 */
	public FixedAmountPerRow(int a){
		amount = a;
	}

	public boolean isPossible(Gridstate grid, int x, int y, Tile t){
		countResult c = countRow(grid, x, y, t);
		return c.collapsedCount < amount;
	}

	public boolean isForced(Gridstate grid, int x, int y, Tile t){
		countResult c = countRow(grid, x, y, t);
		return c.collapsedCount + c.possibleCount <= amount-1;
	}

	/**
	 * Counts the amount of a given tile in a row of the grid igonring the position the rule is calculated for
	 * @param  grid the current Gridstate
	 * @param  x    the x coordinate of the position to test for
	 * @param  y    the y coordinate of the position to test for
	 * @param  t    the tile to test for at that position
	 * @return      countResult object holding the amount of possible and collapsed Superpositions
	 */
	private countResult countRow(Gridstate grid, int x, int y, Tile t){
		countResult counter = new countResult(); 
		for(int X = 0; X < grid.getWidth(); X++){
			if(X != x){
				Superposition s = grid.getPosition(X, y);
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
		return new FixedAmountPerColumn(amount);
	}

	public Rule mirrorX(){
		return new FixedAmountPerRow(amount);
	}
	
	public Rule mirrorY(){
		return new FixedAmountPerRow(amount);
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