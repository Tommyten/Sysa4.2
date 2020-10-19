import java.util.Arrays;
import java.util.HashMap;

public class Game {

    public static final int GRID_SIZE = 4;

    private boolean[][] currentGrid = new boolean[GRID_SIZE][GRID_SIZE];
    private boolean[][] nextGrid = new boolean[GRID_SIZE][GRID_SIZE];

    private final String initial;


    public Game(final String initial) {
        this.initial = initial;
        String[] rows = new String[GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            rows[i] = initial.substring(i*GRID_SIZE, i*GRID_SIZE+GRID_SIZE);
        }

        for (int y = 0; y < GRID_SIZE; y++) {
            String currentInitializer = rows[y];
            for (int x = 0; x < GRID_SIZE; x++) {
                currentGrid[x][y] = currentInitializer.charAt(x) == '1';
                nextGrid[x][y] = false;
            }
        }
    }

    public int getAssignmentNumber() {
        String assignmentStringBinary = "";
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                if(currentGrid[x][y]) assignmentStringBinary += '1';
                else assignmentStringBinary += '0';
            }
        }
        return Integer.parseInt(assignmentStringBinary, 2);
    }

    public void generateNextGeneration() {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                int neighbors = getNumOfNeighbors(x, y);
                if(neighbors > 3) nextGrid[x][y] = false;
                else if(neighbors == 3) nextGrid[x][y] = true;
                else if(neighbors == 2) nextGrid[x][y] = currentGrid[x][y];
                else nextGrid[x][y] = false;
            }
        }

        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                currentGrid[x][y] = nextGrid[x][y];
            }
        }
    }

    private int getNumOfNeighbors(int xPos, int yPos) {
        int neighbors = 0;
        if(yPos-1 >= 0) {
            if (xPos - 1 >= 0 && currentGrid[xPos - 1][yPos - 1]) neighbors++;
            if (currentGrid[xPos][yPos - 1]) neighbors++;
            if (xPos + 1 < GRID_SIZE && currentGrid[xPos + 1][yPos - 1]) neighbors++;
        }
        if (xPos - 1 >= 0 && currentGrid[xPos - 1][yPos]) neighbors++;
        if (xPos + 1 < GRID_SIZE && currentGrid[xPos + 1][yPos]) neighbors++;
        if(yPos+1 < GRID_SIZE) {
            if (xPos - 1 >= 0 && currentGrid[xPos - 1][yPos + 1]) neighbors++;
            if (currentGrid[xPos][yPos + 1]) neighbors++;
            if (xPos + 1 < GRID_SIZE && currentGrid[xPos + 1][yPos + 1]) neighbors++;
        }
        return neighbors;
    }

    public String getInitial() {
        return initial;
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Helper Functions -> not needed for the solution itself

    public void outputGridToConsole() {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                if(currentGrid[x][y]) {
                    System.out.print("1");
                } else {
                    System.out.print("0");
                }
                System.out.print(" ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
}
