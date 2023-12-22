package DesignPattern;

import model.Fruit;

public class RemoveFromCartCommand implements Command {
    private ShoppingCartFacade cartFacade;
    private Fruit fruit;

    public RemoveFromCartCommand(ShoppingCartFacade cartFacade, Fruit fruit) {
        this.cartFacade = cartFacade;
        this.fruit = fruit;
    }

    @Override
    public void execute() {
        System.out.println("execute remove from cart command");
        cartFacade.removeItem(fruit);
    }
}
