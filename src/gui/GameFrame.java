package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import logic.Board;

/**
 * Represents the graphical user interface (GUI) for the 2048 game.
 */
public class GameFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel mainPanel;
    private JPanel scorePanel;
    private JPanel resetPanel;
    private JPanel gamePanel;
    private JButton resetButton;
    private TileButton[][] tileButtons;
    private JLabel scoreLabel;
    private JLabel highScoreLabel;
    private JLabel gameOverLabel;
    private JLabel goalLabel;
    private Board board;

    /**
     * Constructs a new GameFrame with the specified size and associated game board.
     *
     * @param size  The size of the game board.
     * @param board The game board associated with the frame.
     */
    public GameFrame(int size, Board board) {
        super("2048 - Damir Bajrami - Razvoj softvera");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.board = board; // Set the Board reference
        mainPanel = new JPanel(new BorderLayout());

        // Score and High Score Panel
        scorePanel = new JPanel();
        scoreLabel = new JLabel("Score: 0");
        scorePanel.add(scoreLabel);
        highScoreLabel = new JLabel("Highscore: 0");
        scorePanel.add(highScoreLabel);
        mainPanel.add(scorePanel, BorderLayout.NORTH);

        // Game reset panel
        resetPanel = new JPanel();
        resetPanel.setLayout(new BoxLayout(resetPanel, BoxLayout.Y_AXIS));

        resetButton = new JButton("Reset Game");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        gameOverLabel = new JLabel("");
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 20));

        goalLabel = new JLabel("");
        goalLabel.setForeground(Color.GREEN);
        goalLabel.setFont(new Font("Arial", Font.BOLD, 20));

        resetPanel.add(resetButton);
        resetPanel.add(gameOverLabel);
        resetPanel.add(goalLabel);

        mainPanel.add(resetPanel, BorderLayout.LINE_START);

        // Game Panel
        gamePanel = new JPanel(new GridLayout(size, size));
        tileButtons = new TileButton[size][size];

        // Create and add TileButtons to the gamePanel
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tileButtons[i][j] = new TileButton(0);
                gamePanel.add(tileButtons[i][j]);
            }
        }

        mainPanel.add(gamePanel, BorderLayout.CENTER);
        // Add key bindings
        createKeyBindings();

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        updateHighScore(board.getHighScore()); // Load the highscore at startup
    }

    /**
     * Creates key bindings for arrow keys to handle game movements.
     */
    private void createKeyBindings() {
        InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getRootPane().getActionMap();

        // Define key bindings for arrow keys
        KeyStroke upKey = KeyStroke.getKeyStroke("UP");
        KeyStroke downKey = KeyStroke.getKeyStroke("DOWN");
        KeyStroke leftKey = KeyStroke.getKeyStroke("LEFT");
        KeyStroke rightKey = KeyStroke.getKeyStroke("RIGHT");

        inputMap.put(upKey, "moveUp");
        inputMap.put(downKey, "moveDown");
        inputMap.put(leftKey, "moveLeft");
        inputMap.put(rightKey, "moveRight");

        actionMap.put("moveUp", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                board.moveUp(); // Delegate to the Board's moveUp method
                updateUI(); // Update the UI after the move
            }
        });

        actionMap.put("moveDown", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                board.moveDown(); // Delegate to the Board's moveDown method
                updateUI(); // Update the UI after the move
            }
        });

        actionMap.put("moveLeft", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                board.moveLeft(); // Delegate to the Board's moveLeft method
                updateUI(); // Update the UI after the move
            }
        });

        actionMap.put("moveRight", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                board.moveRight(); // Delegate to the Board's moveRight method
                updateUI(); // Update the UI after the move
            }
        });
    }

    /**
     * Resets the game when the reset button is pressed.
     */
    public void resetGame() {
        board.reset();
        updateBoard(board.getGrid());
        updateScore(0);
        updateGameOverLabel();
        updateGoalLabel();
    }

    /**
     * Updates the game over label based on the game state.
     */
    public void updateGameOverLabel() {
        if (!board.canMove()) {
            gameOverLabel.setText("Game Over!");
        } else {
            gameOverLabel.setText("");
        }
    }

    /**
     * Updates the goal label based on whether the win condition is met.
     */
    public void updateGoalLabel() {
        if (board.isWinConditionMet()) {
            goalLabel.setText("Goal reached!");
        } else {
            goalLabel.setText("");
        }
    }

    /**
     * Updates the score label with the current score.
     *
     * @param score The current score.
     */
    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    /**
     * Updates the high score label with the current high score.
     *
     * @param highScore The current high score.
     */
    public void updateHighScore(int highScore) {
        highScoreLabel.setText("Highscore: " + highScore);
    }

    /**
     * Updates the entire graphical user interface (UI) based on the current state of the game.
     */
    public void updateUI() {
        updateGameOverLabel();
        updateGoalLabel();
        updateScore(board.getScore());
        updateHighScore(board.getHighScore());
        updateBoard(board.getGrid());
    }

    /**
     * Updates the game board in the GUI with the provided board state.
     *
     * @param board The current state of the game board.
     */
    public void updateBoard(int[][] board) {
        for (int i = 0; i < tileButtons.length; i++) {
            for (int j = 0; j < tileButtons[i].length; j++) {
                tileButtons[i][j].setValue(board[i][j]);
                tileButtons[i][j].repaint();
            }
        }
    }
}

