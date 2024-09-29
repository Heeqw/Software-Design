package springframework.container_di.product;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import springframework.container_di.core.Notifier;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(
                ProductConfig.class);
        Notifier emailer = ctx.getBean(Notifier.class);
        emailer.send("     test      ");
    }
}
