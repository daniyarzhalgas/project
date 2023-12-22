package DesignPattern;

import model.Fruit;

public class AddToCartCommand implements Command {
    private ShoppingCartFacade cartFacade;
    private Fruit fruit;
    private double quantity;

    public AddToCartCommand(ShoppingCartFacade cartFacade, Fruit fruit, double quantity) {
        this.cartFacade = cartFacade;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    @Override
    public void execute() {
        cartFacade.addItem(fruit, quantity);
    }
}
