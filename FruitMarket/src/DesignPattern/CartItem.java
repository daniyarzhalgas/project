package DesignPattern;

import model.Fruit;

public class CartItem {
    private Fruit fruit;
    private double quantity;

    public CartItem(Fruit fruit, double quantity) {
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public Fruit getProduct() {
        return fruit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}

