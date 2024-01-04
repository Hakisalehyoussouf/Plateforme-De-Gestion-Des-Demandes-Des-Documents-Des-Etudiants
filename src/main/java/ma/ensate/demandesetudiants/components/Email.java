package ma.ensate.demandesetudiants.components;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Properties;



import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;




@Component
public class Email {

    @Value("${mail.from}")
    private String from;

    @Value("${mail.password}")
    private String password;

    public void envoie(String to,String document) throws IOException {

        //final String from = "salehyoussouf.haki@etu.uae.ac.ma";
        //final String from = "limsaley@gmail.com";
        //final String password = "xoguypfvrjryebwm";

       // final String from = "hakisalehyoussouf13@gmail.com";
        //final String password = "Haki13@gmail.com";

        //final String from = "adnan.chafai@etu.uae.ac.ma";
        //final String password = "Yama1234";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        //prop.put("mail.smtp.socketFactory.port", "465");
        //prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        try{

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(document);

            String msg = "Bonne réception";

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(new File(document));
            multipart.addBodyPart(attachmentBodyPart);
            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Mail successfully sent..");
        }catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void envoieRejet(String to) {



        //final String from = "salehyoussouf.haki@etu.uae.ac.ma";
        //final String from = "limsaley@gmail.com";
        //final String password = "xoguypfvrjryebwm";

        // final String from = "hakisalehyoussouf13@gmail.com";
        //final String password = "Haki13@gmail.com";

        //final String from = "adnan.chafai@etu.uae.ac.ma";
        //final String password = "Yama1234";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        //prop.put("mail.smtp.socketFactory.port", "465");
        //prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        try{

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Rejet de Demande");
            message.setText("Votre demande a été rejeté! Veuillez rendre à l'Ecole pour plus d'information!");

            Transport.send(message);

            System.out.println("Mail successfully sent..");
        }catch (MessagingException e) {
            System.out.println(e.getMessage());
        }


    }
}
