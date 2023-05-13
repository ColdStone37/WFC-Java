package math.wfc;
import math.wfc.rules.Rule;

/**
 * A Class storing a single superposition of multiple tiles.
 */
public class Superposition {
	private final Tile[] tileset;
	private final boolean[] possibleTiles;
	private final int x, y;
	private int possibilityCount;

	/**
	 * Constructor for a Superposition object.
	 * @param  x       the x-coordinate of the Superposition
	 * @param  y       the y-coordinate of the Superposition
	 * @param  tileset the possibilities for the Superposition
	 */
	public Superposition(int x, int y, Tile[] tileset) {
		this.tileset = tileset;
		this.x = x;
		this.y = y;
		possibilityCount = tileset.length;
		possibleTiles = new boolean[tileset.length];
		for(int i = 0; i < possibleTiles.length; i++)
			possibleTiles[i] = true;
	}

	/**
	 * Constructor for a Superposition object with possibilities given.
	 * If the dimensions of tileset and possibilities mismatch
	 * the given possibilities parameter will be ignored.
	 * @param  x             the x-coordinate of the Superposition
	 * @param  y             the y-coordinate of the Superposition
	 * @param  tileset       the possibilities for the Superposition
	 * @param  possibilities the possibilities
	 */
	public Superposition(int x, int y, Tile[] tileset, boolean[] possibilities) {
		this.tileset = tileset;
		this.x = x;
		this.y = y;
		possibilityCount = tileset.length;
		possibleTiles = new boolean[tileset.length];
		if(tileset.length == possibilities.length){
			for(int i = 0; i < possibleTiles.length; i++)
				possibleTiles[i] = possibilities[i];
		} else {
			for(int i = 0; i < possibleTiles.length; i++)
				possibleTiles[i] = true;
		}
	}

	/**
	 * Collapses possibilities for the position.
	 * @param  grid The grid of Superpositions
	 * @return      Whether or not anything was collapsed
	 */
	public boolean collapsePossibilities(Gridstate grid) {
		if(isCollapsed())
			return false;
		boolean collapsed = false;
		for(int i = 0; i < possibleTiles.length; i++)
			if(possibleTiles[i]) {
				Rule.STATE s = tileset[i].getState(grid, x, y);
				switch(s){
				case FORCED:
					collapseTo(i);
					collapsed = true;
					break;
				case IMPOSSIBLE:
					possibleTiles[i] = false;
					collapsed = true;
					possibilityCount--;
					break;
				case FORBIDDEN:
					possibilityCount = 0;
					return true;
				default:
					break;
				}
			}
		return collapsed;
	}

	/**
	 * Collapses the Superposition to one of the still possible states.
	 * If the given parameter is higher than the number of possible States left
	 * (or less than 0) the Superposition will be empty afterwards.
	 * @param possibility The index of the State to collapse to
	 */
	public void collapseToFromPossible(int possibility){
		int countPossibilities = 0;
		possibilityCount = 0;
		for(int i = 0; i < possibleTiles.length; i++){
			if(possibleTiles[i]){
				if(countPossibilities != possibility){
					possibleTiles[i] = false;
				} else {
					possibilityCount++;
				}
				countPossibilities++;
			}
		}
	}

	/**
	 * Collapses the Superposition to one possibility.
	 * @param possibility The index of the State to collapse to
	 */
	public void collapseTo(int possibility){
		possibilityCount = 0;
		for(int i = 0; i < possibleTiles.length; i++){
			if(i == possibility){
				possibleTiles[i] = true;
				possibilityCount++;
			} else {
				possibleTiles[i] = false;
			}
		}
	}

	/**
	 * Gets the count of possibilities left for the Position.
	 * @return Count of possibilities remaining
	 */
	public int getPossibilityCount(){
		return possibilityCount;
	}

	/**
	 * To test if the wave function has fully collapsed in this position (equivalent to getPossibilityCount() == 1).
	 * @return Whether or not the Position is fully collapsed
	 */
	public boolean isCollapsed(){
		return possibilityCount == 1;
	}

	/**
	 * Overwrites the clone function inherited from Object.
	 * @return Clone of the current Superposition
	 */
	public Object clone(){
		return new Superposition(x, y, tileset, possibleTiles);
	}

	/**
	 * Tests whether a given tile is still possible at this position.
	 * @param  t The tile to test for
	 * @return   true if it is still possible
	 */
	public boolean tilePossible(Tile t){
		for(int i = 0; i < possibleTiles.length; i++)
			if(t == tileset[i])
				return possibleTiles[i];
		return false;
	}

	/**
	 * Gets the tile a Superposition has collapsed to.
	 * @return Returns the tile a Superposition has collapsed to or null if it hasn't collapsed
	 */
	public Tile getTile(){
		if(!isCollapsed())
			return null;
		for(int i = 0; i < possibleTiles.length; i++){
			if(possibleTiles[i])
				return tileset[i];
		}
		return null;
	}
}