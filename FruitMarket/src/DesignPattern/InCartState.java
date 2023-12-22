package DesignPattern;

public class InCartState extends OrderState {
    @Override
    public void processOrder(OrderContext context) {
        // Perform actions specific to the "In Cart" state
        System.out.println("Processing order in the In Cart state");

        // Transition to the next state, e.g., ShippedState
        context.setState(new PendingPaymentState());
    }
}
