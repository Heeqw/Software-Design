package springframework.naive;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NotifierTest {
    @Test
    public void emailer() {
        // 每次测试都会调用SpellChecker.checkSpell和MailClient.sendMail
        boolean result = new Notifier().send(" text ");
        // 问题：如何验证SpellChecker.checkSpell和MailClient.sendMail是否被调用？
        assertTrue(result);
    }
}
