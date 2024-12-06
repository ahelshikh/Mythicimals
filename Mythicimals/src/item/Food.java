package item;

/**
 * The Food class describes the food items that the player will be able to give their pet to raise their fullness statistic.
 * 
 * @author Liam Elliott
 */
public class Food extends Item {
    /**
     * The constructor for the Food type of items.
     * @param itemName the name of the newly-created Food.
     * @param itemQuantity the initial number of uses remaining for the newly-created Food.
     */
    public Food(String itemName, int itemQuantity) {
        super(itemName, itemQuantity);
        this.setDescription("A generic food.");
    }
}
