package math.wfc;
import math.wfc.rules.Rule;

/**
 * A Class to store data about a Tile.
 */
public class Tile {
	private final int id;
	private Rule[] rules;
	/**
	 * Default Constructer that initializes a Tile without rules.
	 * @param  id the id of the Tile
	 */
	public Tile(int id) {
		this.id = id;
		rules = new Rule[0];
	}

	/**
	 * Constructer that takes a list of rules for the given Tile.
	 * @param  id    the id of the Tile
	 * @param  rules the rules to add to the Tile
	 */
	public Tile(int id, Rule[] rules) {
		this.rules = new Rule[rules.length];
		this.id = id;
		for(int i = 0; i < rules.length; i++)
			this.rules[i] = rules[i];
	}

	/**
	 * Function to see if all Rules for this tile are fullfilles at a certain position
	 * @param  grid current gridstate
	 * @param  x    x-coordinate of Superposition
	 * @param  y    y-coordinate of Superposition
	 * @return true if the tile can be at this position
	 */
	public boolean isPossible(Gridstate grid, int x, int y) {
		for(Rule r:rules)
			if(!r.isPossible(grid, x, y, this))
				return false;
		return true;
	}

	/**
	 * Function to see if the current tile is forced
	 * @param  grid current gridstate
	 * @param  x    x-coordinate of Superposition
	 * @param  y    y-coordinate of Superposition
	 * @return true if the tile has to be at this position
	 */
	public boolean isForced(Gridstate grid, int x, int y) {
		for(Rule r:rules)
			if(r.isForced(grid, x, y, this))
				return true;
		return false;
	}

	/**
	 * Getter Function for the id of the tile.
	 * @return id of the tile that was set in the Constructor
	 */
	public int getId(){
		return id;
	}
}