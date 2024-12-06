/**
 * The PetBuilder class implements the Builder design pattern, to help facilitate the creation of new Pet objects in the game.
 * 
 * @author Liam Elliott
 */
abstract class PetBuilder {
    /** The newly-created Pet object, which is either a Dragon, Griffin or Unicorn, depending on the type of Builder used by the program. */
    protected Pet newPet;
    /** The given name of the newly-created Pet object. */
    String newPetName = "";
    
    /**
     * Returns the newly-created Pet object constructed by any type of PetBuilder.
     * @return the new Pet object which has all of the attributes that were passed to the Pet Builder.
     */
    public Pet getPet() {
        return newPet;
    }

    /**
     * Constructs the new Pet object with default settings.
     * @param name the name given to the newly-created Pet.
     */
    public void createNewPet(String name) {
        newPet = new Pet(name);
    }

    /**
     * This method will set the hunger-related statistics of the newly-created Pet, with the values varying depending on the pet type.
     */
    public abstract void buildHunger();
    /**
     * This method will set the health-related statistics of the newly-created Pet, with the values varying depending on the pet type.
     */
    public abstract void buildHealth();
    /**
     * This method will set the energy-related statistics of the newly-created Pet, with the values varying depending on the pet type.
     */
    public abstract void buildTiredness();
    /**
     * This method will set the happiness-related statistics of the newly-created Pet, with the values varying depending on the pet type.
     */
    public abstract void buildHappiness();
    /**
     * This method will set the gold balance of the newly-created Pet.
     */
    public abstract void buildBalance(int balance);
    /**
     * This method will set the value of the Pet's petType depending on which builder is being used.
     */
    public abstract void buildPetType();
}