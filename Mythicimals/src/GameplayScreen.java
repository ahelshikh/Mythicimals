import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import item.*;

/**
 * The GameplayScreen class represents the main gameplay interface where the
 * player interacts with the game.
 * It displays the pet, statistics, and gameplay options such as feeding,
 * playing, and taking the pet to the vet.
 * The class also handles keyboard shortcuts for screen navigation and other
 * actions.
 * 
 * @author Ahmed
 */
public class GameplayScreen extends JPanel {
        /** Label to display the pet's sprite. */
        private JLabel petLabel;

        /** Reference to the parent GamePanel. */
        private GamePanel gamePanel;

        /** Icon for the pet's sprite. */
        private ImageIcon petIcon;

        /** Flipped icon for the pet's sprite. */
        private ImageIcon petIconFlipped;

        /** Health bar panel. */
        private JPanel healthBar;

        /** Happiness bar panel. */
        private JPanel happinessBar;

        /** Fullness bar panel. */
        private JPanel fullnessBar;

        /** Energy bar panel. */
        private JPanel energyBar;

        /** Label to display the health value. */
        private JLabel healthNumber;

        /** Label to display the happiness value. */
        private JLabel happinessNumber;

        /** Label to display the fullness value. */
        private JLabel fullnessNumber;

        /** Label to display the energy value. */
        private JLabel energyNumber;

        /** Label to display the score. */
        private JLabel score;

        /** Indicates whether the pet's sprite is flipped horizontally. */
        private boolean spriteFlipped = false;

        /** Width of the stat bars. */
        private int barWidth = 200;

        /** Height of the stat bars. */
        private int barHeight = 60;

        /** Default green color for healthy stats. */
        Color barGreen = new Color(0, 170, 0);

        /**
         * Constructs a new GameplayScreen and initializes all UI components.
         * 
         * @param gamePanel the parent GamePanel that contains this screen.
         */
        public GameplayScreen(GamePanel gamePanel) {
                setLayout(null);
                this.gamePanel = gamePanel;
                this.setBackground(GamePanel.lightGreen);

                petIcon = new ImageIcon(gamePanel.getActivePet().getSprite());
                Image scaledImage = petIcon.getImage().getScaledInstance(petIcon.getIconWidth() * 3,
                                petIcon.getIconHeight() * 3, Image.SCALE_SMOOTH);
                petIcon = new ImageIcon(scaledImage);

                Image flippedImage = flipImageHorizontally(scaledImage);
                petIconFlipped = new ImageIcon(flippedImage);

                petLabel = new JLabel(petIcon);
                int petWidth = petIcon.getIconWidth(), petHeight = petIcon.getIconHeight();
                petLabel.setBounds((GamePanel.screenWidth - petWidth) / 2, (GamePanel.screenHeight - petHeight) / 2,
                                petWidth,
                                petHeight);
                add(petLabel);

                int buttonSize = 90;

                JButton storeButton = new JButton("Store");
                storeButton.setBounds(100, 300, buttonSize, buttonSize);
                add(storeButton);
                storeButton.addActionListener(e -> gamePanel.showScreen("Store"));
                JButton inventoryButton = new JButton("Inventory");
                inventoryButton.setBounds(1075, 300, buttonSize, buttonSize);
                add(inventoryButton);
                inventoryButton.addActionListener(e -> {
                        gamePanel.showScreen("Inventory");
                });

                JButton menuButton = new JButton("Back to Main Menu");
                menuButton.setBounds(470, 545, 160, 20);
                menuButton.addActionListener(e -> {
                        gamePanel.saveGame();
                        gamePanel.showScreen("Main Menu");
                });
                add(menuButton);

                JButton saveButton = new JButton("Save Game");
                saveButton.setBounds(650, 545, 160, 20);
                saveButton.addActionListener(e -> {
                        displayMessage("Game Saved", "Game saved successfully");
                        gamePanel.saveGame();
                });
                add(saveButton);

                int petButtonWidth = 160;
                int petButtonHeight = 65;

                JButton feedButton = new JButton("Feed");
                JButton sleepButton = new JButton("Sleep");
                JButton walkButton = new JButton("Walk");
                JButton playButton = new JButton("Play");
                JButton vetButton = new JButton("Take to Vet");
                JButton giftButton = new JButton("Give Gift");

                feedButton.setBounds(160, 590, petButtonWidth, petButtonHeight);
                sleepButton.setBounds(320, 590, petButtonWidth, petButtonHeight);
                walkButton.setBounds(480, 590, petButtonWidth, petButtonHeight);
                playButton.setBounds(640, 590, petButtonWidth, petButtonHeight);
                vetButton.setBounds(800, 590, petButtonWidth, petButtonHeight);
                giftButton.setBounds(960, 590, petButtonWidth, petButtonHeight);

                add(feedButton);
                add(sleepButton);
                add(walkButton);
                add(playButton);
                add(vetButton);
                add(giftButton);

                feedButton.addActionListener(e -> gamePanel.feedPetButton());
                playButton.addActionListener(e -> gamePanel.playButton());
                walkButton.addActionListener(e -> gamePanel.walkButton());
                sleepButton.addActionListener(e -> gamePanel.sleepButton());
                vetButton.addActionListener(e -> gamePanel.vetButton());
                giftButton.addActionListener(e -> gamePanel.giftButton());

                // Stat Labels
                Font labelFont = new Font("Arial", Font.PLAIN, 18);
                JLabel healthLabel = new JLabel("Health");
                healthLabel.setBounds(225, 35, 200, 30);
                healthLabel.setFont(labelFont);
                add(healthLabel);

                JLabel happinessLabel = new JLabel("Happiness");
                happinessLabel.setBounds(470, 35, 200, 30);
                happinessLabel.setFont(labelFont);
                add(happinessLabel);

                JLabel fullnessLabel = new JLabel("Fullness");
                fullnessLabel.setBounds(740, 35, 200, 30);
                fullnessLabel.setFont(labelFont);
                add(fullnessLabel);

                JLabel energyLabel = new JLabel("Energy");
                energyLabel.setBounds(1000, 35, 200, 30);
                energyLabel.setFont(labelFont);
                add(energyLabel);

                // Stat numbers
                Font numberFont = new Font("Arial", Font.BOLD, 20);

                healthNumber = new JLabel(
                                gamePanel.getActivePet().getHealth() + " / " + gamePanel.getActivePet().getMaxHealth());
                healthNumber.setBounds(210, 85, 200, 30);
                healthNumber.setFont(numberFont);
                add(healthNumber);

                happinessNumber = new JLabel(
                                gamePanel.getActivePet().getHappiness() + " / "
                                                + gamePanel.getActivePet().getMaxHappiness());
                happinessNumber.setBounds(470, 85, 200, 30);
                happinessNumber.setFont(numberFont);
                add(happinessNumber);

                fullnessNumber = new JLabel(
                                gamePanel.getActivePet().getFullness() + " / "
                                                + gamePanel.getActivePet().getMaxFullness());
                fullnessNumber.setBounds(730, 85, 200, 30);
                fullnessNumber.setFont(numberFont);
                add(fullnessNumber);

                energyNumber = new JLabel(
                                gamePanel.getActivePet().getEnergy() + " / " + gamePanel.getActivePet().getMaxEnergy());
                energyNumber.setBounds(990, 85, 200, 30);
                energyNumber.setFont(numberFont);
                add(energyNumber);

                // Stat borders
                JPanel healthBorder = new JPanel(null);
                healthBorder.setBounds(150, 70, barWidth, barHeight);
                healthBorder.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                healthBorder.setOpaque(false);
                add(healthBorder);

                JPanel happinessBorder = new JPanel(null);
                happinessBorder.setBounds(410, 70, barWidth, barHeight);
                happinessBorder.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                happinessBorder.setOpaque(false);
                add(happinessBorder);

                JPanel fullnessBorder = new JPanel(null);
                fullnessBorder.setBounds(670, 70, barWidth, barHeight);
                fullnessBorder.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                fullnessBorder.setOpaque(false);
                add(fullnessBorder);

                JPanel energyBorder = new JPanel(null);
                energyBorder.setBounds(930, 70, barWidth, barHeight);
                energyBorder.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                energyBorder.setOpaque(false);
                add(energyBorder);

                // Stat Bars
                healthBar = new JPanel();
                Color color = (gamePanel.getActivePet().getHealth() > gamePanel.getActivePet().getMaxHealth() / 4)
                                ? barGreen
                                : Color.RED;
                healthBar.setBackground(color);
                healthBar.setBounds(150, 70, barWidth, barHeight);
                healthBar.setSize((int) ((double) gamePanel.getActivePet().getHealth()
                                / gamePanel.getActivePet().getMaxHealth()
                                * barWidth), barHeight);
                add(healthBar);

                happinessBar = new JPanel();
                color = (gamePanel.getActivePet().getHappiness() > gamePanel.getActivePet().getMaxHappiness() / 4)
                                ? barGreen
                                : Color.RED;
                happinessBar.setBackground(color);
                happinessBar.setBounds(410, 70, barWidth, barHeight);
                happinessBar.setSize((int) ((double) gamePanel.getActivePet().getHappiness()
                                / gamePanel.getActivePet().getMaxHappiness() * barWidth), barHeight);
                add(happinessBar);

                fullnessBar = new JPanel();
                color = (gamePanel.getActivePet().getFullness() > gamePanel.getActivePet().getMaxFullness() / 4)
                                ? barGreen
                                : Color.RED;
                fullnessBar.setBackground(color);
                fullnessBar.setBounds(670, 70, barWidth, barHeight);
                fullnessBar.setSize((int) ((double) gamePanel.getActivePet().getFullness()
                                / gamePanel.getActivePet().getMaxFullness() * barWidth), barHeight);
                add(fullnessBar);

                energyBar = new JPanel();
                color = (gamePanel.getActivePet().getEnergy() > gamePanel.getActivePet().getMaxEnergy() / 4) ? barGreen
                                : Color.RED;
                energyBar.setBackground(color);
                energyBar.setBounds(930, 70, barWidth, barHeight);
                energyBar.setSize((int) ((double) gamePanel.getActivePet().getEnergy()
                                / gamePanel.getActivePet().getMaxEnergy()
                                * barWidth), barHeight);
                add(energyBar);

                score = new JLabel("Score: " + gamePanel.getActivePet().getScore());
                score.setBounds(615, 50, 200, 200);
                Font scoreFont = new Font("Comic Sans MS", Font.BOLD, 14);
                score.setFont(scoreFont);
                add(score);

                setupKeyBindings();
        }

        /**
         * Flips the pet's sprite horizontally.
         */
        public void flipSprite() {
                if (spriteFlipped) {
                        petLabel.setIcon(petIcon);
                } else {
                        petLabel.setIcon(petIconFlipped);
                }
                spriteFlipped = !spriteFlipped;
        }

        /**
         * Flips an image horizontally.
         * 
         * @param img the image to flip.
         * @return the flipped image.
         */
        private Image flipImageHorizontally(Image img) {
                BufferedImage bimage = toBufferedImage(img);

                AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
                tx.translate(-bimage.getWidth(), 0);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                bimage = op.filter(bimage, null);

                return bimage;
        }

        /**
         * Converts an Image to a BufferedImage for transformations.
         * 
         * @param img the Image to convert.
         * @return the BufferedImage representation of the image.
         */
        private static BufferedImage toBufferedImage(Image img) {
                if (img instanceof BufferedImage) {
                        return (BufferedImage) img;
                }

                BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null),
                                BufferedImage.TYPE_INT_ARGB);

                Graphics2D bGr = bimage.createGraphics();
                bGr.drawImage(img, 0, 0, null);
                bGr.dispose();

                return bimage;
        }

        /**
         * Updates the pet's sprite based on changes to the active pet.
         */
        public void updatePetSprite() {
                String newSpritePath = gamePanel.getActivePet().getSprite();
                if (!petIcon.toString().equals(newSpritePath)) {

                        ImageIcon newIcon = new ImageIcon(newSpritePath);

                        int newWidth = newIcon.getIconWidth() * 3;
                        int newHeight = newIcon.getIconHeight() * 3;

                        Image scaledImage = newIcon.getImage().getScaledInstance(newWidth, newHeight,
                                        Image.SCALE_SMOOTH);
                        petIcon = new ImageIcon(scaledImage);

                        // Create flipped image
                        Image flippedImage = flipImageHorizontally(scaledImage);
                        petIconFlipped = new ImageIcon(flippedImage);

                        // Update petLabel icon based on flip state
                        if (spriteFlipped) {
                                petLabel.setIcon(petIconFlipped);
                        } else {
                                petLabel.setIcon(petIcon);
                        }

                        petLabel.setBounds(
                                        (GamePanel.screenWidth - newWidth) / 2,
                                        (GamePanel.screenHeight - newHeight) / 2,
                                        newWidth,
                                        newHeight);
                }
        }

        /**
         * Updates the visual representation of the stat bars (health, happiness,
         * fullness, energy).
         */
        public void updateStatBar() {

                if (gamePanel.getActivePet().getHealth() < gamePanel.getActivePet().getMaxHealth() / 4) {
                        healthBar.setBackground(Color.RED);
                } else {
                        healthBar.setBackground(barGreen);
                }

                if (gamePanel.getActivePet().getHappiness() < gamePanel.getActivePet().getMaxHappiness() / 4) {
                        happinessBar.setBackground(Color.RED);
                } else {
                        happinessBar.setBackground(barGreen);
                }

                if (gamePanel.getActivePet().getFullness() < gamePanel.getActivePet().getMaxFullness() / 4) {
                        fullnessBar.setBackground(Color.RED);
                } else {
                        fullnessBar.setBackground(barGreen);
                }

                if (gamePanel.getActivePet().getEnergy() < gamePanel.getActivePet().getMaxEnergy() / 4) {
                        energyBar.setBackground(Color.RED);
                } else {
                        energyBar.setBackground(barGreen);
                }

                healthBar.setSize((int) ((double) gamePanel.getActivePet().getHealth()
                                / gamePanel.getActivePet().getMaxHealth()
                                * barWidth), barHeight);

                happinessBar.setSize((int) ((double) gamePanel.getActivePet().getHappiness()
                                / gamePanel.getActivePet().getMaxHappiness() * barWidth), barHeight);

                fullnessBar.setSize((int) ((double) gamePanel.getActivePet().getFullness()
                                / gamePanel.getActivePet().getMaxFullness() * barWidth), barHeight);

                energyBar.setSize((int) ((double) gamePanel.getActivePet().getEnergy()
                                / gamePanel.getActivePet().getMaxEnergy() * barWidth), barHeight);

                healthNumber.setText(
                                gamePanel.getActivePet().getHealth() + " / " + gamePanel.getActivePet().getMaxHealth());
                happinessNumber.setText(
                                gamePanel.getActivePet().getHappiness() + " / "
                                                + gamePanel.getActivePet().getMaxHappiness());
                fullnessNumber.setText(
                                gamePanel.getActivePet().getFullness() + " / "
                                                + gamePanel.getActivePet().getMaxFullness());
                energyNumber.setText(
                                gamePanel.getActivePet().getEnergy() + " / " + gamePanel.getActivePet().getMaxEnergy());
        }

        /**
         * Displays available food options for feeding the pet.
         */
        public void displayFoodOptions() {
                int foodWidth = 50;
                int foodHeight = 50;
                int itemCount = 0;

                ArrayList<JButton> foodButtons = new ArrayList<>();

                for (Item item : gamePanel.getActivePet().getItems()) {
                        if (item instanceof Food && item.getQuantity() > 0) {
                                ImageIcon icon = new ImageIcon(item.getSprite());
                                Image img = icon.getImage().getScaledInstance(foodWidth, foodHeight,
                                                Image.SCALE_SMOOTH);
                                icon = new ImageIcon(img);

                                JButton imageButton = new JButton(icon);
                                imageButton.setBounds(160 + 50 * itemCount, 530, foodWidth, foodHeight);

                                add(imageButton);
                                foodButtons.add(imageButton);
                                itemCount++;

                                imageButton.addActionListener(e -> {
                                        gamePanel.feedPet((Food) item);

                                        for (JButton button : foodButtons) {
                                                remove(button);
                                        }
                                        revalidate();
                                        repaint();
                                });
                        }

                }
        }

        /**
         * Displays available gift options for gifting the pet.
         */
        public void displayGiftOptions() {
                int giftWidth = 50;
                int giftHeight = 50;
                int itemCount = 0;

                ArrayList<JButton> giftButtons = new ArrayList<>();

                for (Item item : gamePanel.getActivePet().getItems()) {
                        if (item instanceof Gift && item.getQuantity() > 0) {
                                System.out.println(item.getSprite());
                                ImageIcon icon = new ImageIcon(item.getSprite());
                                Image img = icon.getImage().getScaledInstance(giftWidth, giftHeight,
                                                Image.SCALE_SMOOTH);
                                icon = new ImageIcon(img);

                                JButton imageButton = new JButton(icon);
                                imageButton.setBounds(960 + 50 * itemCount, 530, giftWidth, giftHeight);

                                add(imageButton);
                                giftButtons.add(imageButton);
                                itemCount++;

                                imageButton.addActionListener(e -> {
                                        gamePanel.giftPet((Gift) item);

                                        for (JButton button : giftButtons) {
                                                remove(button);
                                        }
                                        revalidate();
                                        repaint();
                                });
                        }

                }
        }

        /**
         * Displays a simple message dialog.
         * 
         * @param title   the title of the dialog.
         * @param message the message content to display.
         */
        private void displayMessage(String title, String message) {
                JOptionPane.showMessageDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
        }

        /**
         * Updates the score label to reflect the current score of the active pet.
         */
        public void updateScoreLabel() {
                score.setText("Score: " + gamePanel.getActivePet().getScore());
        }

        /**
         * Configures key bindings for keyboard shortcuts (e.g., navigating screens and
         * saving the game).
         */
        private void setupKeyBindings() {

                InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
                ActionMap actionMap = this.getActionMap();

                // Main Menu
                inputMap.put(KeyStroke.getKeyStroke('Q'), "Main Menu");
                inputMap.put(KeyStroke.getKeyStroke('q'), "Main Menu");

                actionMap.put("Main Menu", new AbstractAction() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                                gamePanel.saveGame();
                                gamePanel.showScreen("Main Menu");
                        }
                });

                // Store
                inputMap.put(KeyStroke.getKeyStroke('S'), "Store");
                inputMap.put(KeyStroke.getKeyStroke('s'), "Store");

                actionMap.put("Store", new AbstractAction() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                                gamePanel.showScreen("Store");
                        }
                });

                // Inventory
                inputMap.put(KeyStroke.getKeyStroke('I'), "Inventory");
                inputMap.put(KeyStroke.getKeyStroke('i'), "Inventory");

                actionMap.put("Inventory", new AbstractAction() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                                gamePanel.showScreen("Inventory");
                        }
                });

                // Save Game
                inputMap.put(KeyStroke.getKeyStroke('R'), "Save Game");
                inputMap.put(KeyStroke.getKeyStroke('r'), "Save Game");

                actionMap.put("Save Game", new AbstractAction() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                                displayMessage("Game Saved", "Game saved successfully");
                                gamePanel.saveGame();
                        }
                });
        }
}