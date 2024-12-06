import java.awt.Font;

import javax.swing.*;

/**
 * The Save Selection interface provides a means for the user to choose a save file through the use of a GUI.
 * 
 * This screen will appear immediately after the user selects "New Game" or "Load Game" at the main menu.
 * However, selecting a save file under the "New Game" context will go to the Pet Selection Interface, whereas the player will be loaded directly into the game when in the context of "Load Game."
 * 
 * @author Liam Elliott
 */
public class SaveSelectionInterface extends JPanel {
    /** Used to determine if the menu is in the context of creating a new game (false) or loading an old one (true). */
    private boolean isLoadContext;

    /** Tells the user what context the menu is in, either creating or loading a save. */
    private JLabel menuLabel;
    /** Instructs the user what to do, and provides a warning, if in new save mode. */
    private JLabel descriptionLabel;

    /** Allows the user to return to the main menu, should they wish to not proceed with creating or loading a game. */
    private JButton backButton;
    /** */
    private JButton saveButton1;
    /** */
    private JButton saveButton2;
    /** */
    private JButton saveButton3;

    /**
     * The constructor for the Save Selection screen.
     * @param gamePanel
     * @param isLoadGameSelected determines whether the menu is meant for loading an old save or creating a new one.
     */
    public SaveSelectionInterface(GamePanel gamePanel, boolean isLoadGameSelected) {
        setLayout(null);

        int saveWidth = 200;
        int saveHeight = 80;
        this.isLoadContext = isLoadGameSelected;
        this.setBackground(GamePanel.lightBlue);

        // Initialization of menuLabel and an accompanying descriptionLabel
        menuLabel = new JLabel();
        descriptionLabel = new JLabel();

        // Initialization of backButton
        backButton = new JButton("Back to Main Menu");
        backButton.setBounds(20, 20, 180, 30);
        backButton.addActionListener(e -> gamePanel.showScreen("Main Menu"));
        add(backButton);

        // Initialization of saveButton1
        saveButton1 = new JButton("Save 1");
        saveButton1.setBounds(540, 250, saveWidth, saveHeight);

        // Initialization of saveButton2
        saveButton2 = new JButton("Save 2");
        saveButton2.setBounds(540, 350, saveWidth, saveHeight);

        // Initialization of saveButton3
        saveButton3 = new JButton("Save 3");
        saveButton3.setBounds(540, 450, saveWidth, saveHeight);

        if (this.isLoadContext) {
            saveButton1.addActionListener(e -> {
                gamePanel.loadGame("1");
                gamePanel.showScreen("Gameplay Screen");
            });
            saveButton2.addActionListener(e -> {
                gamePanel.loadGame("2");
                gamePanel.showScreen("Gameplay Screen");
            });
            saveButton3.addActionListener(e -> {
                gamePanel.loadGame("3");
                gamePanel.showScreen("Gameplay Screen");
            });

            menuLabel.setText("Load Save");
            menuLabel.setBounds(610, 50, 200, 100);
            descriptionLabel.setText("Choose a save file to load.");
            descriptionLabel.setBounds(560, 100, 200, 100);
        } else {
            saveButton1.addActionListener(e -> {
                gamePanel.setCurrentSaveFile("1");
                gamePanel.showScreen("Pet Selection");
            });

            saveButton2.addActionListener(e -> {
                gamePanel.setCurrentSaveFile("2");
                gamePanel.showScreen("Pet Selection");
            });

            saveButton3.addActionListener(e -> {
                gamePanel.setCurrentSaveFile("3");
                gamePanel.showScreen("Pet Selection");
            });

            Font textFont = new Font("Comic Sans MS", Font.PLAIN, 16);
            menuLabel.setText("Create New Game");
            menuLabel.setBounds(585, 50, 200, 100);
            menuLabel.setFont(textFont);
            descriptionLabel.setText("Choose a save file to create your new game. NOTE: pre-existing data in the chosen save file will be overwritten!");
            descriptionLabel.setBounds(220, 100, 2000, 100);
            descriptionLabel.setFont(textFont);
        }

        add(saveButton1);
        add(saveButton2);
        add(saveButton3);

        add(menuLabel);
        add(descriptionLabel);
    }
}
