package tfip.ssf.assessment2.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Item {

    @NotNull(message = "Item is mandatory field")
    private String itemName;

    @NotNull(message = "Cannot leave quantity field empty!")
    @Min(value = 1,message = "You must add at least 1 item!")
    private Integer quantity;
    
    //Constructors
    public Item() {
    }

    public Item(String itemName, Integer quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

    //Getters & Setters
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    } 
    
    //Methods
    public void updateQuantity(Integer quantity) {
        this.quantity += quantity;
    }
    
}
