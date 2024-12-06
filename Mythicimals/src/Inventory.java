import javax.swing.*;

import item.Food;
import item.Item;

import java.awt.*;

/**
 * The Inventory class represents a player's inventory in the game.
 * It extends JPanel to provide a graphical representation of the inventory.
 */
public class Inventory extends JPanel {
    /** The screen manager used by the program. */
    private GamePanel gamePanel;

    /**
     * Constructs the Inventory menu.
     * @param gamePanel the screen manager used by the game.
     */
    public Inventory(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.setBackground(GamePanel.lightRed);

        setLayout(null);
        displayInventory();

        setupKeyBindings();
    }

    /**
     * Visually displays the player's inventory, which allows the player to easily identify the quantities they have of each item in the game.
     */
    public void displayInventory() {
        this.removeAll();

        // Initialization of the menu title.
        Font titleFont = new Font("Comic Sans MS", Font.BOLD, 30);
        JLabel title = new JLabel("INVENTORY");
        title.setBounds(550, -10, 500, 100);
        title.setFont(titleFont);
        add(title);

        // Initialization of the "Back to Game" button.
        JButton gameInterfaceButton = new JButton("Back to Game");
        gameInterfaceButton.setBounds(15, 15, 160, 30);
        gameInterfaceButton.addActionListener(e -> gamePanel.showScreen("Gameplay Screen"));
        add(gameInterfaceButton);

        // Variables that help keep visual elements consistent.

        int foodY = 90;
        int giftY = 390;
        int itemWidth = 200;
        int itemHeight = 200;
        int foodCount = 0;
        int giftCount = 0;

        // Drawing the sprites of the items, and their counters, to the screen.
        for (Item item : gamePanel.getActivePet().getItems()) {
            int level = (item instanceof Food) ? foodY : giftY;

            ImageIcon icon = new ImageIcon(item.getSprite());
            Image img = icon.getImage().getScaledInstance(itemWidth, itemHeight, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);

            JLabel itemIcon = new JLabel(icon);

            JPanel itemBorder = new JPanel(null);
            itemBorder.setBorder(BorderFactory.createLineBorder(Color.black, 3));
            itemBorder.setOpaque(false);

            Font quantityFont = new Font("Comic Sans MS", Font.BOLD, 20);
            JLabel quantity = new JLabel(item.getQuantity() + " " + item.getName() + "s");
            quantity.setFont(quantityFont);

            if (item instanceof Food) {
                itemBorder.setBounds(140 + 400 * foodCount, level, itemWidth, itemHeight);
                quantity.setBounds(160 + 400 * foodCount, level + itemHeight - 75, itemWidth, itemHeight);
                itemIcon.setBounds(140 + 400 * foodCount, level, itemWidth, itemHeight);
                foodCount++;
            } else {
                itemBorder.setBounds(140 + 400 * giftCount, level, itemWidth, itemHeight);
                quantity.setBounds(160 + 400 * giftCount, level + itemHeight - 75, itemWidth, itemHeight);
                itemIcon.setBounds(140 + 400 * giftCount, level, itemWidth, itemHeight);
                giftCount++;
            }

            this.add(quantity);
            this.add(itemIcon);
            this.add(itemBorder);
        }
    }

    /**
     * Configures key bindings for keyboard shortcuts
     */
    private void setupKeyBindings() {

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        // Gameplay Screen
        inputMap.put(KeyStroke.getKeyStroke('Q'), "Gameplay Screen");
        inputMap.put(KeyStroke.getKeyStroke('q'), "Gameplay Screen");

        actionMap.put("Gameplay Screen", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gamePanel.showScreen("Gameplay Screen");
            }
        });
    }
}