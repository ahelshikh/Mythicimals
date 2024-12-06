import item.*;

import java.util.ArrayList;

/**
 * The Pet class outlines all logic surrounding how the creatures in the game
 * will perform actions and the types of information that they will retain.
 * As the user interacts with the program, the data belonging to the pet will be
 * altered.
 * Some player-specific functionality, such as the score and inventory, are also
 * implemented here, since the Pet is the central focus of the game.
 * 
 * @author Liam Elliott
 */
public class Pet {
    /**
     * The name of the pet.
     */
    private String name;
    /**
     * The current health of the pet.
     */
    private int health;
    /**
     * The current happiness of the pet.
     */
    private int happiness;
    /**
     * The current fullness of the pet.
     */
    private int fullness;
    /**
     * The current energy level of the pet.
     */
    private int energy;
    /**
     * The rate at which the pet's happiness decreases over time.
     */
    private int happinessDecreaseRate;
    /**
     * The rate at which the pet's fullness decreases over time.
     */
    private int fullnessDecreaseRate;
    /**
     * The rate at which the pet's energy decreases over time.
     */
    private int energyDecreaseRate;
    /**
     * The maximum health the pet can have.
     */
    private int maxHealth;
    /**
     * The maximum happiness the pet can have.
     */
    private int maxHappiness;
    /**
     * The maximum fullness the pet can have.
     */
    private int maxFullness;
    /**
     * The maximum energy the pet can have.
     */
    private int maxEnergy;
    /**
     * The current state of the pet.
     */
    private int petState;
    /**
     * The type of pet (Griffin, Dragon or Unicorn).
     */
    private String petType = "";
    /**
     * Indicates whether the pet is in a forced sleep state.
     */
    private boolean isForcedSleep = false;
    /**
     * The score associated with the pet.
     */
    private int score;
    /**
     * The balance of the pet, likely representing the pet's available resources
     * (e.g., money).
     */
    int balance;
    /**
     * A list of items the pet has in its inventory. Includes the food and toys that
     * the player can give to the pet.
     */
    private ArrayList<Item> inventory;
    /**
     * A constant representing the normal state of the pet.
     */
    public static final int NORMAL = 0;
    /**
     * A constant representing the hungry state of the pet.
     */
    public static final int HUNGRY = 1;
    /**
     * A constant representing the angry state of the pet.
     */
    public static final int ANGRY = 2;
    /**
     * A constant representing the sleeping state of the pet.
     */
    public static final int SLEEPING = 3;
    /**
     * A constant representing the dead state of the pet.
     */
    public static final int DEAD = 4;
    /**
     * The cost associated with a visit to the vet for the pet's treatment or
     * checkup.
     */
    int vetCost = 50;

    /**
     * The constructor for the Pet object.
     * 
     * @param name the name that will be given to the new pet object.
     */
    public Pet(String name) {

        this.name = name;

        this.petState = NORMAL;
        // inventory field
        this.inventory = new ArrayList<Item>();

        // Default 0 of each food except 1 carrot
        inventory.add(new Carrot(1));
        inventory.add(new Burger(0));
        inventory.add(new GoldenApple(0));

        // Default 0 of each gift except 1 blanket
        inventory.add(new Blanket(1));
        inventory.add(new Toy(0));
        inventory.add(new MagicFlower(0));

        this.score = 0;
    }

    /**
     * Feeds the pet some Food object in order to increase the fullness statistic.
     * 
     * @param food the food being given to the Pet.
     */
    public void feed(Food food) {
        // Can't feed if dead, sleeping, angry, or full
        if (food.getQuantity() > 0 && petState != DEAD && petState != SLEEPING && petState != ANGRY) {
            if (!isFull()) {
                // increase fullness by value of the food
                fullness = Math.min(fullness + food.getEffectiveness(), maxFullness);
                food.useItem();
                updateState();
                System.out.println(name + " fed, fullness is now " + fullness);
            } else {
                System.out.println(name + " is full");
            }
        }
    }

    /**
     * Gives the Pet object a gift, usually from the player's inventory.
     * 
     * @param gift the gift being given to the Pet.
     */
    public void giveGift(Gift gift) {
        // Can't feed if dead, sleeping, or angry
        if (gift.getQuantity() > 0 && petState != DEAD && petState != SLEEPING) {
            // increase happiness by the value of the gift item
            happiness = Math.min(happiness + gift.getEffectiveness(), maxHappiness);
            gift.useItem();
            updateState();
            System.out.println(name + " given gift, happiness is now " + happiness);
        }
    }

    /**
     * Subtracts the value of an item from the remaining balance of the player.
     * 
     * @param item the item that the player is trying to buy.
     */
    public void purchaseItem(Item item) {
        item.addAmount(1);
        balance -= item.getPrice();
    }

    /**
     * Slightly raises the happiness of a given Pet by playing with them.
     */
    public void play() {
        // Can't play if dead of sleeping
        if (petState != DEAD && petState != SLEEPING) {
            // increase happiness after playing
            // Using min and max functions to make sure the stats don't go over the max or
            // under zero
            happiness = Math.min(happiness + 25, maxHappiness);
            updateState();
            System.out.println(name + " has played, happiness is now " + happiness);
        }
    }

    /**
     * Puts the pet to bed to raise the energy statistic, unless the pet has died.
     */
    public void takeToBed() {
        if (petState == DEAD) {
            System.out.println("Pet is dead");
            return;
        }

        if (petState != SLEEPING && energy != maxEnergy) {
            petState = SLEEPING;
            isForcedSleep = false;
            System.out.println("Is now sleeping");
        } else {
            System.out.println("Pet is not tired and will not sleep.");
        }
    }

    /**
     * A stronger version of takeToBed(); forces the pet into the sleeping state
     * regardless of their previous state, unless the pet is dead.
     */
    public void forceToSleep() {
        if (petState != DEAD) {
            petState = SLEEPING;
            isForcedSleep = true;
        }
    }

    /**
     * Wakes up the pet from the sleeping state.
     */
    public void wakeUp() {
        if (petState == SLEEPING && !isForcedSleep) {
            petState = NORMAL;
            System.out.println(name + " has woken up early.");
        } else if (isForcedSleep) {
            System.out.println(name + " cannot wake up until fully rested.");
        }
        updateState();
    }

    /**
     * Checks if the pet has collapsed from exhaustion, due to the energy statistic
     * being zero.
     * 
     * @return true if the Pet's energy is at zero, false otherise.
     */
    public boolean collapsed() {
        return isForcedSleep;
    }

    /**
     * Excerises the pet, which raises its health statistic but lowers its fullness and energy levels.
     */
    public void exercise() {
        // Can't exercise if dead, sleeping, or angry
        if (petState != DEAD && petState != SLEEPING && petState != ANGRY) {
            // increase health, decrease fullness and energy
            health = Math.min(health + 25, maxHealth);
            fullness = Math.max(fullness - 10, 0);
            energy = Math.max(energy - 10, 0);
            updateState();
            System.out.println(name + " exercised, health, fullness, and energy are now " + health + ", " + fullness
                    + ", and " + energy + " respectively");
        }
    }

    /**
     * Checks the current statisitics of a given Pet object and determines which
     * state the pet should be in.
     * For example, if the hunger statistic is at zero, the pet should go into the
     * hungry state.
     */
    public void updateState() {
        int previousState = petState;

        // Check if the pet is in the dead state. If so, deplete all stats to zero.
        if (health == 0) {
            petState = DEAD;
            this.setEnergy(0);
            this.setHappiness(0);
            this.setFullness(0);
            System.out.println(name + " died.");
            return;
        }

        // If the pet is sleeping, check if it has fully recovered its energy. If so,
        // return the pet to the normal state.
        if (petState == SLEEPING) {
            if (energy >= maxEnergy) {
                petState = NORMAL;
                isForcedSleep = false;
                System.out.println(name + " has woken up fully rested!");
            } else if (isForcedSleep) {
                return;
            }
        }

        // If pet's happiness levels are at 0, they should be in the angry state.
        if (happiness == 0) {
            petState = ANGRY;
        }
        // If the pet's fullness levels are at 0, they should be in the hungry state.
        else if (fullness == 0) {
            petState = HUNGRY;
        }
        // If the pet's energy levels are at 0, they should be in the sleeping state.
        else if (this.energy == 0) {
            petState = SLEEPING;
        }
        // Otherwise, if all statisitics are in acceptable levels, the pet should be in
        // the normal state.
        else if (petState != SLEEPING) {
            petState = NORMAL;
        }

        if (previousState != petState) {
            System.out
                    .println("Pet state changed from " + getStateName(previousState) + " to " + getStateName(petState));
        }
    }

    /**
     * Used for debugging; prints out all statistics of the given Pet object to the
     * terminal.
     */
    private void printPetStats() {
        System.out.println("Health: " + health);
        System.out.println("Happiness: " + happiness);
        System.out.println("Fullness: " + fullness);
        System.out.println("Energy: " + energy);
        System.out.println(name + "'s State: " + getStateName(petState));
        System.out.println();
    }

    /**
     * Updates the statistics of the pet every time the method is called in the game
     * loop. Generally, the energy should decrease unless the pet is sleeping or
     * dead. All other values should decrease regardless of awakeness.
     */
    public void updateStats() {
        if (petState == DEAD) {
            return;
        }
        if (petState == SLEEPING) {
            energy = Math.min(energy + 30, maxEnergy);
        } else {
            energy = Math.max(energy - energyDecreaseRate, 0);
        }
        if (fullness == 0) {
            happiness = Math.max(happiness - 10, 0);
            health = Math.max(health - 10, 0);
        }

        happiness = Math.max(happiness - happinessDecreaseRate, 0);
        fullness = Math.max(fullness - fullnessDecreaseRate, 0);

        updateState();

        printPetStats();
    }

    // -----------------------------------------------------------------------------------------------------------------------
    // Player methods

    /**
     * Takes the pet to the vet for medical care.
     * If the pet is healthly, dead, or the player cannot afford care, the pet wil not be healed.
     * Otherwise, the pet will be brough back to full health.
     */
    public void takePetToVet() {
        // If the pet is dead, then healing cannot be performed.
        if (petState == DEAD) {
            System.out.println("Your pet is already dead. It's too late to take it to the vet. :(");
            return;
        }

        // If the player cannot afford care, do not perform healing.
        if (balance < vetCost) {
            System.out.println("You don't have enough money to take your pet to the vet. You need $" + vetCost + ".");
            return;
        }

        // If the pet is uninjured, there is no need to perform any healing.
        if (this.health == this.maxHealth) {
            System.out.println("The pet is perfectly healthy; there is nothing that needs to be done!");
            return;
        }

        System.out.println("Taking " + name + " to the vet...");
        balance -= vetCost;
        Pet.this.setHealth(maxHealth);
        Pet.this.updateState();
        System.out.println("The vet restored " + name + "'s health to " + health + ". Your remaining balance is $"
                + balance + ".");
    }

    /**
     * Gets the cost of the veternarian care.
     * 
     * @return an integer corresponding to how much gold is needed to afford pet
     *         care.
     */
    public int getVetCost() {
        return vetCost;
    }

    /**
     * Returns the name of the pet's current state.
     * 
     * @param petState the state of the pet represented by an integer constant.
     * @return the name of the pet's current state as a string.
     */
    public String getStateName(int petState) {
        switch (petState) {
            case NORMAL:
                return "Normal";
            case HUNGRY:
                return "Hungry";
            case ANGRY:
                return "Angry";
            case SLEEPING:
                return "Sleeping";
            case DEAD:
                return "Dead";
            default:
                return "Unknown";
        }
    }

    /**
     * Returns the current state of the pet.
     * 
     * @return the current state of the pet as an integer.
     */
    public int getState() {
        return petState;
    }

    /**
     * Returns the current health of the pet.
     * 
     * @return an integer corresponding to the current health value of the pet.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the current happiness of the pet.
     * 
     * @return an integer corresponding to the current happiness value of the pet.
     */
    public int getHappiness() {
        return happiness;
    }

    /**
     * Returns the current fullness of the pet.
     * 
     * @return an integer corresponding to the fullness value of the pet.
     */
    public int getFullness() {
        return fullness;
    }

    /**
     * Returns the current energy of the pet.
     * 
     * @return an integer corresponding to the energy value of the pet.
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * Checks if the pet's fullness is at maximum.
     * 
     * @return true if the pet's fullness is at maximum, false otherwise.
     */
    public boolean isFull() {
        return this.fullness == maxFullness;
    }

    /**
     * Changes the current health of the pet.
     * 
     * @param newHealth the new health value of the pet.
     */
    public void setHealth(int newHealth) {
        this.health = newHealth;
    }

    /**
     * Chnages the current happiness of the pet.
     * 
     * @param newHappiness the new happiness value of the pet.
     */
    public void setHappiness(int newHappiness) {
        this.happiness = newHappiness;
    }

    /**
     * Sets the current fullness of the pet.
     * 
     * @param newFullness the new fullness value of the pet.
     */
    public void setFullness(int newFullness) {
        this.fullness = newFullness;
    }

    /**
     * Changes the current energy of the pet.
     * 
     * @param newEnergy the new energy value of the pet.
     */
    public void setEnergy(int newEnergy) {
        this.energy = newEnergy;
    }

    /**
     * Changes the maximum health value for the pet.
     * 
     * @param newMaxHealth the new maximum health value of the pet.
     */
    public void setMaximumHealth(int newMaxHealth) {
        this.maxHealth = newMaxHealth;
    }

    /**
     * Changes the maximum happiness value for the pet.
     * 
     * @param newMaxHappiness the new maximum happiness value of the pet.
     */
    public void setMaximumHappiness(int newMaxHappiness) {
        this.maxHappiness = newMaxHappiness;
    }

    /**
     * Changes the maximum fullness value for the pet.
     * 
     * @param newMaxFullness the new maximum fullness value of the pet.
     */
    public void setMaximumFullness(int newMaxFullness) {
        this.maxFullness = newMaxFullness;
    }

    /**
     * Changes the maximum energy value for the pet.
     * 
     * @param newMaxEnergy the new maximum energy value of the pet.
     */
    public void setMaximumEnergy(int newMaxEnergy) {
        this.maxEnergy = newMaxEnergy;
    }

    /**
     * Changes the rate at which the pet's happiness decreases.
     * 
     * @param newDecreaseRate The new happiness decrease rate of the pet.
     */
    public void setHappinessDecreaseRate(int newDecreaseRate) {
        this.happinessDecreaseRate = newDecreaseRate;
    }

    /**
     * Sets the rate at which the pet's fullness decreases.
     * 
     * @param newDecreaseRate The new fullness decrease rate of the pet.
     */
    public void setFullnessDecreaseRate(int newDecreaseRate) {
        this.fullnessDecreaseRate = newDecreaseRate;
    }

    /**
     * Sets the rate at which the pet's energy decreases.
     * 
     * @param newDecreaseRate The new energy descrease rate of the pet.
     */
    public void setEnergyDecreaseRate(int newDecreaseRate) {
        this.energyDecreaseRate = newDecreaseRate;
    }

    /**
     * Changes the Pet Type label attached to a given Pet object.
     * 
     * @param type the new type of Pet the object now is.
     */
    public void setPetType(String type) {
        this.petType = type;
    }

    /**
     * Changes the balance of the player associated with a given Pet object.
     * 
     * @param balance the new balance of gold for the player.
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Adds to the balance of the player associated with a given Pet object.
     * 
     * @param amount the namount to add to the alance of gold for the player.
     */
    public void addBalance(int amount) {
        this.balance += amount;
    }

    /**
     * Gets the remaining balance of the player associated with a given Pet object.
     * 
     * @return an integer corresponding with the amount of gold the player has left.
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Gets the name of a given Pet object.
     * 
     * @return the String corresponding to the pet's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the type of creature that the Pet object is.
     * @return a String corresponding to the type of animal that the pet is.
     */
    public String getPetType() {
        return petType;
    }

    /**
     * Gets the file path for the sprite of the pet.
     * 
     * @return a String that locates the image file for the pet's sprite.
     */
    public String getSprite() {
        String petSprite = "Mythicimals/sprites/" + petType.toLowerCase() + "_" + getStateName(petState).toLowerCase()
                + ".png";
        return petSprite;
    }

    /**
     * Gets the maximum health value of a given Pet object.
     * 
     * @return the maximum amount of health that the Pet can have, as an integer.
     */
    public int getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * Gets the maximum happiness value of a given Pet object.
     * 
     * @return the maximum amount of happiness that the Pet can have, as an integer.
     */
    public int getMaxHappiness() {
        return this.maxHappiness;
    }

    /**
     * Gets the maximum fullness value of a given Pet object.
     * 
     * @return the maximum amount of fullness that the Pet can have, as an integer.
     */
    public int getMaxFullness() {
        return this.maxFullness;
    }

    /**
     * Gets the maximum energy value of a given Pet object.
     * 
     * @return the maximum amount of energy that the Pet can have, as an integer.
     */
    public int getMaxEnergy() {
        return this.maxEnergy;
    }

    /**
     * Checks the player's inventory to see if the player has at least one Food item in their inventory.
     * @return true if there is one or more Food items in the inventory, false otherwise.
     */
    public boolean hasFood() {
        for (Item item : inventory) {
            if (item instanceof Food && item.getQuantity() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the player's inventory to see if the player has at least one Gift item in their inventory.
     * @return true if there is one or more Gift items in the inventory, false otherwise.
     */
    public boolean hasGift() {
        for (Item item : inventory) {
            if (item instanceof Gift && item.getQuantity() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the hunger decrease rate of a given Pet object.
     * 
     * @return an integer representing the hunger decrease rate of the Pet.
     */
    public int getHungerDecreaseRate() {
        return this.fullnessDecreaseRate;
    }

    /**
     * Gets the happiness decrease rate of a given Pet object.
     * 
     * @return an integer representing the happiness decrease rate of the Pet.
     */
    public int getHappinessDecreaseRate() {
        return this.happinessDecreaseRate;
    }

    /**
     * Gets the energy decrease rate of a given Pet object.
     * 
     * @return an integer representing the energy decrease rate of the Pet.
     */
    public int getEnergyDecreaseRate() {
        return this.energyDecreaseRate;
    }

    // -----------------------------------------------------------------------------------------------------------------------
    // Inventory Methods

    /**
     * Returns the list of items in the inventory.
     * 
     * @return the list of items
     */
    public ArrayList<Item> getItems() {
        return inventory;
    }

    /**
     * Sets the list of items in the inventory.
     * 
     * @param items the list of items to set
     */
    public void setItems(ArrayList<Item> items) {
        this.inventory = items;
    }

    /**
     * Returns the first item in the inventory.
     * 
     * @return the first item, or null if the inventory is empty
     */
    public Item getFirstItem() {
        if (inventory.isEmpty()) {
            return null;
        }
        return inventory.get(0);
    }

    /**
     * Returns the item at the specified position in the inventory.
     * 
     * @param x the position of the item to return
     * @return the item at the specified position
     */
    public Item getItem(int x) {
        return inventory.get(x);
    }

    /**
     * Adds an item to the inventory.
     * 
     * @param item the item to add
     */
    public void addItem(Item item) {
        inventory.add(item);
    }

    /**
     * Returns the number of items in the inventory.
     * 
     * @return the size of the inventory
     */
    public int getInventorySize() {
        return inventory.size();
    }

    // ---------------------------------------------------------------------
    // Score Methods

    /**
     * Changes the total amount of score points a given Pet object has.
     * 
     * @param newScore the new score that the Pet should have.
     */
    public void setScore(int newScore) {
        this.score = newScore;
    }

    /**
     * Adds points to the score of a given Pet object.
     * 
     * @param scoreToAdd how much score should be added, as an integer.
     */
    public void addScore(int scoreToAdd) {
        this.score += scoreToAdd;
    }

    /**
     * Returns the score associated with a given Pet object.
     * 
     * @return the current score of the Pet object.
     */
    public int getScore() {
        return this.score;
    }
}
