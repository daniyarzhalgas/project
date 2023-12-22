package DesignPattern;

public class OrderContext {
    private OrderState state;

    public OrderContext() {
        // Initialize with a default state, e.g., InCartState
        this.state = new InCartState();
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void processOrder() {
        // Delegate the processing to the current state
        state.processOrder(this);
    }
}
