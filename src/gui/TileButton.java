package gui;

import javax.swing.*;
import java.awt.*;

/**
 * A custom JButton representing a tile in the 2048 game.
 * Each tile has a specific value and appearance.
 */
public class TileButton extends JButton {
    private static final long serialVersionUID = 1L;
    
    /** The numerical value of the tile. */
    private int value;

    /**
     * Constructs a new TileButton with the specified initial value.
     * 
     * @param value The initial value of the tile.
     */
    public TileButton(int value) {
        this.value = value;
        setPreferredSize(new Dimension(100, 100));
        updateText();
        updateBackgroundColor();
    }

    /**
     * Sets the value of the tile and updates its appearance.
     * 
     * @param value The new value for the tile.
     */
    public void setValue(int value) {
        this.value = value;
        updateText();
        updateBackgroundColor();
        repaint();
    }

    /**
     * Helper method to update the displayed text on the button.
     * If the value is 0, the button appears empty; otherwise, it displays the value.
     */
    private void updateText() {
        String text = (value == 0) ? "" : String.valueOf(value);
        setText(text);

        // Set white text color for value 16 for better readability
        if (value >= 16) {
            setForeground(Color.WHITE);
        } else {
            setForeground(Color.BLACK);
        }
    }

    /**
     * Helper method to update the background color based on the tile value.
     * The color varies depending on the tile's value.
     */
    private void updateBackgroundColor() {
        switch (value) {
            case 0:
                setBackground(new Color(205, 193, 180)); // Empty tile color
                break;
            case 2:
                setBackground(new Color(238, 228, 218)); // Color for tile with value 2
                break;
            case 4:
                setBackground(new Color(237, 224, 200)); // Color for tile with value 4
                break;
            case 8:
                setBackground(new Color(242, 177, 121)); // Color for tile with value 8
                break;
            case 16:
                setBackground(new Color(245, 149, 99)); // Color for tile with value 16
                break;
            // Add more cases for other values as needed
            default:
                setBackground(new Color(255, 0, 0)); // Default color (for unexpected values)
                break;
        }
    }
}
