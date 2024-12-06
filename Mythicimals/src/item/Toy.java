package item;

/**
 * The Toy is a mid-tiered Gift item in the game, which acts as a compromise between cost and effectiveness.
 * 
 * @author Liam Elliott
 */
public class Toy extends Gift {
    /**
     * The constructor for the Toy object.
     * @param quantity the numeber of uses that the new Toy will have.
     */
    public Toy(int quantity) {
        super("Toy", quantity);
        this.effectiveness = 60;
        this.price = 40;
        this.itemDescription = "A fun toy that raises your pet's happiness by " + effectiveness + " points.";
    }
}
