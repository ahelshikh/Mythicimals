import java.util.Scanner;

import item.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

/**
 * The Save File class handles everything regarding saving and loading data from
 * CSV files.
 * 
 * Every save file contains the statistics of the player and the pet as instance
 * variables.
 * 
 * @author Liam Elliott
 */
public class SaveFile {
    /** Used by the Save File class to read data from a CSV file. */
    private Scanner scanner;
    /** The number associated with the save file. Either 1, 2 or 3. */
    private String id;
    /** Represents the file being read. */
    private File saveFilePath;

    /** The total playtime for a given save file. */
    private long totalPlaytime;
    /**
     * The average playtime for a given save file among the amount of sessions that
     * have been played
     */
    private long averagePlaytimePerSession;
    /**
     * The number of times this save file has been loaded; used to calculate average
     * play time per gaming session.
     */
    private int timesLoaded = 1;
    /** The saved balance of the player. */
    public int playerBalance;
    /** The saved inventory of the player. */
    public ArrayList<Item> playerInventory = new ArrayList<Item>();
    /** The saved score of the player. */
    public int score;
    /** The saved name of the pet belonging to the player. */
    public String petName;
    /** The saved type of pet belonging to the player. */
    public String petSpecies;
    /** The saved health stat of the pet belonging to the player. */
    public int petHealth;
    /** The saved hunger stat of the pet belonging to the player. */
    public int petHunger;
    /** The saved energy stat of the pet belonging to the player. */
    public int petEnergy;
    /** The saved happiness stat of the pet belonging to the player. */
    public int petHappiness;

    /**
     * The constructor for the Save File class.
     * 
     * @param saveId the number identifying the save file.
     */
    public SaveFile(String saveId) {
        try {
            this.id = saveId;
            this.saveFilePath = new File("Mythicimals\\saves\\save" + this.id + ".csv");
            if (saveFilePath.createNewFile()) {
                System.out.println("Created save file " + this.id);
            } else {
                System.out.println("Save file " + this.id + " found. Loading...");
            }
            this.scanner = new Scanner(this.saveFilePath);
        } catch (IOException e) {
            System.out.println("ERROR: could not open save file.");
        }
    }

    /**
     * Saving functionality; when the Player calls upon this, all statistics
     * concerning the player and their pet are saved to file.
     */
    public void writeData() {
        // this.averagePlaytimePerSession = this.totalPlaytime / this.timesLoaded;

        try {
            // Needed to write data to the file.
            FileWriter fw = new FileWriter(this.saveFilePath);

            // Used to insert newlines after each data element is written to file.
            String lineSeparator = System.lineSeparator();

            fw.write("timesLoaded," + timesLoaded + lineSeparator);
            fw.write("playerBalance," + playerBalance + lineSeparator);

            for (int itemNum = 0; itemNum < this.playerInventory.size(); itemNum++) {
                Item itemToWrite = playerInventory.get(itemNum);
                fw.write("playerInventory," + itemToWrite.getClass().getSimpleName() + "," + itemToWrite.getName() + ","
                        + itemToWrite.getQuantity()
                        + "," + itemToWrite.getEffectiveness() + "," + itemToWrite.getPrice() + lineSeparator);
            }

            fw.write("score," + score + lineSeparator);
            fw.write("petName," + petName + lineSeparator);
            fw.write("petSpecies," + petSpecies + lineSeparator);
            fw.write("petHealth," + petHealth + lineSeparator);
            fw.write("petHunger," + petHunger + lineSeparator);
            fw.write("petEnergy," + petEnergy + lineSeparator);
            fw.write("petHappiness," + petHappiness + lineSeparator);

            // When the file is done being updated, close the FileWriter as it is no longer
            // needed.
            fw.close();
        } catch (IOException e) {
            System.out.println("ERROR: could not write to save file " + this.id);
        }
    }

    /**
     * Loading functionality; when the Player calls upon this, the data from the
     * file is saved into the class's instance variables, which are then set as the
     * instance variables of the player and pet.
     * 
     * * @throws IllegalStateException if the save file is empty.
     */
    public void readData() {
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("The save file is empty. Cannot load data.");
        }
        // Reading through each line in the file to assign data to each of the instance
        // variables of the Save File class.
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] data = line.split(",");

                switch (data[0]) {
                    case "timesLoaded":
                        this.timesLoaded = Integer.parseInt(data[1]);
                        break;
                    case "playerBalance":
                        this.playerBalance = Integer.parseInt(data[1]);
                        break;
                    case "playerInventory":
                        switch (data[1]) {
                            case "Food":
                                this.playerInventory.add(new Food(data[2], Integer.parseInt(data[3])));
                                break;
                            case "Gift":
                                this.playerInventory.add(new Gift(data[2], Integer.parseInt(data[3])));
                                break;
                            case "Blanket":
                                this.playerInventory.add(new Blanket(Integer.parseInt(data[3])));
                                break;
                            case "Burger":
                                this.playerInventory.add(new Burger(Integer.parseInt(data[3])));
                                break;
                            case "Carrot":
                                this.playerInventory.add(new Carrot(Integer.parseInt(data[3])));
                                break;
                            case "GoldenApple":
                                this.playerInventory.add(new GoldenApple(Integer.parseInt(data[3])));
                                break;
                            case "MagicFlower":
                                this.playerInventory.add(new MagicFlower(Integer.parseInt(data[3])));
                                break;
                            case "Toy":
                                this.playerInventory.add(new Toy(Integer.parseInt(data[3])));
                                break;
                            default: // Generic Item, if no specific type was found.
                                this.playerInventory.add(new Item(data[2], Integer.parseInt(data[3])));
                                break;
                        }
                        break;
                    case "score":
                        this.score = Integer.parseInt(data[1]);
                        break;
                    case "petName":
                        this.petName = data[1];
                        break;
                    case "petSpecies":
                        this.petSpecies = data[1];
                        break;
                    case "petHealth":
                        this.petHealth = Integer.parseInt(data[1]);
                        break;
                    case "petHunger":
                        this.petHunger = Integer.parseInt(data[1]);
                        break;
                    case "petEnergy":
                        this.petEnergy = Integer.parseInt(data[1]);
                        break;
                    case "petHappiness":
                        this.petHappiness = Integer.parseInt(data[1]);
                        break;
                    default:
                        break;
                }
            }
    }

    /**
     * Aligins the data of the save file; that is, it updates the statistics saved
     * in the instance variables of the save file to match the current statistics of
     * the pet.
     */
    public void alignData(Pet currentPet) {
        this.playerBalance = currentPet.getBalance();
        this.petHealth = currentPet.getHealth();
        this.petHunger = currentPet.getFullness();
        this.petHappiness = currentPet.getHappiness();
        this.petEnergy = currentPet.getEnergy();
        this.petSpecies = currentPet.getPetType();
        this.petName = currentPet.getName();
        this.playerInventory = currentPet.getItems();
        this.score = currentPet.getScore();
    }

    /**
     * Should only be used by an authenticated user; resets the total playtime
     * statisiic of the save file.
     */
    public void resetTotalPlaytime() {
        this.totalPlaytime = 0;
    }

    /**
     * Should only be used by an authenticated user; resets the total average
     * playtime statistic of the save file.
     */
    public void resetAveragePlaytime() {
        this.averagePlaytimePerSession = 0;
    }

    /**
     * Getter for the save file's save ID
     * 
     * @return The file's save ID
     */
    public String getSaveID() {
        return this.id;
    }
    
}
