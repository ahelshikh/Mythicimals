package item;

/**
 * The Golden Apple is a top-tiered Food item that provides the absolute best effectiveness, for the highest cost.
 * 
 * @author Liam Elliott
 */
public class GoldenApple extends Food {
    /**
     * The constructor for the new Golden Apple object.
     * @param quantity the number of uses that the new Golden Apple will have.
     */
    public GoldenApple(int quantity) {
        super("Golden Apple", quantity);
        this.effectiveness = 1000;
        this.price = 90;
        this.itemDescription = "A rare golden apple that fully restores your pet's fullness.";
    }
}
