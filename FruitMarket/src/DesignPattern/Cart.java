package DesignPattern;

import model.Fruit;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addItem(Fruit fruit, double quantity) {
        CartItem existingItem = getItem(fruit);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            items.add(new CartItem(fruit, quantity));
        }
    }

    public void removeItem(Fruit fruit) {
        CartItem item = getItem(fruit);
        items.remove(item);
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double total = 0.0;
        for (CartItem item : items) {
            if (item == null) return 0;
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    public CartItem getItem(Fruit fruit) {
        for (CartItem item : items) {
            if (item.getProduct().equals(fruit)) {
                return item;
            }
        }
        return null;
    }
}

