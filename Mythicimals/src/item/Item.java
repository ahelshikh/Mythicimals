package item;

/**
 * Object representing the items that will be held in the player's inventory and used on their pets to make their stats increase.
 * 
 * @author Liam Elliott
 */
public class Item {
    /** The name of the item. */
    protected String itemName;
    /** A short description about what the item does and by how much it raises a certain stat. */
    protected String itemDescription;
    /** The amount of the same item possessed by the player in their inventory. */
    protected int itemQuantity;
    /** The amount that the item will increase a stat by when used. */
    protected int effectiveness;
    /** The price of an item. */
    protected int price;
    /** The item's sprite. */
    protected String sprite;

    /**
     * The constructor for the Item class. Creates and initializes a new Item object.
     * @param name The name of the new item.
     * @param quantity How many uses the new item will have before it is exhausted.
     */
    public Item(String name, int quantity) {
        this.itemName = name;
        this.itemQuantity = quantity;
        this.itemDescription = "A generic item.";
        this.sprite = "Mythicimals/sprites/" + itemName.replace(" ", "") + ".png";
    }

    /**
     * Adds a given amount of uses to the item's current quantity. Used when the player buys more of the same item.
     * @param amount The number of additional uses the item will gain.
     */
    public void addAmount(int amount) {
        this.itemQuantity += amount;
    }

    /**
     * Retreieves the current number of uses remaining for a given item.
     * @return an integer corresponding to the number of uses remaining for the item.
     */
    public int getQuantity() {
        return this.itemQuantity;
    }

    /**
     * Retreieves the effectiveness of a given item.
     * @return an integer representing the effectiveness of the item.
     */
    public int getEffectiveness() {
        return this.effectiveness;
    }

    /**
     * Retreieves the name of a given item.
     * @return a String representing the name of the item.
     */
    public String getName() {
        return this.itemName;
    }

    /**
     * Retreieves the description of a given item.
     * @return a String representing the description belonging to the Item.
     */
    public String getDescription() {
        return this.itemDescription;
    }

    /**
     * Reduces the remaining quantity of uses left in an item after the player uses it on the pet.
     * @see decreaseAfterUse
     */
    public void useItem() {
        if (itemQuantity > 0) {
            itemQuantity--;
        }
        if (itemQuantity == 0) {
            System.out.println(itemName + " is depleted.");
        }
    }

    /**
     * Changes the description associated with a given Item object.
     * @param newDescription the new description of the item.
     */
    public void setDescription(String newDescription) {
        this.itemDescription = newDescription;
    }

    /**
     * Returns the price of a given Item object.
     * @return the amount of gold needed by the player to buy this item in the Store.
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Returns the path to the sprite of a given Item object.
     * @return a string corresponding to the path for the item's sprite image.
     */
    public String getSprite() {
        return this.sprite;
    }

    /**
     * Changes the value of a given Item object's effectiveness instance variable.
     * @param newEffectiveness the new value which will overwrite the old effectiveness value.
     */
    public void setEffectiveness(int newEffectiveness) {
        this.effectiveness = newEffectiveness;
    }
}
