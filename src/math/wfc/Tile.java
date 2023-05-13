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
	 * @return Rule.STATE object with result of all included rules
	 */
	public Rule.STATE getState(Gridstate grid, int x, int y) {
		boolean possible = true;
		for(Rule r:rules){
			Rule.STATE s = r.getState(grid, x, y, this);
			if(s == Rule.STATE.FORBIDDEN || s == Rule.STATE.FORCED)
				return s;
			if(s == Rule.STATE.IMPOSSIBLE)
				possible = false;
		}
		if(possible){
			return Rule.STATE.POSSIBLE;
		} else {
			return Rule.STATE.IMPOSSIBLE;
		}
	}

	/**
	 * Getter Function for the id of the tile.
	 * @return id of the tile that was set in the Constructor
	 */
	public int getId(){
		return id;
	}
}