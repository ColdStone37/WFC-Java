package math.wfc.rules;
import math.wfc.Gridstate;
import math.wfc.Tile;

/**
 * A Interface for functions on a rule.
 */
public interface Rule {
	/**
	 * A function to test if the Rule is fullfilled
	 * @param  grid the current Gridstate
	 * @param  x    the x coordinate of the position to test for
	 * @param  y    the y coordinate of the position to test for
	 * @param  t    the tile to test for at that position
	 * @return      whether or not the rule is fullfilled
	 */
	public boolean isPossible(Gridstate grid, int x, int y, Tile t);

	/**
	 * A fuction to test if the rule forces the given Tile
	 * @param  grid the current Gridstate
	 * @param  x    the x coordinate of the position to test for
	 * @param  y    the y coordinate of the position to test for
	 * @param  t    the tile to test for at that position
	 * @return      whether or not the rule forced the tile at that position
	 */
	public boolean isForced(Gridstate grid, int x, int y, Tile t);
	/**
	 * A function that rotates the rule clockwise.
	 * @return A rotated version of the rule
	 */
	public Rule rotate();
	/**
	 * A function that mirrors the rule along the x-Axis
	 * @return A mirrored version of the rule
	 */
	public Rule mirrorX();
	/**
	 * A function that mirrors the rule along the y-Axis
	 * @return A mirrores version of the rule
	 */
	public Rule mirrorY();
}