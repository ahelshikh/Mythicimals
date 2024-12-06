package item;

/**
 * The Burger is a mid-tiered Food item in the game, meant to be the best-of-both-worlds in terms of cost and effectiveness.
 * 
 * @author Liam Elliott
 */
public class Burger extends Food {
    /**
     * The constructor for the Burger object.
     * @param quantity the number of uses that the new Burger object will have.
     */
    public Burger(int quantity) {
        super("Burger", quantity);
        this.effectiveness = 50;
        this.price = 40;
        this.itemDescription = "A juicy burger that raises your pet's fullness by " + effectiveness + " points.";
    }
}
