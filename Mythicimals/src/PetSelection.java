import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Implements all GUI design and logic for the Pet Selection menu.
 * This menu appears when the user chooses to create a new game, and allows the user to create their pet before the game starts.
 * 
 * @author Liam Elliott
 */
public class PetSelection extends JPanel {
    /** The text box where the user enters the name they would like to give their new pet. */
    private JTextField petNameField;
    /** Represents the choice made by the player on which pet they would like to adopt. */
    private String selectedPetType;

    // Images of the different pet types.

    /**
     * Gives the player a visual of the Dragon, helping them decide which pet they would like to adopt.
     */
    private JLabel dragonSprite;
    /**
     * Gives the player a visual of the Griffin, helping them decide which pet they would like to adopt.
     */
    private JLabel griffinSprite;
    /**
     * Gives the player a visual of the Unicorn, helping them decide which pet they would like to adopt.
     */
    private JLabel unicornSprite;

    /**
     * The constructor for the Pet Selection menu.
     * @param gamePanel the screen/game mananger being used to organize the menus of the game.
     */
    public PetSelection(GamePanel gamePanel) {
        setLayout(null);
        this.setBackground(GamePanel.lightBlue);

        // Initialization of the title label for the pet creation menu.
        JLabel titleLabel = new JLabel("Choose your new Pet!");
        titleLabel.setBounds(580, 20, 300, 50);
        add(titleLabel);

        // Initialzation of the button which the user presses if they want to create a new Griffin pet.
        JButton griffinButton = new JButton("Select Griffin");
        griffinButton.setBounds(165, 550, 170, 50);
        griffinButton.addActionListener(e -> selectedPetType = "Griffin");
        add(griffinButton);

        // Initialzation of the button which the user presses if they want to create a new Dragon pet.
        JButton dragonButton = new JButton("Select Dragon");
        dragonButton.setBounds(555, 550, 170, 50);
        dragonButton.addActionListener(e -> selectedPetType = "Dragon");
        add(dragonButton);

        // Initialzation of the button which the user presses if they want to create a new Unicorn pet.
        JButton unicornButton = new JButton("Select Unicorn");
        unicornButton.setBounds(915, 550, 170, 50);
        unicornButton.addActionListener(e -> selectedPetType = "Unicorn");
        add(unicornButton);

        // Initialzation of the label telling the user what the text field is for.
        JLabel nameLabel = new JLabel("Pet Name:");
        nameLabel.setBounds(450, 70, 150, 40);
        add(nameLabel);

        // Initialization of the text field that will be used to get the name of the newly-created pet.
        petNameField = new JTextField();
        petNameField.setBounds(540, 70, 200, 35);
        add(petNameField);

        // Initialization of the "Back to Save File Select" button.
        JButton backButton = new JButton("Back to Save File Select");
        backButton.setBounds(20, 20, 180, 30);
        backButton.addActionListener(e -> gamePanel.showScreen("New Save"));
        add(backButton);

        // Initialization of the "Confirm" button; causes the pet to be created and the game to begin.
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(565, 620, 150, 40);
        confirmButton.addActionListener(e -> {
            String petName = petNameField.getText().trim();
            if (petName.isEmpty() || selectedPetType == null) {
                JOptionPane.showMessageDialog(this, "Please select a pet type and enter a name.");
                return;
            }

            // Create the new pet.
            PetBuilder builder;
            switch (selectedPetType) {
                case "Griffin":
                    builder = new GriffinBuilder();
                    break;
                case "Dragon":
                    builder = new DragonBuilder();
                    break;
                case "Unicorn":
                    builder = new UnicornBuilder();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown pet type: " + selectedPetType);
            }

            builder.createNewPet(petName);
            builder.buildHealth();
            builder.buildHappiness();
            builder.buildHunger();
            builder.buildTiredness();
            builder.buildBalance(500); // Default starting balance
            builder.buildPetType();

            Pet newPet = builder.getPet();
            gamePanel.setActivePet(newPet);

            // Save the new pet to the selected save file
            gamePanel.saveGame();

            // Transition to the gameplay screen
            if (!gamePanel.gameLoopActive()) {
                gamePanel.startGameLoop();
            }
            gamePanel.showScreen("Gameplay Screen");
        });
        add(confirmButton);

        // Initialization of the pet images.
        try {
            BufferedImage dragonImage = ImageIO.read(new File("Mythicimals\\sprites\\dragon_normal.png"));
            BufferedImage griffinImage = ImageIO.read(new File("Mythicimals\\sprites\\griffin_normal.png"));
            BufferedImage unicornImage = ImageIO.read(new File("Mythicimals\\sprites\\unicorn_normal.png"));

            dragonSprite = new JLabel(new ImageIcon(dragonImage));
            dragonSprite.setBounds(590, 190, 100, 100);
            griffinSprite = new JLabel(new ImageIcon(griffinImage));
            griffinSprite.setBounds(200, 190, 100, 100);
            unicornSprite = new JLabel(new ImageIcon(unicornImage));
            unicornSprite.setBounds(930, 190, 100, 100);

            add(dragonSprite);
            add(griffinSprite);
            add(unicornSprite);
        } catch (IOException e) {
            System.out.println("ERROR [@ PetSelection, constructor]: Could not load one or more sprites.");
        }

        // The descriptions for each of the pets in the game.

        String griffinText = "The Griffin is loyal and easily satisfied.<br>It stays happy for long periods of time.<br>However, it's tendency to fly causes it<br>to become exhausted often.<br><br>TRAITS:<br>+ Health<br>+ Happiness<br>- Hunger<br>- Energy";
        String dragonText = "The Dragon is a sturdy beast.<br>It can go a long time without sleeping or eating.<br>It also has a lot of health.<br>However, the dragon is fickle and greedy.<br>Dragons want to receive gifts very often.<br><br>TRAITS:<br>+ Health<br>+ Energy<br>- Hunger<br>- Happiness";
        String unicornText = "The Unicorn is frail but energetic.<br>It's outgoing personality drain its happiness and energy quickly.<br>However, they are inexpensive to feed and have a long life expectancy.<br><br>TRAITS:<br>+ Hunger<br>+ Happiness<br>- Health<br>- Energy";

        Font petTextFont = new Font("Comic Sans MS", Font.BOLD, 14);

        // Initialization of the griffin's description underneath its sprite.
        JLabel griffinDescription = new JLabel("<html>" + griffinText + "</html>");
        griffinDescription.setFont(petTextFont);
        griffinDescription.setBounds(110, 280, 300, 250);
        add(griffinDescription);

        // Initialization of the dragon's description underneath its sprite.
        JLabel dragonDescription = new JLabel("<html>" + dragonText + "</html>");
        dragonDescription.setFont(petTextFont);
        dragonDescription.setBounds(500, 290, 300, 250);
        add(dragonDescription);

        // Initialization of the unicorn's description underneath its sprite.
        JLabel unicornDescription = new JLabel("<html>" + unicornText + "</html>");
        unicornDescription.setFont(petTextFont);
        unicornDescription.setBounds(870, 280, 300, 250);
        add(unicornDescription);
    }
}
