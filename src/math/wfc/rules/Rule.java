package math.wfc.rules;
import math.wfc.Gridstate;
import math.wfc.Tile;

/**
 * A Interface for functions on a rule.
 */
public abstract class Rule {
	/**
	 * A function to test if the Rule is fullfilled
	 * @param  grid the current Gridstate
	 * @param  x    the x coordinate of the position to test for
	 * @param  y    the y coordinate of the position to test for
	 * @param  t    the tile to test for at that position
	 * @return      The State of this rule (FORCED, POSSIBLE, IMPOSSIBLE or FORBIDDEN)
	 */
	public abstract STATE getState(Gridstate grid, int x, int y, Tile t);

	/**
	 * A function that rotates the rule clockwise.
	 * @return A rotated version of the rule
	 */
	public Rule rotate(){
		return this;
	}
	/**
	 * A function that mirrors the rule along the x-Axis
	 * @return A mirrored version of the rule
	 */
	public Rule mirrorX(){
		return this;
	}
	/**
	 * A function that mirrors the rule along the y-Axis
	 * @return A mirrores version of the rule
	 */
	public Rule mirrorY(){
		return this;
	}

	/**
	 * Enum used to output the State of the Rule.
	 */
	public enum STATE {
		/**
		 * When this is returned the Tile will instantly be placed at that position
		 */
		FORCED,
		/**
		 * When this is returned the Tile can be at this position. (Should be the default)
		 */
		POSSIBLE,
		/**
		 * The Tile can't be at this position anymore. The rule won't be executed at that position again
		 */
		IMPOSSIBLE,
		/**
		 * The Grid can't be filled anymore
		 */
		FORBIDDEN;
	}
}