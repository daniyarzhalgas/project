package DesignPattern;

public class ShippedState extends OrderState {
    @Override
    public void processOrder(OrderContext context) {
        // Perform actions specific to the "Shipped" state
        System.out.println("Processing order in the Shipped state");

        // Transition to another state or perform additional actions
        // For simplicity, let's transition back to InCartState
        context.setState(new InCartState());
    }
}
