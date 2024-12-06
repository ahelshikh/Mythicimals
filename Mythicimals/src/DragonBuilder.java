/**
 * The Dragon Builder class helps facilitate creating new Dragon pets by automatically setting their statistic behaviours.
 * 
 * @author Liam Eliott
 */
public class DragonBuilder extends PetBuilder {
    /**
     * Sets the maximum health for the new Dragon object.
     */
    public void buildHealth() {
        newPet.setMaximumHealth(200);
        newPet.setHealth(newPet.getMaxHealth());
    }
    
    /**
     * Sets the maximum happiness value, as well as the happiness decrease rate, for the new Dragon object.
     */
    public void buildHappiness() {
        newPet.setMaximumHappiness(100);
        newPet.setHappinessDecreaseRate(25);
        newPet.setHappiness(newPet.getMaxHappiness());
    }

    /**
     * Sets the maximum hunger value, as well as the hunger decrease rate, for the new Dragon object.
     */
    public void buildHunger() {
        newPet.setMaximumFullness(150);
        newPet.setFullnessDecreaseRate(20);
        newPet.setFullness(newPet.getMaxFullness());
    }

    /**
     * Sets the maximum energy value, as well as the energy decrease rate, for the new Dragon object.
     */
    public void buildTiredness() {
        newPet.setMaximumEnergy(200);
        newPet.setEnergyDecreaseRate(15);
        newPet.setEnergy(newPet.getMaxEnergy());
    }

    /**
     * Sets the gold balance of the player associated with the new Dragon object.
     * @param balance the starting balance of the player associated with the newly-created Dragon.
     */
    public void buildBalance(int balance) {
        newPet.setBalance(balance);
    }

    /**
     * Initializes the petType variable in correspondence with the new Dragon object.
     */
    public void buildPetType() {
        newPet.setPetType("Dragon");
    }
}

