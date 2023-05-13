package math.wfc.rules;
import math.wfc.Gridstate;
import math.wfc.Tile;
import math.wfc.Superposition;

/**
 * A Rule that forces a certain amount of tiles to be inside each width x height area of the grid.
 */
public class FixedAmountPerBlock extends Rule {
	private final int amount;
	private final int blockWidth, blockHeight;

	/**
	 * Constructer for a Rule that forces a certain count of tile to be in a certain row.
	 * @param  a The amount of tiles in each row
	 */
	public FixedAmountPerBlock(int a, int w, int h){
		amount = a;
		blockWidth = w;
		blockHeight = h;
	}

	public STATE getState(Gridstate grid, int x, int y, Tile t){
		countResult c = countBlock(grid, x, y, t);
		if(c.collapsedCount > amount || c.collapsedCount + c.possibleCount < amount-1)
			return STATE.FORBIDDEN;
		if(c.collapsedCount == amount)
			return STATE.IMPOSSIBLE;
		if(c.collapsedCount + c.possibleCount == amount-1)
			return STATE.FORCED;
		return STATE.POSSIBLE;
	}

	/**
	 * Counts the amount of a given tile in a row of the grid igonring the position the rule is calculated for
	 * @param  grid the current Gridstate
	 * @param  x    the x coordinate of the position to test for
	 * @param  y    the y coordinate of the position to test for
	 * @param  t    the tile to test for at that position
	 * @return      countResult object holding the amount of possible and collapsed Superpositions
	 */
	private countResult countBlock(Gridstate grid, int x, int y, Tile t){
		countResult counter = new countResult();
		int blockX = x / blockWidth;
		int blockY = y / blockHeight;
		for(int X = blockX * blockWidth; X < (blockX + 1) * blockWidth; X++){
			for(int Y = blockY * blockHeight; Y < (blockY + 1) * blockHeight; Y++){
				if(!(x == X && y == Y)){
					Superposition s = grid.getPosition(X, Y);
					if(s.tilePossible(t)){
						if(s.isCollapsed()){
							counter.collapsedCount++;
						} else {
							counter.possibleCount++;
						}
					}
				}
			}
		}
		return counter;
	}

	public Rule rotate(){
		return new FixedAmountPerBlock(amount, blockWidth, blockHeight);
	}

	public Rule mirrorX(){
		return new FixedAmountPerBlock(amount, blockWidth, blockHeight);
	}
	
	public Rule mirrorY(){
		return new FixedAmountPerBlock(amount, blockWidth, blockHeight);
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