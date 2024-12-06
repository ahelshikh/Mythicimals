package item;

/**
 * The Blanket is a low-tiered Gift item in the game, meant to be an inexpensive way of increasing the pet's happiness.
 * 
 * @author Liam Elliott
 */
public class Blanket extends Gift {
    /**
     * The constructor for the Blanket object.
     * @param quantity the number of uses that the blanket will have.
     */
    public Blanket(int quantity) {
        super("Blanket", quantity);
        this.effectiveness = 30;
        this.price = 20;
        this.itemDescription = "A soft and cozy blanket that raises your pet's happiness by " + effectiveness + " points.";
    }
}
