import javax.swing.*;
import java.awt.*;

import item.*;

/**
 * The GamePanel class organizes the screens and core game functionality for the
 * game.
 * It manages the various game states, screens, and user interactions.
 * 
 * The GamePanel serves as the central hub for handling the game's flow,
 * rendering screens,
 * and managing gameplay features, including the game loop.
 * 
 * @author Ahmed
 */
public class GamePanel extends JPanel {

    /** Background color for light blue-themed screens. */
    public static final Color lightBlue = new Color(235, 250, 255);

    /** Background color for light purple-themed screens. */
    public static final Color lightPurple = new Color(245, 235, 255);

    /** Background color for light green-themed screens. */
    public static final Color lightGreen = new Color(235, 255, 235);

    /** Background color for light red-themed screens. */
    public static final Color lightRed = new Color(255, 235, 235);

    /** The width of the game screen in pixels. */
    public static final int screenWidth = 1280;

    /** The height of the game screen in pixels. */
    public static final int screenHeight = 720;

    /** The currently selected save file. */
    private SaveFile selectedSaveFile;

    /** The name of the currently active screen. */
    private String currentScreen;

    /** Main menu screen of the game. */
    private MainMenu mainMenu;

    /** Gameplay screen of the game. */
    private GameplayScreen gameplayScreen;

    /** Store screen for purchasing items. */
    private Store store;

    /** Inventory screen for managing the player's items. */
    private Inventory inventory;

    /** Pet selection screen. */
    private PetSelection petSelectionMenu;

    /** Parental controls menu. */
    private ParentalControls parentalControlsMenu;

    /** Screen for creating a new save file. */
    private SaveSelectionInterface newSaveMenu;

    /** Screen for loading an existing save file. */
    private SaveSelectionInterface loadSaveMenu;

    /** Tutorial menu screen. */
    private TutorialInterface tutorialMenu;

    /** Indicates whether the game loop is active. */
    private boolean gameLoopActive = false;

    /** Indicated whether the play button is on cooldown */
    private boolean playButtonCooldown = false;

    /** Cooldown duration for the play button in milliseconds. */
    private static final int PLAY_BUTTON_COOLDOWN_DURATION = 3000;

    /** Frames per second for the game loop. */
    private static final int FPS = 60;

    /** Counter for elapsed frames during the game loop. */
    private int framesElapsed = 0;

    /** Timer to handle the game loop updates. */
    private Timer gameTimer;

    /** The active pet being managed by the player. */
    private Pet activePet;

    /** Layout manager for switching between different screens. */
    private CardLayout cardLayout;

    /**
     * Constructs a new GamePanel and initializes the game screens and layout.
     */
    public GamePanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Create screens
        mainMenu = new MainMenu(this);
        petSelectionMenu = new PetSelection(this);
        parentalControlsMenu = new ParentalControls(this);
        newSaveMenu = new SaveSelectionInterface(this, false);
        loadSaveMenu = new SaveSelectionInterface(this, true);
        tutorialMenu = new TutorialInterface(this);

        add(mainMenu, "Main Menu");
        add(petSelectionMenu, "Pet Selection");
        add(parentalControlsMenu, "Parental Controls");
        add(newSaveMenu, "New Save");
        add(loadSaveMenu, "Load Save");
        add(tutorialMenu, "Tutorial");

        cardLayout.show(this, "Gameplay Screen");
        currentScreen = "Gameplay Screen";
    }

    /**
     * Switches to a specified screen by its name.
     * 
     * @param screenName the name of the screen to display.
     */
    public void showScreen(String screenName) {
        if (screenName.equals("Inventory"))
            inventory.displayInventory();
        cardLayout.show(this, screenName);
        currentScreen = screenName;
        System.out.println(currentScreen);
    }

    /**
     * Starts the game loop and initializes gameplay-related screens.
     */
    public void startGameLoop() {
        this.gameplayScreen = new GameplayScreen(this);
        add(gameplayScreen, "Gameplay Screen");
        this.store = new Store(this);
        add(store, "Store");
        store.populateStore();
        this.inventory = new Inventory(this);
        add(inventory, "Inventory");

        gameLoopActive = true;

        gameTimer = new Timer(1000 / FPS, e -> updateGame());
        gameTimer.start();
    }

    /**
     * Updates the game state and active screens during each game loop iteration.
     */
    private void updateGame() {
        if (currentScreen.equals("Gameplay Screen") || currentScreen.equals("Store")
                || currentScreen.equals("Inventory")) {
            framesElapsed++;
            if (framesElapsed >= FPS * 5) {
                activePet.updateStats();
                activePet.addBalance(20);
                framesElapsed = 0;
            }
            if (framesElapsed % (FPS * 3) == 0) {
                gameplayScreen.flipSprite();
            }
        }

        gameplayScreen.updateStatBar();
        gameplayScreen.updatePetSprite();
        gameplayScreen.updateScoreLabel();
        store.updateBalanceLabel();

        if (activePet.getEnergy() == 0 && activePet.getState() != Pet.DEAD) {
            activePet.forceToSleep();
        }
        repaint();
    }

    /**
     * Sets the active pet being managed by the player.
     * 
     * @param newPet the new active pet.
     */
    public void setActivePet(Pet newPet) {
        activePet = newPet;
    }

    /**
     * Sets the current save file to a new save ID.
     * 
     * @param newSaveID the ID of the new save file.
     */
    public void setCurrentSaveFile(String newSaveID) {
        this.selectedSaveFile = new SaveFile(newSaveID);
    }

    /**
     * Gets the current save file.
     * 
     * @return the current save file.
     */
    public SaveFile getCurrentSaveFile() {
        return this.selectedSaveFile;
    }

    /**
     * Gets the active pet being managed by the player.
     * 
     * @return the active pet.
     */
    public Pet getActivePet() {
        return activePet;
    }

    /**
     * Displays an error dialog with a specified title and message.
     * 
     * @param errorTitle   the title of the error dialog.
     * @param errorMessage the message to display in the error dialog.
     */
    private void displayError(String errorTitle, String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, errorTitle, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays a message dialog with a specified title and message.
     * 
     * @param title   the title of the message dialog.
     * @param message the message to display in the dialog.
     */
    private void displayMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Displays an error dialog for invalid pet actions based on the pet's state.
     * 
     * @param action   the action the player attempted to perform.
     * @param petState the current state of the pet.
     */
    private void displayStateError(String action, String petState) {
        String errorMessage = "Can't " + action + ", " + activePet.getName() + " is " + petState.toLowerCase();
        JOptionPane.showMessageDialog(this, errorMessage, "State Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Handles feeding the pet, including checks for the pet's state and inventory.
     */
    public void feedPetButton() {
        int petState = activePet.getState();

        if (petState == Pet.DEAD || petState == Pet.SLEEPING || petState == Pet.ANGRY) {
            displayStateError("feed", activePet.getStateName(petState));
            return;
        }
        if (!activePet.hasFood()) {
            displayError("No Food", "You have no food! You can purchase food from the shop.");
            return;
        }
        if (activePet.getFullness() == activePet.getMaxFullness()) {
            displayMessage("Pet Full", activePet.getName() + " can't eat, he's full!");
        }

        gameplayScreen.displayFoodOptions();
    }

    /**
     * Feeds the pet with the specified food item and updates the score.
     * 
     * @param food the food item to feed the pet.
     */
    public void feedPet(Food food) {
        activePet.feed(food);
        activePet.addScore(250);
        gameplayScreen.updateScoreLabel();
    }

    /**
     * Handles giving a gift to the pet, including checks for the pet's state and
     * inventory.
     */
    public void giftButton() {
        int petState = activePet.getState();
        if (petState == Pet.DEAD || petState == Pet.SLEEPING) {
            displayStateError("Gift gift", activePet.getStateName(petState));
            return;
        }
        if (!activePet.hasGift()) {
            displayError("No Gifts", "You have no Gifts! You can purchase gifts from the shop.");
            return;
        }

        gameplayScreen.displayGiftOptions();
    }

    /**
     * Gives a gift to the pet and updates the score.
     * 
     * @param gift the gift item to give to the pet.
     */
    public void giftPet(Gift gift) {
        activePet.giveGift(gift);
        activePet.addScore(250);
        gameplayScreen.updateScoreLabel();
    }

    /**
     * Handles taking the pet to the vet, including cost and health checks.
     */
    public void vetButton() {
        int petState = activePet.getState();
        if (petState == Pet.DEAD || petState == Pet.SLEEPING || petState == Pet.ANGRY) {
            displayStateError("take " + activePet.getName() + " to the vet", activePet.getStateName(petState));
            return;
        }
        if (activePet.getHealth() == activePet.getMaxHealth()) {
            displayMessage("Completely Healthy", activePet.getName() + " is already completely healthy!");
            return;
        }
        if (activePet.getBalance() < activePet.getVetCost()) {
            displayError("Not enough money", "can't take " + activePet.getName() + " to the vet. you only have "
                    + activePet.getBalance() + " gold. The vet costs " + activePet.getVetCost() + " gold.");
            return;
        }
        activePet.takePetToVet();
        displayMessage("Vet Visit Completed", activePet.getName() + " is now fully healthy, your new balance is "
                + activePet.getBalance() + " gold.");

        activePet.addScore(-1000);
    }

    /**
     * Handles purchasing an item for the pet, including inventory and cost checks.
     * 
     * @param item the item to purchase.
     */
    public void purchaseItem(Item item) {
        if (item.getQuantity() >= 20) {
            displayMessage("Maximum " + item.getName() + "s",
                    "You already have the maximum amount of " + item.getName() + "s.");
            return;
        }
        if (item.getPrice() > activePet.getBalance()) {
            displayMessage("Not Enough Gold", "You need " + (item.getPrice() - activePet.getBalance())
                    + " more gold to purchase this " + item.getName() + ".");
            return;
        }
        activePet.purchaseItem(item);
        System.out.println("Purchased " + item.getName() + ". " + activePet.getBalance() + " gold remaining.");
    }

    /**
     * Handles the action for playing with the pet. Playing with the pet applies a 3 second cooldown to the play button. 
     */
    public void playButton() {
        if (playButtonCooldown) {
            displayMessage("Can't Play Yet", activePet.getName() + " cannot play again yet.");
            return;
        }

        int petState = activePet.getState();

        if (petState == Pet.DEAD || petState == Pet.SLEEPING) {
            displayStateError("play", activePet.getStateName(petState));
            return;
        }

        activePet.play();

        activePet.addScore(50);
        gameplayScreen.updateScoreLabel();

        playButtonCooldown = true;
        Timer cooldownTimer = new Timer(PLAY_BUTTON_COOLDOWN_DURATION, e -> playButtonCooldown = false);
        cooldownTimer.setRepeats(false);
        cooldownTimer.start();
    }

    /**
     * Handles the action for taking the pet on a walk.
     */
    public void walkButton() {
        int petState = activePet.getState();

        if (petState == Pet.DEAD || petState == Pet.SLEEPING || petState == Pet.ANGRY || petState == Pet.HUNGRY) {
            displayStateError("take " + activePet.getName() + " on a walk", activePet.getStateName(petState));
            return;
        }
        if (activePet.getEnergy() < (activePet.getMaxEnergy() / 4)) {
            displayMessage("Too Tired", activePet.getName() + " is too tired to go on a walk.");
            return;
        }
        if (activePet.getFullness() < (activePet.getMaxFullness() / 4)) {
            displayMessage("Too Tired", activePet.getName() + " is too hungry to go on a walk.");
            return;
        }

        activePet.exercise();

        activePet.addScore(100);
        gameplayScreen.updateScoreLabel();
    }

    /**
     * Handles the action for putting the pet to sleep.
     */
    public void sleepButton() {
        int petState = activePet.getState();
        if (petState == Pet.DEAD || petState == Pet.ANGRY) {
            displayStateError("Put " + activePet.getName() + " to bed", activePet.getStateName(petState));
            return;
        }
        if ((activePet.getEnergy() > (activePet.getMaxEnergy() / 2)) && petState != Pet.SLEEPING) {
            displayMessage("Energy is Full", activePet.getName() + " does not need to sleep.");
            return;
        }
        if (petState == Pet.SLEEPING) {
            if (activePet.collapsed()) {
                displayMessage("Can't Wake Up",
                        activePet.getName() + " collapsed from exhaustion. Wait for energy to be full");
                return;
            } else {
                activePet.wakeUp();
                return;
            }
        }

        activePet.takeToBed();

        activePet.addScore(25);
        gameplayScreen.updateScoreLabel();
    }

    /**
     * Checks whether the game loop is active.
     * 
     * @return true if the game loop is active, false otherwise.
     */
    public boolean gameLoopActive() {
        return gameLoopActive;
    }

    /**
     * Saves the current game state to the selected save file.
     */
    public void saveGame() {
        this.selectedSaveFile.alignData(this.activePet);
        this.selectedSaveFile.writeData();
    }

    /**
     * Loads the game state from a save file.
     * 
     * @param saveID the ID of the save file to load.
     */
    public void loadGame(String saveID) {
        try {
            this.setCurrentSaveFile(saveID);
            this.selectedSaveFile.readData();

            // Create the active Pet and assign all loaded data to it.
            PetBuilder pb = null;
            switch (this.selectedSaveFile.petSpecies) {
                case "Dragon":
                    pb = new DragonBuilder();
                    break;
                case "Griffin":
                    pb = new GriffinBuilder();
                    break;
                case "Unicorn":
                    pb = new UnicornBuilder();
                    break;
                default:
                    System.out.println(
                            "ERROR [@ GamePanel, loadGame()]: could not load save information into activePet.");
                    break;
            }

            pb.createNewPet(this.selectedSaveFile.petName);
            pb.buildBalance(this.selectedSaveFile.playerBalance);
            pb.buildPetType();
            pb.buildHealth();
            pb.buildHunger();
            pb.buildTiredness();
            pb.buildHappiness();

            this.activePet = pb.getPet();

            // Give all of the saved statistics of the save file to this new pet object so
            // it can take on the role of the old pet.
            this.activePet.setBalance(this.selectedSaveFile.playerBalance);
            this.activePet.setItems(this.selectedSaveFile.playerInventory);
            this.activePet.setHealth(this.selectedSaveFile.petHealth);
            this.activePet.setHappiness(this.selectedSaveFile.petHappiness);
            this.activePet.setEnergy(this.selectedSaveFile.petEnergy);
            this.activePet.setFullness(this.selectedSaveFile.petHunger);
            this.activePet.setPetType(this.selectedSaveFile.petSpecies);
            this.activePet.setScore(this.selectedSaveFile.score);

            // Once everything regarding the pet has been successfully loaded, start the
            // game.
            if (!gameLoopActive) {
                this.startGameLoop();
            }
        } catch (IllegalStateException e) {
            displayError("Load Error", "The selected save file is empty and cannot be loaded.");
        }
    }
}