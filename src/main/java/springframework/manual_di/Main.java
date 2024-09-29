package springframework.manual_di;

import springframework.manual_di.core.Notifier;
import springframework.manual_di.product.MailClientImpl;
import springframework.manual_di.product.SpellCheckerImpl;

public class Main {
    public static void main(String[] args) {
        Notifier emailer = new Notifier(
                new SpellCheckerImpl(),
                new MailClientImpl());
        emailer.send("     test      ");
    }
}
