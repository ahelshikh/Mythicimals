package item;

/**
 * The Carrot is a low-tiered Food item that provides an inexpensive option for feeding the pet.
 * 
 * @author Liam Elliott
 */
public class Carrot extends Food {
    /**
     * The constructor for the Carrot object.
     * @param quantity the number of uses that the new Carrot will have.
     */
    public Carrot(int quantity) {
        super("Carrot", quantity);
        this.effectiveness = 20;
        this.price = 20;
        this.itemDescription = "A fresh carrot that raises your pet's fullness by " + effectiveness + " points.";
    }
}
