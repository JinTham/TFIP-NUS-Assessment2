package tfip.ssf.assessment2.Model;

import java.util.LinkedList;
import java.util.List;

public class Cart {
    
    private List<Item> cartList = new LinkedList<>();

    //Constructors
    public Cart(List<Item> cartList) {
        this.cartList = cartList;
    }

    public Cart() {
        //List<Item> cartList = new LinkedList<>();
    }

    //Getters & Setters
    public List<Item> getCartList() {
        return cartList;
    }

    public void setCartMap(List<Item> cartList) {
        this.cartList = cartList;
    }
    
    //Methods 
    public Item getItem(Item item) {
        for (int i=0;i<cartList.size();i++) {
            if (cartList.get(i).getItemName().equalsIgnoreCase(item.getItemName())) {
                return cartList.get(i);
            }
        }
        return null;
    }

    public void addItem(Item item) {
        Item existItem = this.getItem(item);
        if (existItem != null){
            existItem.updateQuantity(item.getQuantity());
        } else {
            this.cartList.add(item);
        }
    }

    public List<String> getItemNames() {
        List<String> itemList = new LinkedList<>();
        for (Item item : this.cartList) {
            itemList.add(item.getItemName());
        }
        return itemList;
    }
}
