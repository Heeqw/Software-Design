package springframework.naive;

class MailClient {
    public boolean sendMail(String text) {
        System.out.println("send mail with mail client:" + text);
        return true;
    }
}