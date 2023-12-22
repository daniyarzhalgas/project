package DesignPattern;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public double calculatePaymentFee(double amount) {
        return 0;
    }
}
