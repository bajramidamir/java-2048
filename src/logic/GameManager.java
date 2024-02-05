package logic;

import gui.GameFrame;

/**
 * Manages the game logic for the 2048 game, including the main game loop
 * and interactions with the user interface.
 */
public class GameManager {
    /** The game board. */
    private Board board;

    /** The graphical user interface for the game. */
    private GameFrame gameFrame;

    /** The current score of the game. */
    private int score;

    /**
     * Constructs a new GameManager with the specified grid size.
     * Initializes the game board, graphical user interface, and score.
     *
     * @param size The size of the game board (4 or 5).
     */
    public GameManager(int size) {
        board = new Board(size);
        gameFrame = new GameFrame(size, board);
        score = 0;
    }

    /**
     * Starts the main game loop and manages the game flow.
     * The loop continues until the game is over.
     */
    public void startGame() {
        boolean gameOver = false;

        while (!gameOver) {
            gameFrame.updateBoard(board.getGrid());
        }
        
        if (board.isBoardFull()) {
            gameOver = true;
        }
    }

    /**
     * Updates the game score and reflects the changes in the user interface.
     *
     * @param additionalScore The additional score to be added.
     */
    public void updateScore(int additionalScore) {
        score += additionalScore;
        gameFrame.updateScore(score);
    }
}
