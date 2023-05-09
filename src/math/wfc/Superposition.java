package math.wfc;
import math.wfc.tiles.Tile;

/**
 * A Class storing a single superposition of multiple tiles.
 */
public class Superposition {
	private final Tile[] tileset;
	private boolean[] possibleTiles;

	/**
	 * Function to set the tileset.
	 * @param tileset Array of all tiles
	 */
	public static void setTileSet(Tile[] tileset){
		this.tileset = tileset;
	}
}