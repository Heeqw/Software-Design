package springframework.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private PaymentProcessor paymentProcessor;

    public void processPayment(double amount) {
        paymentProcessor.processPayment(amount);
    }
}
