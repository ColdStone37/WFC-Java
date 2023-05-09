package math.wfc.tiles;
import math.wfc.Gridstate;

/**
 * A Interface for functions on a rule.
 */
public interface Rule {
	/**
	 * A function to test if the Rule is fullfilled
	 * @param  grid the current Gridstate
	 * @param  x    the x coordinate of the position to test for
	 * @param  y    the y coordinate of the position to test for
	 * @return      whether or not the rule is fullfilled
	 */
	public boolean isFullfilled(Gridstate grid, int x, int y);
	/**
	 * A function that rotates the rule 90 degrees clockwise
	 * to generate variations of a tile.
	 */
	public void rotate();
	/**
	 * A function that mirrors the rule along the x-Axis
	 * to generate variations of a tile.
	 */
	public void mirrorX();
	/**
	 * A function that mirrors the rule along the y-Axis
	 * to generate variations of a tile
	 */
	public void mirrorY();
}