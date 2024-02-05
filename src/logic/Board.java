package logic;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the game board for the 2048 game, including grid management,
 * scoring, and game state.
 */
public class Board {
    /**
     * The 2D array representing the game grid.
     */
    private final int[][] grid;

    /**
     * The size of the game grid (4 or 5).
     */
    private int size;

    /**
     * Random number generator for tile generation.
     */
    private final Random random;

    /**
     * The current score of the game.
     */
    private int score;

    /**
     * The highest score achieved in the game.
     */
    private int highScore;

    /**
     * File path for storing the high score.
     */
    private static final String HIGH_SCORE_FILE = "highscore.txt";

    /**
     * Constructs a new game board with the specified size.
     * Initializes the grid, random number generator, score, and high score.
     * Generates two initial tiles.
     *
     * @param size The size of the game board (4 or 5).
     */
    public Board(int size) {
        this.size = size;
        grid = new int[size][size];
        random = new Random();
        score = 0;
        highScore = loadHighScore();
        generateNewTile();
        generateNewTile();
    }

    /**
     * Gets the size of the game board.
     *
     * @return The size of the game board.
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of the game board.
     *
     * @param size The new size of the game board.
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Gets the 2D array representing the game grid.
     *
     * @return The game grid.
     */
    public int[][] getGrid() {
        return grid;
    }

    /**
     * Gets the current score of the game.
     *
     * @return The current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the highest score achieved in the game.
     *
     * @return The highest score.
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Loads the high score from a file.
     *
     * @return The loaded high score.
     */
    public int loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORE_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line.trim());
            }
        } catch (IOException | NumberFormatException e) {
            // Handle the exception (e.g., file not found or invalid content)
            e.printStackTrace();
        }
        return 0; // Default value if high score cannot be loaded
    }

    /**
     * Saves the current high score to a file.
     */
    public void saveHighScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE))) {
            writer.write(Integer.toString(highScore));
        } catch (IOException e) {
            // Handle the exception (e.g., unable to write to file)
            e.printStackTrace();
        }
    }

    /**
     * Resets the high score to zero and saves the updated high score to the file.
     */
    public void resetHighScore() {
        highScore = 0;
        saveHighScore(); // Save the updated high score to the file
    }


    /**
     * Moves the tiles on the game board to the left, merging and updating the score.
     * Generates a new tile if any movement occurs.
     */
    public void moveLeft() {
        boolean moved = false;
        for (int row = 0; row < size; row++) {
            for (int col = 1; col < size; col++) {
                if (grid[row][col] != 0) {
                    int current = col;
                    while (current - 1 >= 0 && grid[row][current - 1] == 0) {
                        // Move to an empty cell to the left
                        grid[row][current - 1] = grid[row][current];
                        grid[row][current] = 0;
                        current--;
                        moved = true;
                    }

                    if (current - 1 >= 0 && grid[row][current - 1] == grid[row][current]) {
                        // Merge with the same value to the left
                        grid[row][current - 1] *= 2;
                        grid[row][current] = 0;
                        score += grid[row][current - 1];
                    }
                }
            }
        }
        
        if (score > highScore) {
            highScore = score;
            saveHighScore();
        }

        if (moved) {
            generateNewTile(); // Generate new tile after each move
        }
    }

    /**
     * Moves the tiles on the game board to the right, merging and updating the score.
     * Generates a new tile if any movement occurs.
     */
    public void moveRight() {
        boolean moved = false;
        for (int row = 0; row < size; row++) {
            for (int col = size - 2; col >= 0; col--) {
                if (grid[row][col] != 0) {
                    int current = col;
                    while (current + 1 < size && grid[row][current + 1] == 0) {
                        // Move to an empty cell to the right
                        grid[row][current + 1] = grid[row][current];
                        grid[row][current] = 0;
                        current++;
                        moved = true;
                    }

                    if (current + 1 < size && grid[row][current + 1] == grid[row][current]) {
                        // Merge with the same value to the right
                        grid[row][current + 1] *= 2;
                        grid[row][current] = 0;
                        score += grid[row][current + 1];
                    }
                }
            }
        }
        
        if (score > highScore) {
            highScore = score;
            saveHighScore();
        }

        if (moved) {
            generateNewTile(); // Generate new tile after each move
        }
    }

    /**
     * Moves the tiles on the game board upwards, merging and updating the score.
     * Generates a new tile if any movement occurs.
     */
    public void moveUp() {
        boolean moved = false;
        for (int col = 0; col < size; col++) {
            for (int row = 1; row < size; row++) {
                if (grid[row][col] != 0) {
                    int current = row;
                    while (current - 1 >= 0 && grid[current - 1][col] == 0) {
                        // Move to an empty cell upwards
                        grid[current - 1][col] = grid[current][col];
                        grid[current][col] = 0;
                        current--;
                        moved = true;
                    }

                    if (current - 1 >= 0 && grid[current - 1][col] == grid[current][col]) {
                        // Merge with the same value upwards
                        grid[current - 1][col] *= 2;
                        grid[current][col] = 0;
                        score += grid[current - 1][col];
                    }
                }
            }
        }
        
        if (score > highScore) {
            highScore = score;
            saveHighScore();
        }

        if (moved) {
            generateNewTile(); // Generate new tile after each move
        }
    }

    /**
     * Moves the tiles on the game board downwards, merging and updating the score.
     * Generates a new tile if any movement occurs.
     */
    public void moveDown() {
        boolean moved = false;
        for (int col = 0; col < size; col++) {
            for (int row = size - 2; row >= 0; row--) {
                if (grid[row][col] != 0) {
                    int current = row;
                    while (current + 1 < size && grid[current + 1][col] == 0) {
                        // Move to an empty cell downwards
                        grid[current + 1][col] = grid[current][col];
                        grid[current][col] = 0;
                        current++;
                        moved = true;
                    }

                    if (current + 1 < size && grid[current + 1][col] == grid[current][col]) {
                        // Merge with the same value downwards
                        grid[current + 1][col] *= 2;
                        grid[current][col] = 0;
                        score += grid[current + 1][col];
                    }
                }
            }
        }
        
        if (score > highScore) {
            highScore = score;
            saveHighScore();
        }

        if (moved) {
            generateNewTile(); // Generate new tile after each move
        }
    }

    
    /**
     * Resets the game board by clearing the grid, resetting the score, and generating two initial tiles.
     */
    public void reset() {
        // Clear the grid
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = 0;
            }
        }

        // Reset the score
        score = 0;

        // Generate two initial tiles
        generateNewTile();
        generateNewTile();
    }

    /**
     * Checks if the game board is full with no empty spaces, indicating a potential loss condition.
     *
     * @return True if the board is full, false otherwise.
     */
    public boolean isBoardFull() {
        // Check if the board is full (no empty spaces)
        for (int[] row : grid) {
            for (int value : row) {
                if (value == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the win condition is met, typically when a tile with the value 2048 is achieved.
     *
     * @return True if the win condition is met, false otherwise.
     */
    public boolean isWinConditionMet() {
        // Check if the win condition is met (tile with the value 2048)
        for (int[] row : grid) {
            for (int value : row) {
                if (value == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Generates a new tile (2 or 4) in an empty spot on the game board.
     */
    public void generateNewTile() {
        // Find empty spots on the board
        List<Point> emptySpots = new ArrayList<>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid[row][col] == 0) {
                    emptySpots.add(new Point(row, col));
                }
            }
        }

        // Check if there are empty spots to place a new tile
        if (!emptySpots.isEmpty()) {
            // Generate a random position for the new tile
            int randomPosition = random.nextInt(emptySpots.size());

            // Place a new tile (2 or 4) at the randomly chosen empty spot
            Point selectedSpot = emptySpots.get(randomPosition);
            grid[selectedSpot.x][selectedSpot.y] = (random.nextInt(2) + 1) * 2;
        }
    }

    /**
     * Checks if the game can still be moved, i.e., if there are empty spots or adjacent tiles with the same value.
     *
     * @return True if the game can still be moved, false if there are no empty spots and no adjacent tiles with the same value.
     */
    public boolean canMove() {
        // Check if there are empty spots or if there are adjacent tiles with the same value
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid[row][col] == 0) {
                    return true;  // There's an empty spot, so the game can continue
                }
                // Check adjacent tiles for the same value
                if (col < size - 1 && grid[row][col] == grid[row][col + 1]) {
                    return true;  // Tiles with the same value are adjacent, so the game can continue
                }
                if (row < size - 1 && grid[row][col] == grid[row + 1][col]) {
                    return true;  // Tiles with the same value are adjacent, so the game can continue
                }
            }
        }
        return false;  // No empty spots and no adjacent tiles with the same value, indicating a game over
    }
}
