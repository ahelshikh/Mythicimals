import java.awt.Font;
import java.awt.Image;

import javax.swing.*;

/**
 * Implements the logic and GUI design of the game's main menu.
 * This will be the first screen that the player sees upon opening the game.
 * 
 * @author Liam Elliott
 */
public class MainMenu extends JPanel {
    /** The label that displays the title of the game. */
    JLabel gameTitle;
    /** A label that displays text underneath the game title. A small subtitle adds some flair to the main menu and tells the user what the game is about. */
    JLabel subtitle;
    /** The button that allows the player to create a new game. Upon selection, the player will be taken to the New Game screen. */
    JButton newGameButton;
    /** The button that allows the player to load an old game. Upon selection, the player will be taken to the Load Game screen. */
    JButton loadGameButton;
    /** The button that takes the player to the Parental Controls screen. */
    JButton parentalControlsButton;
    /** The button that ends the game. */
    JButton quitButton;
    /** The button that takes the players to the "Help" screen. Provides many tutorials for new players. */
    JButton tutorialButton;
    /** Lists the credits of the game. */
    JTextArea creditsArea;

    /**
     * The constructor for the Main Menu screen.
     * @param gamePanel the screen/game management JPanel used by the software.
     */
    public MainMenu(GamePanel gamePanel) {
        setLayout(null);

        // Adds a background colour that enhances the visuals of the menu.
        this.setBackground(GamePanel.lightBlue);
        // Standardizes the primary buttons on the screen so they have the same dimensions.
        int buttonWidth = 360;
        int buttonHeight = 50;

        // Initialization of the game's title text.
        Font titleFont = new Font("Comic Sans MS", Font.BOLD, 50);
        gameTitle = new JLabel("Mythicimals");
        gameTitle.setBounds(510, 10, 1000, 100);
        gameTitle.setFont(titleFont);
        add(gameTitle);

        // Initialization of the subtitle text.
        subtitle = new JLabel("A Magical Pet Simulator");
        Font subTitleFont = new Font("Comic Sans MS", Font.BOLD, 20);
        subtitle.setBounds(530, 70, 1000, 100);
        subtitle.setFont(subTitleFont);
        add(subtitle);

        // Initializion of the "New Game" button.
        newGameButton = new JButton("Start New Game");
        newGameButton.setBounds(460, 400, buttonWidth, buttonHeight);
        newGameButton.addActionListener(e -> gamePanel.showScreen("New Save"));
        add(newGameButton);

        // Initializion of the "Load Game" button.
        loadGameButton = new JButton("Load Save File");
        loadGameButton.setBounds(460, 465, buttonWidth, buttonHeight);
        loadGameButton.addActionListener(e -> gamePanel.showScreen("Load Save"));
        add(loadGameButton);

        // Initializion of the "Parental Controls" button.
        parentalControlsButton = new JButton("Parental Controls");
        parentalControlsButton.setBounds(460, 530, buttonWidth, buttonHeight);
        parentalControlsButton.addActionListener(e -> gamePanel.showScreen("Parental Controls"));
        add(parentalControlsButton);

        // Initializion of the "Quit" button.
        quitButton = new JButton("Quit");
        quitButton.setBounds(460, 595, buttonWidth, buttonHeight);
        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton);

        // Initializion of the "Help" button.
        tutorialButton = new JButton("Help");
        tutorialButton.setBounds(1125, 595, 100, buttonHeight);
        tutorialButton.addActionListener(e -> gamePanel.showScreen("Tutorial"));
        add(tutorialButton);

        // Initialization of the credits title.
        Font creditsTitleFont = new Font("Comic Sans MS", Font.BOLD, 16);
        JLabel creditsTitle= new JLabel("CREDITS");
        creditsTitle.setFont(creditsTitleFont);
        creditsTitle.setBounds(30, 340, 300, 250);
        add(creditsTitle);

        // Initialization of the credits text, which displays all team members and the project details.
        String credits = "Mythicimals Team Members:<br>- Ahmed Elshikh<br>- Liam Gerard Elliot<br>- Muhammad Saad Khan<br>- Zhantore Borangali";
        Font creditsFont = new Font("Comic Sans MS", Font.PLAIN, 14);
        JLabel creditsText = new JLabel("<html>" + credits + "</html>");
        creditsText.setFont(creditsFont);
        creditsText.setBounds(30, 430, 300, 250);
        add(creditsText);

        // Initialization of the dragon sprite on the main menu.
        int imageWidth = 200;
        int imageHeight = 200;
        ImageIcon dragonIcon = new ImageIcon("Mythicimals\\sprites\\dragon_normal.png");
        Image dragonImg = dragonIcon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        dragonIcon = new ImageIcon(dragonImg);
        JLabel dragonImage = new JLabel(dragonIcon);
        dragonImage.setBounds(550, 170, imageWidth, imageHeight);
        add(dragonImage);

        // Initialization of the griffin sprite on the main menu.
        ImageIcon griffinIcon = new ImageIcon("Mythicimals\\sprites\\griffin_angry_flipped.png");
        Image img = griffinIcon.getImage().getScaledInstance(imageWidth + 20, imageHeight + 20, Image.SCALE_SMOOTH);
        griffinIcon = new ImageIcon(img);
        JLabel griffinImage = new JLabel(griffinIcon);
        griffinImage.setBounds(780, 120, imageWidth + 20, imageHeight + 20);
        add(griffinImage);

        // Initialization of the unicorn sprite on the main menu.
        ImageIcon unicornIcon = new ImageIcon("Mythicimals\\sprites\\unicorn_angry.png");
        Image unicornImg = unicornIcon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        unicornIcon = new ImageIcon(unicornImg);
        JLabel unicornImage = new JLabel(unicornIcon);
        unicornImage.setBounds(320, 160, imageWidth, imageHeight);
        add(unicornImage);
    }
}