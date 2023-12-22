package DesignPattern;

public class PayPalPayment implements PaymentStrategy {
    @Override
    public double calculatePaymentFee(double amount) {
        return amount * 0.1;
    }
}
