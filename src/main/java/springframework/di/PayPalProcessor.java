package springframework.di;

import org.springframework.stereotype.Component;

@Component
public class PayPalProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("processPayment with args: " + amount);
        // Logic to process payment using PayPal
    }
}