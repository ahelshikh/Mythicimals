import javax.swing.*;

/**
 * The Tutorial Interface class contains all the design and logic needed to implement the tutorial page requirement for the project.
 * The main purpose of the Tutorial screen is to provide a way for users/players to get help with playing the game or understand the game's mechanics.
 * 
 * @author Liam Elliott
 */
public class TutorialInterface extends JPanel {
    /** Contains the area where all categories of help text will be displayed. */
    JTextArea helpTextArea;
    /** Allows the user/player to choose what category of information they would like help with. */
    JComboBox<String> selectionBox;
    /** Allows the player to return to the main menu. */
    JButton backButton;

    /**
     * The constructor for the Tutorial Interface.
     * @param gamePanel the screen/game mananger being used to organize the menus of the game.
     */
    public TutorialInterface(GamePanel gamePanel) {
        setLayout(null);

        // Initialization of selectionBox and the accompanying instructionLabel
        JLabel instructionLabel = new JLabel("Choose a topic:");
        instructionLabel.setBounds(595, 100, 200, 50);
        add(instructionLabel);

        // Initialization of the options selection box, which the user will use to select a topic they want to know more about.
        String[] options = {"Objectives", "Creating New Games", "Loading Saves", "Pet Care", "Store", "Inventory", "Game Interface"};
        selectionBox = new JComboBox<>(options);
        selectionBox.addActionListener(e -> changeText());
        selectionBox.setBounds(540, 150, 200, 50);
        add(selectionBox);

        // Initialization of helpTextArea
        helpTextArea = new JTextArea("Please choose a topic that you would like help with.");
        helpTextArea.setBounds(50, 250, 1160, 400);
        helpTextArea.setEditable(false);
        add(helpTextArea);

        // Initialization of menuTitle
        JLabel menuTitle = new JLabel("Tutorial / Help");
        menuTitle.setBounds(600, 50, 100, 50);
        add(menuTitle);

        // Initialization of backButton
        backButton = new JButton("Back to Main Menu");
        backButton.setBounds(50, 25, 200, 50);
        backButton.addActionListener(e -> gamePanel.showScreen("Main Menu"));
        add(backButton);
    }

    /**
     * Changes the help text at the bottom of the screen, depending on what category the user has chosen in selectionBox.
     */
    private void changeText() {
        switch (selectionBox.getSelectedIndex()) {
            // Objectives
            case 0:
                helpTextArea.setText("""
                    The objective of the game is to take care of a mythical creature as a pet for as long, and as properly, as possible to gather the most amount of points possible.

                    A wizard has granted you the opportunity to adopt a mythical creature as a pet!
                    You can choose from a Dragon, Griffin or Unicorn, each of which have unique attributes and challenges to caretaking.

                    Players will need to feed, exercise and play with the pet for it to live a happy life.
                    There are a variety of foods and gifts to give your pet to make it well-fed and happy!
                    """);
                break;
            // Creating New Games
            case 1:
                helpTextArea.setText("""
                    CREATING A NEW GAME
                    -------------------
                    The player can choose from three different save files to store their pets in.

                    To select a save file, simply click on the button corresponding with the save file that you would like to use.
                    After an option is selected, then the player will be brought to the Pet Selection menu, to create their ideal mythical companion.

                    NOTE: if there is pre-existing data on the save file already, it will be overwritten!

                    PET SELECTION
                    -------------------
                    Upon choosing the save file for their new game, the player can choose from 3 different mythical creatures to adopt; a Dragon, a Griffin, or a Unicorn.

                    To adopt a pet, you must first enter the name that you would like to give your new friend. Keep in mind that it cannot be changed later, so pick a good one!
                    You cannot leave the name blank!

                    After a name has been selected, you must choose the type of pet you want. Click the button corresponding to the creature type you would like to have.

                    If you are happy with your name and creature choices, click "Confirm" to begin the game.
                    """);
                break;
            // Loading Saves
            case 2:
            helpTextArea.setText("""
                To load an existing save, select "Load Save" on the main menu. Then, select the save file you wish to play.
                
                The game will load up the data and start the game right where the last player saved, returning all pet statistics, player balance, and score back to their previous values.
                 """);
                break;
            // Pet Care
            case 3:
                helpTextArea.setText("""
                Taking care of the pet is a vital aspect of game play. Positive actions, such as playing with or feeding the pet, will earn the player points.

                POSITIVE ACTIONS:
                    - Feeding the pet (3 second cooldown): Requires that the player has at least one food item. Feeding the pet will restore their hunger levels by however many effectiveness points are given to the item.
                    - Playing with the pet: Playing with the pet will slightly restore the pet's happiness levels.
                    - Exercising with the pet: Exercising the pet raises their health, but also lowers their fullness and energy quite a bit.
                    - Giving gifts to the pet: Giving the pet gifts raises their happiness levels by a certain amount, depending on the gift given. Generally, the more expensive, the more effective it is.

                NEGATIVE ACTIONS:
                    - Taking the pet to the Vet: Although positive in the way that the pet will be restored to full health, no pet, real or otherwise, likes going to the vet! The player will lose 500 points, as well as $50, to cover the costs of the visit.
                """);
                break;
            // Store
            case 4:
                helpTextArea.setText("""
                During the game, players can access the Store menu on the left side of the game interface.
                Using the currency they have collected over time, the player can choose to buy a variety of items that either raise the happiness or the hunger statistics of their pet.
                
                In the Store menu, the player can choose items to add to their cart, which stores all the items they wish to buy. The player may also choose to remove items from the cart, should they no longer want to purchase those items.
                
                Pressing the "Clear" button will empty out the player's cart, allowing them to easily remove all items in the cart at once.

                Pressing the "Back to Game" button will return the player back to their pet. If there are any items remaining in the cart when the player leaves the store, the contents of the cart will be cleared.
                """);
                break;
            // Inventory
            case 5:
                helpTextArea.setText("""
                The Inventory menu is where the player can access all of the items they have bought and not used completely.

                The player can view how many items they have of different kinds of items, such as hamburgers or blankets.

                If the player wishes to obtain more items, they should go to the Store to buy more items.

                To return back to your pet, click the "Back to Game" button in the top-left of the menu.
                 """);
                break;
            // Game Interface
            case 6:
                helpTextArea.setText("""
                The "Feed" Button is used to increase your pet's fullness bar, reducing its hunger. Clicking the button will make a small hotbar appear; clicking any of the pictures will use that item on your pet.
                The "Sleep" Button is used to increase your pet's energy bar. While in a sleeping state, the pet cannot perform any actions and will awake when the energy bar has been completed restored.
                The "Play" Button is used to increase your pet's happiness bar.
                The "Give Gift" Button is used to increase your pet's happiness bar quickly. Clicking the button will make a small hotbar appear; clicking any of the pictures will use that item on your pet.

                Keyboard Shortcuts:
                In the gameplay screen, you may press 'I' to go to the inventory, 'S' to go to the store, or 'Q' to go back to the main menu. You may press 'Q' in both the store and the inventory to go back to the gameplay screen.
                """);
                break;
            // This should not occur, as the selectionBox index should always be 0 to 6 (since there are 7 indices).
            default:
                helpTextArea.setText("This should not be visible.");
                break;
        }
    }
}
