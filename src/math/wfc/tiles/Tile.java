package math.wfc.tiles;
import math.wfc.Gridstate;

/**
 * A Class to store data about a Tile.
 */
public class Tile {
	private Rule[] rules;
	/**
	 * Default Constructer that initializes a Tile without rules.
	 */
	public Tile() {
		rules = new Rule[0];
	}

	/**
	 * Constructer that takes a list of rules for the given Tile.
	 * @param  rules the rules to add to the Tile
	 */
	public Tile(Rule[] rules) {
		this.rules = new Rule[rules.length];
		for(int i = 0; i < rules.length; i++)
			this.rules[i] = rules[i];
	}

	/**
	 * Function to see if all Rules for this tile are fullfilles at a certain position
	 * @param  grid [description]
	 * @param  x    [description]
	 * @param  y    [description]
	 * @return      [description]
	 */
	public boolean isPossible(Gridstate grid, int x, int y) {
		for(Rule r:rules)
			if(!r.isFullfilled(grid, x, y))
				return false;
		return true;
	}
}