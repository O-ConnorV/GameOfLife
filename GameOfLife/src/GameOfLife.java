/*
  Simple implementation of John Conway's Game of Life.
  Starts with random seed and plays out on bounded grid
  (without wrap-around).

   User interactions:
     ENTER to evolve
     'A' to add a live cell
     'N' to display neighbor counts for each cell
     'Q' to end game

   TO DO:
         - Display the number of live cells and the number of evolutions.
         - Add presets to choose from at the beginning (ex: a simple glider, a gun,...)
         instead of choosing a size and a density.
 */

package GameOfLife.GameOfLife.src;
import java.util.Scanner;

public class GameOfLife
{
  public static void main(String[] args)
  {
    Scanner input = new Scanner(System.in);

    // user commands
    final String QUIT = "Q";
    final String ADD_CELL = "A";
    final String DISPLAY_NEIGHBOR_COUNTS = "N";

    // user input is stored here
    String command;

    // world setup
    boolean[][] cells = new boolean[4][2]; // init of the cells array
    boolean keepGoing = true;

    System.out.print("Press 1 if you want to start with a preset " +
            "and 2 if you want a random seed: ");
    int keyPressed = input.nextInt();

    if (keyPressed == 2) // random seed mode
    {
      System.out.print("Choose array size:\nwidth: ");
      final int GRID_WIDTH = input.nextInt();
      System.out.print("height: ");
      final int GRID_HEIGHT = input.nextInt();

      System.out.print("Choose the density: ");
      final double SEED_DENSITY = input.nextDouble();

      cells = randomSeed(GRID_HEIGHT, GRID_WIDTH, SEED_DENSITY);
    }
    else if (keyPressed == 1) // preset mode
    {
      System.out.print("Choose a preset:\n" +
              "   - Enter 1 if you want an empty grid\n" +
              "   - Enter 2 if you want to start with a simple glider\n" +
              "   - Enter 3 if you want to start with a simple gun\n");

      int choice = input.nextInt();

      if (choice == 1) // empty
        cells = new boolean[50][50];

      else if (choice == 2) // glider
      {
        cells = new boolean[50][50];

        cells[25][25] = true;
        cells[26][26] = true;
        cells[27][26] = true;
        cells[27][25] = true;
        cells[27][24] = true;
      }
      else if (choice == 3) // gun
      {
        //TODO: gun
        cells = new boolean[50][50];
      }
      else
      {
        System.out.print("Not a valid choice! Try again...");
        keepGoing = false;
      }
    }
    else
    {
      System.out.print("Not a valid answer! Try again...");
      keepGoing = false;
    }

    // main loop that keeps the game running
    while(keepGoing)
    {
      displayGrid(cells);
      command = input.nextLine().trim().toUpperCase();

      if(command.startsWith(QUIT))
        keepGoing = false;

      else if(command.startsWith(ADD_CELL))
      {
        System.out.print("row: ");
        int row = input.nextInt();
        System.out.print("col: ");
        int col = input.nextInt();
        input.nextLine();
        addCell(cells, row, col);
      }
      else if(command.startsWith(DISPLAY_NEIGHBOR_COUNTS))
        displayNeighborCounts(cells);
      else
        cells = evolve(cells);
    }
  }

  /*
     Takes current generation of cells and returns the next generation.
     Cells in top and bottom row and leftmost and rightmost columns of grid
     (i.e. border cells) do not evolve
  */
  public static boolean[][] evolve(boolean[][] cells)
  {
    boolean[][] nextGen = new boolean[cells.length][cells[0].length];

    for(int row = 1; row < nextGen.length-1; row++)
    {
      for(int col = 1; col < nextGen[0].length-1; col++)
      {
        int numNeighbors = countNeighbors(cells, row, col);

        // 1. Any live cell with fewer than two live neighbours dies.
        if(cells[row][col] && numNeighbors < 2)
          nextGen[row][col] = false;

          // 2. Any live cell with two or three live neighbours lives
          //   on to the next generation.
        else if(cells[row][col] && (numNeighbors == 2 || numNeighbors == 3))
          nextGen[row][col] = true;

          // 3. Any live cell with more than three live neighbours dies.
        else if(cells[row][col] && numNeighbors > 3)
          cells[row][col] = false;

          // 4. Any dead cell with exactly three live neighbours becomes alive.
        else if(!cells[row][col] && numNeighbors == 3)
          cells[row][col] = true;
      }
    }
    return nextGen;
  }

  //Makes the cell at specified location alive.
  public static void addCell(boolean[][] grid,
                             int row,
                             int col)
  {
    if(row >= 0 && row < grid.length && col >= 0 && col < grid[0].length)
      grid[row][col] = true;
  }

  /*
     Counts and returns the number of live cells (true is live) surrounding
     the cell at specified location. If the specified cell is next to a border
     the function detects the cells from the opposite border (like a torus).

     If X is the cell at (row, col), its neighbors are shown with 'n':

                  n n n
                  n X n
                  n n n

     Thus the number returned must be in [0, 8]
  */
  public static int countNeighbors(boolean[][] grid, int row, int col)
  {
    int count = 0;
    int width = grid[0].length - 2;
    int height = grid.length - 2;

    for(int i = -1; i <= 1; i++)
      for (int j = -1; j <= 1; j++)
      {
        if(i == 0 && j == 0)
          continue;

        int I = (row + i + height) % height;
        int J = (col + j + width) % width;

        if (grid[I][J])
          count++;
      }

    return count;
  }

  /*
     Returns grid with specified height and width, randomly seeded with
     live cells. The probability that a non-border cell is alive is equal
     to density.
  */
  public static boolean[][] randomSeed(int height,
                                       int width,
                                       double density)
  {
    boolean[][] grid = new boolean[height][width];

    for(int row = 1; row < grid.length-1; row++)
      for(int col = 1; col < grid[0].length-1; col++)
        grid[row][col] = Math.random() < density;
    return grid;
  }

  /*
     Prints the grid surrounded by a border. Live cells are represented
     with 'o', dead cells are white space.
  */
  public static void displayGrid(boolean[][] grid)
  {
    // top border
    displayHorizontalBorder(grid[0].length);

    for (int row = 1; row < grid.length-1; row++)
    {
      // left border
      System.out.print("| ");

      // cells on grid
      for (int col = 1; col < grid[0].length-1; col++)
        System.out.print(grid[row][col]? "o " : "  ");

      // right border
      System.out.println("|");
    }

    // bottom border
    displayHorizontalBorder(grid[0].length);
  }

  /*
     Displays the grid with each non-border cell showing the number
     of live neighbors around the cell. Useful for debugging!
  */
  public static void displayNeighborCounts(boolean[][] grid)
  {
    // top border
    displayHorizontalBorder(grid[0].length);

    for (int row = 1; row < grid.length-1; row++)
    {
      // left border
      System.out.print("| ");

      // neighbor counts for each cell
      for (int col = 1; col < grid[0].length-1; col++)
        System.out.print(countNeighbors(grid, row, col) + " ");

      // right border
      System.out.println("|");
    }

    // bottom border
    displayHorizontalBorder(grid[0].length);
  }

  /*
     Prints a line that looks like this:

         + - - - - - +

     with the number of dashes depending on width.
 */
  private static void displayHorizontalBorder(int width)
  {
    System.out.print("+ ");
    for(int i = 1; i < width-1; i++)
      System.out.print("- ");
    System.out.println("+");
  }
}