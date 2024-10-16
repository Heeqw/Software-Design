package springframework.livedemo.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import springframework.livedemo.core.Notifier;

public class Main {
    public static void main(String[] args) {
        // initialize the application context
        ApplicationContext context = new AnnotationConfigApplicationContext(ProductConfig.class);

        // get the product bean from the context
        Notifier notifier = context.getBean(Notifier.class);
        // send a notification
        notifier.send("Hello, this is a notification!");
    }
}
