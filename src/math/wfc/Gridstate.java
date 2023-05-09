package math.wfc;
/**
 * A class storing the Superpositions in a grid.
 */
public class Gridstate {
	private final int width, height;
	private Superposition[][] grid;

	Gridstate(int w, int h){	
		width = w;
		height = h;
		grid = new Superposition[w][h];
	}
}