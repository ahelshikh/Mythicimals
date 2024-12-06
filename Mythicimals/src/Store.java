import javax.swing.*;

import item.Food;
import item.Item;

import java.awt.*;
import java.util.ArrayList;

/**
 * The Store class represents a store where players can purchase items.
 * It extends JPanel to provide a graphical representation of the store.
 */
public class Store extends JPanel {
    private GamePanel gamePanel;
    private JLabel balanceLabel;

    /**
     * Constructs a Store object.
     *
     * @param gamePanel the game panel associated with this store
     * @param inventory the inventory to which purchased items will be added
     */
    public Store(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
        setLayout(null);
        this.setBackground(GamePanel.lightPurple);

        Font titleFont = new Font("Comic Sans MS", Font.BOLD, 30);
        JLabel title = new JLabel("WIZARD'S GENERAL STORE");
        title.setBounds(395, -10, 500, 100);
        title.setFont(titleFont);
        add(title);

        JButton gameInterfaceButton = new JButton("Back to Game");
        gameInterfaceButton.setBounds(15, 15, 160, 30);

        gameInterfaceButton.addActionListener(e -> gamePanel.showScreen("Gameplay Screen"));
        add(gameInterfaceButton);

        setupKeyBindings();
    }

    /**
     * Populates the store with items available for purchase, including their prices,
     * descriptions, and images.
     */
    public void populateStore() {

        Font balanceFont = new Font("Comic Sans MS", Font.BOLD, 20);
        balanceLabel = new JLabel("Balance: " + gamePanel.getActivePet().getBalance());
        balanceLabel.setBounds(975, -5, 1000, 100);
        balanceLabel.setFont(balanceFont);
        add(balanceLabel);

        int foodY = 90;
        int giftY = 390;
        int itemWidth = 150;
        int itemHeight = 150;
        int foodCount = 0;
        int giftCount = 0;

        for (Item item : gamePanel.getActivePet().getItems()) {
            int level = (item instanceof Food) ? foodY : giftY;
            ImageIcon icon = new ImageIcon(item.getSprite());
            Image img = icon.getImage().getScaledInstance(itemWidth, itemHeight, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);

            Font priceFont = new Font("Comic Sans MS", Font.BOLD, 16);
            Font itemFont = new Font("Comic Sans MS", Font.PLAIN, 16);
            JLabel price = new JLabel("Cost: " + item.getPrice() + " gold");
            JLabel itemDescription = new JLabel("<html>" + item.getDescription() + "</html>");

            price.setFont(priceFont);
            itemDescription.setFont(itemFont);

            JButton imageButton = new JButton(icon);
            if (item instanceof Food) {
                imageButton.setBounds(165 + 400 * foodCount, level, itemWidth, itemHeight);
                price.setBounds(185 + 400 * foodCount, level + itemHeight - 30, 250, 100);
                itemDescription.setBounds(155 + 400 * foodCount, level + itemHeight + 20, 200, 100);

                foodCount++;
            } else {
                price.setBounds(185 + 400 * giftCount, level + itemHeight - 30, 250, 100);
                itemDescription.setBounds(155 + 400 * giftCount, level + itemHeight + 20, 200, 100);
                imageButton.setBounds(165 + 400 * giftCount, level, itemWidth, itemHeight);

                giftCount++;
            }

            add(itemDescription);
            add(price);
            add(imageButton);

            imageButton.addActionListener(e -> {
                int response = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to purchase this " + item.getName() + " for " + item.getPrice()
                                + " gold? Your remaining balance will be "
                                + (gamePanel.getActivePet().getBalance() - item.getPrice()) + ".",
                        "Confirm Purchase",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    gamePanel.purchaseItem(item);
                }
            });

        }
    }

    /**
     * Updates the balance label to reflect the player's current balance.
     */
    public void updateBalanceLabel() {
        balanceLabel.setText("Balance: " + gamePanel.getActivePet().getBalance());
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