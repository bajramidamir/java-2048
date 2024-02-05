package game2048;

import logic.GameManager;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        int size = chooseGridSize();
        
        if (size != -1) {
            GameManager gameManager = new GameManager(size);
            gameManager.startGame();
        } else {
            System.out.println("User canceled grid size selection.");
        }
    }

    private static int chooseGridSize() {
        String[] options = {"4x4", "5x5"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose Grid Size",
                "Grid Size",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        // Return the selected grid size (4 or 5)
        return choice == 0 ? 4 : (choice == 1 ? 5 : -1);
    }
}
