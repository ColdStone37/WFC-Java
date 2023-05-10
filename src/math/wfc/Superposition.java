package math.wfc;

/**
 * A Class storing a single superposition of multiple tiles.
 */
class Superposition {
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
		boolean collapsed = false;
		for(int i = 0; i < possibleTiles.length; i++)
			if(possibleTiles[i])
				if(!tileset[i].isPossible(grid, x, y)) {
					possibleTiles[i] = false;
					collapsed = true;
					possibilityCount--;
				}
		return collapsed;
	}

	/**
	 * Collapses the Superposition to one of the still possible states.
	 * If the given parameter is higher than the number of possible States left
	 * (or less than 0) the Superposition will be empty afterwards.
	 * @param possibility The index of the State to collapse to
	 */
	public void collapseTo(int possibility){
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
}