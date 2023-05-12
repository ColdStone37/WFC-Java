package math.wfc;
import java.util.Stack;
import java.util.Random;

/**
 * Class that handles the main Wave function collapse operations.
 */
public class WFC_Handler {
    private Gridstate currentGrid;
    private Stack<Gridstate> history;
    private Random random;

    /**
     * Constructor for a WFC Handler
     * @param  w       width of grid
     * @param  h       height of grid
     * @param  tileset tileset to fill the grid with
     */
    public WFC_Handler(int w, int h, Tile[] tileset) {
        currentGrid = new Gridstate(w, h, tileset);
        history = new Stack<Gridstate>();
        random = new Random();
    }

    public WFC_Handler(Gridstate g) {
        currentGrid = g;
        history = new Stack<Gridstate>();
        random = new Random();
    }

    /**
     * Runs Wave function collapse algorithm.
     * @return  true if the algorithm terminted successfully otherwise false
     */
    public boolean wfc() {
        while(true){
            if(currentGrid.collapse()){
                if(history.empty())
                    return false;
                currentGrid = history.pop();
            } else {
                if(currentGrid.isFinished())
                    return true;
            }
            //make random choice and push current to history, if there is no choice left go back up
            Gridstate newState = currentGrid.makeRandomChoice(random);
            while(newState == null){
                if(history.empty())
                    return false;
                currentGrid = history.pop();
                newState = currentGrid.makeRandomChoice(random);
            }
            history.push(currentGrid);
            currentGrid = newState;
        }
    }

    /**
     * A function to get the finished Grid.
     * @return Grid of tiles
     */
    public Tile[][] getGrid(){
        if(currentGrid.isFinished()){
            Tile[][] out = new Tile[currentGrid.getWidth()][currentGrid.getHeight()];
            for(int x = 0; x < currentGrid.getWidth(); x++) {
                for(int y = 0; y < currentGrid.getHeight(); y++){
                    out[x][y] = currentGrid.getPosition(x, y).getTile();
                }
            }
            return out;
        } else {
            return null;
        }
    }

    /**
     * Prints the current Grid.
     */
    public void printGrid(){
        System.out.println(currentGrid);
    }
}