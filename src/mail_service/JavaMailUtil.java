package mail_service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaMailUtil {
    private static String account;
    public JavaMailUtil(String email) {
        this.account = email;
    }

    public static void send(String destinatario, String oggetto, String testo) {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String password = "vacr gbnh ghon swsj";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(account, password);
            }
        });

        Message message = prepareMessage(session, destinatario, oggetto, testo);
        try {
            Transport.send(message);
            System.out.println("Email inviata");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static Message prepareMessage(Session session, String destinatario, String oggetto, String testo) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(account));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(oggetto);
            message.setText(testo);
            return message;
        } catch (MessagingException e) {
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
