package math.wfc;
import java.util.Random;
/**
 * A class storing the Superpositions in a grid.
 */
public class Gridstate {
	private final int width, height;
	private Superposition[][] grid;

	//border behaviour
	//loop = true  means that getPosition on the border of the grid
	//             will return a Superposition object on the other side
	//             of the grid.
	//loop = false means that all positions outside the grid will return
	//             the "borderSP" object.
	private boolean looping = false;
	private Superposition borderSP;

	private boolean[] triedChoices;

	/**
	 * Constructor for a Gridstate object with just dimensions and tileset given.
	 * @param  w       width of the grid
	 * @param  h       height of the grid
	 * @param  tileset the tileset to fill the grid with
	 */
	public Gridstate(int w, int h, Tile[] tileset){
		width = w;
		height = h;
		grid = new Superposition[w][h];
		for(int x = 0; x < w; x++)
			for(int y = 0; y < h; y++)
				grid[x][y] = new Superposition(x, y, tileset);
	}

	/**
	 * Constructer for a Gridstate object with all possible parameters.
	 * @param  grid     The grid of Superpositions
	 * @param  looping  Whether or not positions outside the grid should be looping around
	 * @param  borderSP If not the Superposition to return for positions outside the grid
	 */
	public Gridstate(Superposition[][] grid, boolean looping, Superposition borderSP){
		width = grid.length;
		height = grid[0].length;
		grid = grid;
		this.looping = looping;
		this.borderSP = borderSP;
	}

	/**
	 * Gets the Superposition object at a certain coordinate following
	 * the rules set by looping.
	 * @param  x x-Coordinate of the Position to get
	 * @param  y y-Coordinate of the Position to get
	 * @return   Superposition object at that coordinate
	 */
	public Superposition getPosition(int x, int y){
		if(!looping && (x < 0 || y < 0 || x >= width || y >= height))
			return borderSP;

		//wraps values inside grid
		x -= Math.floorDiv(x, width) * width;
		y -= Math.floorDiv(y, height) * height;
		return grid[x][y];
	}

	/**
	 * Collapses the State until a random choice is needed or the grid
	 * can't be finished.
	 * @return  true if it stopped because the grid can't be finished
	 */
	public boolean collapse(){
		boolean collapsedSomething = true;
		while(collapsedSomething){
			collapsedSomething = false;
			
			for(int x = 0; x < width; x++) {
				for(int y = 0; y < height; y++) {
					Superposition s = grid[x][y];
					collapsedSomething |= s.collapsePossibilities(this);

					if(s.getPossibilityCount() == 0)
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * Tests if the grid is fully collapsed.
	 * @return true if every Superposition is fully collapsed
	 */
	public boolean isFinished(){
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				if(!grid[x][y].isCollapsed())
					return false;
		return true;
	}

	/**
	 * Chooses a random possibility of a random Superposition that
	 * hasn't collapsed yet and collapses it down. If the function is executed
	 * multiple times it will collapse to a different State every time and return
	 * null if there are no States left.
	 * @param  rand A java.util.Random object to use for randomisation
	 * @return A new Gridstate that has one random Superposition collapsed
	 */
	public Gridstate makeRandomChoice(Random rand){
		//count possibilities
		int totalPossibilities = 0;
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				if(!grid[x][y].isCollapsed())
					totalPossibilities += grid[x][y].getPossibilityCount();
	
		//makes sure that every possibility is only tried once
		int chooseFrom = totalPossibilities;
		if(triedChoices == null || triedChoices.length != totalPossibilities){
			triedChoices = new boolean[totalPossibilities];
		} else {
			chooseFrom = 0;
			for(int i = 0; i < triedChoices.length; i++)
				if(!triedChoices[i])
					chooseFrom++;
		}

		if(chooseFrom == 0)
			return null;

		int chosenPossibility = rand.nextInt(chooseFrom);
		//converts index from the remaining list to the total list
		for(int i = 0; i < totalPossibilities; i++){
			if(!triedChoices[i]){
				if(chosenPossibility == 0){
					chosenPossibility = i;
				}
				chosenPossibility--;
			}
		}

		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				if(!grid[x][y].isCollapsed()){
					int posCount = grid[x][y].getPossibilityCount();
					if(chosenPossibility < posCount){
						Superposition[][] cloneGrid = cloneGrid();
						cloneGrid[x][y].collapseTo(chosenPossibility);
						return new Gridstate(cloneGrid, looping, borderSP);
					}
					chosenPossibility -= posCount;
				}

		//should not happen
		return null;
	}

	/**
	 * Overwrites the clone function from Object.
	 * @return Clone of the Gridstate object
	 */
	public Object clone(){
		return new Gridstate(cloneGrid(), looping, borderSP);
	}

	/**
	 * Clones the grid.
	 * @return Clone of the 2D-Array of Superpositions
	 */
	private Superposition[][] cloneGrid(){
		Superposition[][] cloneGrid = new Superposition[width][height];
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				cloneGrid[x][y] = (Superposition) grid[x][y].clone();
		return cloneGrid;
	}
}