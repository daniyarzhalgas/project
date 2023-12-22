package DesignPattern;

import javafx.scene.control.Alert;
import model.Fruit;


public class ShoppingCartFacade {
    static Cart cart;

    public ShoppingCartFacade(Cart cart) {
        ShoppingCartFacade.cart = cart;
    }

    public void addItem(Fruit fruit, double quantity) {
        cart.addItem(fruit, quantity);
        notifyObservers(true);
    }

    public void removeItem(Fruit fruit) {
        cart.removeItem(fruit);
        System.out.println("remove item in shopping cart facade");
        notifyObservers(false);
    }

    public double getShippingCost() {
        // Calculate shipping cost based on cart contents
        return cart.getTotalPrice() >= 20 ? 0 : 0.99;
    }

    public double getOrderTotal() {
        return cart.getTotalPrice() + getShippingCost();
    }

    private void notifyObservers(boolean bool) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        if (bool) {
            alert.setContentText("successfully added");
        } else {
            alert.setContentText("successfully removed");
        }
        alert.show();
        // Notify observers about cart changes
    }

    public Cart getCart() {
        return cart;
    }
}


