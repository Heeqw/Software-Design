package springframework.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringDI {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        PaymentService paymentService = ctx.getBean(PaymentService.class);
        paymentService.processPayment(1000.0);
    }
}
