import javax.swing.*;
import java.awt.event.*;

/**
 * The Parental Controls class implements the GUI menu and all logic required to
 * perform administrator functions.
 * 
 * @author Liam Elliott
 */
public class ParentalControls extends JPanel {
    /**
     * Determines if the user of the software can make administrator-level changes.
     * Initially false, but true if the inputted password was correct.
     */
    private boolean isAuthenticated = false;
    /** The administrator password for the program. */
    private String password = "BLDdrW7+ygw#cXFzbV";
    /** The save file that the adminstrator will make changes to. */
    private SaveFile selectedSave;
    /**
     * Determines if the current save file selected by the administrator has time
     * restrictions.
     * 
     * @see selectedSave
     */
    private boolean isTimeRestrictionsEnabled;
    /** Stores the user's password guess. */
    public String guess;
    // private _ upperTimeBound;
    // private _ lowerTimeBound;

    JTextField inputField;
    JButton authenticateButton, backButton, reviveButton, playtimeButton, avgPlaytimeButton, saveButton;
    JComboBox<String> saveSelectionBox;
    JCheckBox timeRestrictionCheckBox;
    // Used to reduce code duplication when hiding/unhiding the administrator-only
    // elements. Will store all admin-exclusive UI elements.
    JComponent[] adminElements;

    public ParentalControls(GamePanel gamePanel) {
        setLayout(null);

        // This label lets the user know that they have entered the Parental Controls
        // menu.
        JLabel menuLabel = new JLabel("Parental Controls");
        menuLabel.setBounds(580, 100, 100, 50);
        add(menuLabel);

        // The user will enter in the administrator password in the text field in order
        // to authenticate.
        inputField = new JTextField("Enter the administrator password to be able to make changes.");
        inputField.setBounds(325, 200, 500, 50);
        add(inputField);

        // The "Authenticate" button checks if the password contained inside of the
        // inputField is the correct password.
        authenticateButton = new JButton("Check");
        authenticateButton.setBounds(835, 200, 100, 50);
        authenticateButton.addActionListener(e -> authenticate());
        add(authenticateButton);

        // This "Back" button provides the user with the means to return to the main
        // menu. If the player was authenticated, they will need to re-authenticate the
        // next time they visit this menu.
        backButton = new JButton("Back to Main Menu");
        backButton.setBounds(20, 20, 180, 30);
        backButton.addActionListener(e -> {
            this.isAuthenticated = false;
            inputField.setEditable(true);
            inputField.setText("Enter the administrator password to be able to make changes.");
            this.hideAdminOptions();
            gamePanel.showScreen("Main Menu");
        });
        add(backButton);

        // This combo box is used by an authenticated player is select the save file
        // which they want to alter.
        String saves[] = { "Save 1", "Save 2", "Save 3" };
        saveSelectionBox = new JComboBox<>(saves);
        saveSelectionBox.setBounds(200, 400, 100, 100);
        saveSelectionBox.addActionListener(e -> {
            String selectedSaveID = String.valueOf(saveSelectionBox.getSelectedIndex() + 1);
            this.selectedSave = new SaveFile(selectedSaveID);
            this.selectedSave.readData();
        });
        add(saveSelectionBox);

        // The Revive button can be used by authenticated players to revive the pet
        // stored in the selected save.
        reviveButton = new JButton("Revive Pet");
        reviveButton.setBounds(850, 450, 200, 50);
        this.selectedSave = new SaveFile(String.valueOf(saveSelectionBox.getSelectedIndex() + 1));
        this.selectedSave.readData();
        reviveButton.addActionListener(e -> revivePet());
        add(reviveButton);

        // The "Reset Playtime" button resets the selected save file's playtime
        // statistic back to 0.
        playtimeButton = new JButton("Reset Total Playtime");
        playtimeButton.setBounds(850, 300, 200, 50);
        playtimeButton.addActionListener(e -> resetTotalPlaytime());
        add(playtimeButton);

        // Similar to "Reset Playtime," the "Reset Avg. Playtime" button resets the
        // selected save file's average playtime statisitc back to 0.
        avgPlaytimeButton = new JButton("Reset Average Playtime");
        avgPlaytimeButton.setBounds(850, 375, 200, 50);
        avgPlaytimeButton.addActionListener(e -> resetAveragePlaytime());
        add(avgPlaytimeButton);

        // The "Restrict Playtime" checkbox enables or disables the time limit
        // functionality of the game. If enabled, the selected save file will only be
        // able to play within certain times, as dictated by the administrator.
        timeRestrictionCheckBox = new JCheckBox("Enable time restrictions on the selected save file?", false);
        timeRestrictionCheckBox.setBounds(850, 525, 400, 50);
        timeRestrictionCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent toggle) {
                // Toggle on
                if (toggle.getStateChange() == 1) {
                    isTimeRestrictionsEnabled = true;
                }
                // Toggle off
                else {
                    isTimeRestrictionsEnabled = false;
                }

                // DEBUG: an output message to the terminal allows us to know if time
                // restrictions have been enabled or disabled.
                System.out.println("Time restrictions for save file " + selectedSave.getSaveID() + " set to "
                        + isTimeRestrictionsEnabled);
            }
        });
        add(timeRestrictionCheckBox);

        // The "Save" button confirms an authenticated user's settings and saves those
        // settings to file.
        saveButton = new JButton("Save Settings");
        saveButton.setBounds(500, 550, 200, 50);
        saveButton.addActionListener(e -> saveSettingsToFile());
        add(saveButton);

        // Initially hides all administrator options, until the correct password is
        // received.
        adminElements = new JComponent[] { this.reviveButton, this.playtimeButton, this.avgPlaytimeButton,
                this.saveButton, this.timeRestrictionCheckBox, this.saveSelectionBox };
        this.hideAdminOptions();
    }

    private boolean revivePet() {
        if (!isAuthenticated) {
            displayMessage("Not Authenticated", "You must be authenticated to revive the pet.");
            return false;
        }
    
        this.selectedSave.readData();
    
        if (this.selectedSave.petHealth != 0) {
            displayMessage("Pet Not Dead", "Pet must be dead to revive.");
            return false;
        } else {
            switch (this.selectedSave.petSpecies) {
                case "Dragon":
                    this.selectedSave.petHealth = 200;
                    this.selectedSave.petHappiness = 100;
                    this.selectedSave.petHunger = 150;
                    this.selectedSave.petEnergy = 200;
                    break;
                case "Griffin":
                    this.selectedSave.petHealth = 150;
                    this.selectedSave.petHappiness = 150;
                    this.selectedSave.petHunger = 175;
                    this.selectedSave.petEnergy = 100;
                    break;
                case "Unicorn":
                    this.selectedSave.petHealth = 100;
                    this.selectedSave.petHappiness = 200;
                    this.selectedSave.petHunger = 125;
                    this.selectedSave.petEnergy = 150;
                    break;
            }

            this.selectedSave.writeData();
            System.out.println("Pet was revived with new stats.");
            displayMessage("Pet Revived", "Pet revived successfully with full stats.");
            return true;
        }
    }
    

    /**
     * Authenticates the user as an adminstrator/parent and allows them to make
     * changes to the game's settings.
     */
    private void authenticate() {
        String userGuess = inputField.getText();

        if (userGuess.equals(this.password)) {
            this.isAuthenticated = true;
            inputField.setText("Successfully authenticated");
            inputField.setEditable(false);
            this.unhideAdminOptions();
        } else {
            inputField.setText("Incorrect Password");
        }
    }

    /**
     * Resets the total playtime
     */
    private void resetTotalPlaytime() {
        this.selectedSave.resetTotalPlaytime();
    }

    /**
     * Resets the average playtime
     */
    private void resetAveragePlaytime() {
        this.selectedSave.resetAveragePlaytime();
    }

    /**
     * Saves the modified settings to the selected file
     */
    private void saveSettingsToFile() {

    }

    /**
     * Used to unhide the administrator options, once the player has used the
     * correct password.
     */
    private void unhideAdminOptions() {
        for (int element = 0; element < adminElements.length; element++) {
            adminElements[element].setEnabled(true);
            adminElements[element].setVisible(true);
        }
    }

    /**
     * Used to re-hide the administrator options, only after the player leaves the
     * menu after authenticating with the administrator password.
     * If the player did not authenticate before leaving the Parental Controls menu,
     * then this function will not be used.
     */
    private void hideAdminOptions() {
        for (int element = 0; element < adminElements.length; element++) {
            adminElements[element].setEnabled(false);
            adminElements[element].setVisible(false);
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
}
