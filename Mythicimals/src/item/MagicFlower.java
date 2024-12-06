package item;

/**
 * The Magic Flower is a top-tiered Gift item that gives the absolute best effectiveness for the highest price.
 * 
 * @author Liam Elliott
 */
public class MagicFlower extends Gift {
    /**
     * The constructor for the Magic Flower object.
     * @param quantity the number of uses that the new Magic Flower will have.
     */
    public MagicFlower(int quantity) {
        super("Magic Flower", quantity);
        this.effectiveness = 1000;
        this.price = 80;
        this.itemDescription = "A beautiful magical flower that fully restores your pet's happiness.";
    }
}
