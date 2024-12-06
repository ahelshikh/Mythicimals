/**
 * The Griffin Builder class helps facilitate creating new Griffin pets by automatically setting their statistic behaviours.
 * 
 * @author Liam Eliott
 */
public class GriffinBuilder extends PetBuilder {
    /**
     * Sets the maximum health for the new Griffin object.
     */
    public void buildHealth() {
        newPet.setMaximumHealth(150);
        newPet.setHealth(newPet.getMaxHealth());
    }
    
    /**
     * Sets the maximum happiness value, as well as the happiness decrease rate, for the new Griffin object.
     */
    public void buildHappiness() {
        newPet.setMaximumHappiness(150);
        newPet.setHappinessDecreaseRate(10);
        newPet.setHappiness(newPet.getMaxHappiness());
    }

    /**
     * Sets the maximum hunger value, as well as the hunger decrease rate, for the new Griffin object.
     */
    public void buildHunger() {
        newPet.setMaximumFullness(175);
        newPet.setFullnessDecreaseRate(35);
        newPet.setFullness(newPet.getMaxFullness());
    }

    /**
     * Sets the maximum energy value, as well as the energy decrease rate, for the new Griffin object.
     */
    public void buildTiredness() {
        newPet.setMaximumEnergy(100);
        newPet.setEnergyDecreaseRate(20);
        newPet.setEnergy(newPet.getMaxEnergy());
    }

    /**
     * Sets the gold balance of the player associated with the new Griffin object.
     * @param balance the starting balance of the player associated with the newly-created Griffin.
     */
    public void buildBalance(int balance) {
        newPet.setBalance(balance);
    }
    
    /**
     * Initializes the petType variable in correspondence with the new Griffin object.
     */
    public void buildPetType() {
        newPet.setPetType("Griffin");
    }
}
