package item;

/**
 * The Gift class represents the gift items that the player will be able to give their pet during gameplay that will increase the happiness statisitic.
 * 
 * @author Liam Elliott
 */
public class Gift extends Item {
    /**
     * The constructor for the Gift type of items.
     * @param itemName the name of the newly-created Gift.
     * @param itemQuantity the initial number of uses remaining for the newly-created Gift.
     */
    public Gift(String itemName, int itemQuantity) {
        super(itemName, itemQuantity);
        this.setDescription("A generic gift.");
    }
}
