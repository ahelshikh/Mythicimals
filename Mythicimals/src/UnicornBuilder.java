/**
 * The Unicorn Builder class helps facilitate creating new Unicorn pets by automatically setting their statistic behaviours.
 * 
 * @author Liam Eliott
 */
public class UnicornBuilder extends PetBuilder {
    /**
     * Sets the maximum health for the new Unicorn object.
     */
    public void buildHealth() {
        newPet.setMaximumHealth(100);
        newPet.setHealth(newPet.getMaxHealth());
    }
    
    /**
     * Sets the maximum happiness value, as well as the happiness decrease rate, for the new Unicorn object.
     */
    public void buildHappiness() {
        newPet.setMaximumHappiness(200);
        newPet.setHappinessDecreaseRate(15);
        newPet.setHappiness(newPet.getMaxHappiness());
    }

    /**
     * Sets the maximum hunger value, as well as the hunger decrease rate, for the new Unicorn object.
     */
    public void buildHunger() {
        newPet.setMaximumFullness(125);
        newPet.setFullnessDecreaseRate(10);
        newPet.setFullness(newPet.getMaxFullness());
    }

    /**
     * Sets the maximum energy value, as well as the energy decrease rate, for the new Unicorn object.
     */
    public void buildTiredness() {
        newPet.setMaximumEnergy(150);
        newPet.setEnergyDecreaseRate(30);
        newPet.setEnergy(newPet.getMaxEnergy());
    }

    /**
     * Sets the gold balance of the player associated with the new Unicorn object.
     * @param balance the starting balance of the player associated with the newly-created Unicorn.
     */
    public void buildBalance(int balance) {
        newPet.setBalance(balance);
    }

    /**
     * Initializes the petType variable in correspondence with the new Unicorn object.
     */
    public void buildPetType() {
        newPet.setPetType("Unicorn");
    }
}

