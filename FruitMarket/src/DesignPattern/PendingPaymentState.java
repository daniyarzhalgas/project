package DesignPattern;

public class PendingPaymentState extends OrderState {
    @Override
    public void processOrder(OrderContext context) {
        // Perform actions specific to the "Pending Payment" state
        System.out.println("Processing order in the Pending Payment state");

        // Transition to the next state, e.g., ShippedState
        context.setState(new ShippedState());
    }
}
