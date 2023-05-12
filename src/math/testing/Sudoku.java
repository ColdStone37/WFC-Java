package math.testing;
import math.wfc.*;
import math.wfc.rules.*;
import java.util.Scanner;

/**
 * A Sudoku generator/solver.
 */
public class Sudoku {
	private final static int SIZE = 3;

	/**
	 * Main function that can solve or generate sudokus. If no parameter is passed it will
	 * generate a Sudoku. If "solve" is passed as the first parameter it will let the user
	 * input a sudoku and solve it.
	 * @param args arguments
	 */
	public static void main(String[] args){
		Rule[] rules = new Rule[3];
		Tile[] tileset = new Tile[SIZE * SIZE];
		rules[0] = new FixedAmountPerRow(1);
		rules[1] = new FixedAmountPerColumn(1);
		rules[2] = new FixedAmountPerBlock(1, SIZE, SIZE);
		for(int i = 0; i < SIZE * SIZE; i++){
			tileset[i] = new Tile(i+1, rules);
		}
		Gridstate sudoku = new Gridstate(SIZE * SIZE, SIZE * SIZE, tileset);

		if(args.length > 0 && args[0] == "solve"){
			Scanner sc = new Scanner(System.in);
			for(int y = 0; y < SIZE * SIZE; y++){
				for(int x = 0; x < SIZE * SIZE; x++){
					int i = sc.nextInt();
					if(i >= 1 && i <= SIZE * SIZE){
						sudoku.getPosition(x, y).collapseTo(i-1);
					}
				}
			}
		}


		WFC_Handler solver = new WFC_Handler(sudoku);
		solver.wfc();
		System.out.println("Sudoku:");
		solver.printGrid();
	}
}