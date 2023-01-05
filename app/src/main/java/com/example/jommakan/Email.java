package com.example.jommakan;

import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

    private String verificationCode;

    public void sendVerificationEmail(String recipientEmail, String typeOfEmail) throws MessagingException {

        String senderEmail = "jommakan2007@gmail.com"; //jommakan2007@gmail.com
        final String password = "krbxgibdrqntiwuu"; //Jommakan2007!

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Create a session
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        // Create a new message
        // Check type of email
        Message message;
        if (typeOfEmail.equals("user")) {
            // If typeOfEmail is "user"
            message = prepareMessageForUser(session, senderEmail, recipientEmail);
        } else {
            // If typeOfEmail is "forgetPassword"
            message = prepareMessageForForgetPassword(session, senderEmail, recipientEmail);
        }

        // Send an email
        assert message != null;
        Transport.send(message);
    }

    /**
     * Prepare a message that will be sent to user for account verification purpose
     * @param session a session created in sendVerificationEmail method
     * @param senderEmail email address of the sender
     * @param recipientEmail email address of the recipient
     * @return message that contains html part and embedded image
     */
    private Message prepareMessageForUser(Session session, String senderEmail, String recipientEmail) {
        try {
            // Generate a verification code
            verificationCodeGenerator();

            // Prepare a new message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Jom Makan Account Verification");
            message.setText("Please verify your Jom Makan account.\n" + getVerificationCode());

            return message;

        } catch (Exception ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Prepare a message that will be sent to user for forgot-password purpose
     * @param session a session created in sendVerificationEmail method
     * @param senderEmail email address of the sender
     * @param recipientEmail email address of the recipient
     * @return message that contains html part and embedded image
     */
    private Message prepareMessageForForgetPassword(Session session, String senderEmail, String recipientEmail) {
        try {
            // Generate a verification code
            verificationCodeGenerator();

            // Prepare a new message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Reset Your Jom Makan Account Password");
            message.setText("Your reset account password is generated.\n" + getVerificationCode());

            return message;

        } catch (Exception ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Randomly generate a verification code that will be sent to user or admin email address
     * And assign it to verification code using setVerification method
     */
    private void verificationCodeGenerator() {
        // Generate a verification code with 6 digits
        Random r = new Random();
        final int MAX = 999999;
        final int MIN = 100000;
        setVerificationCode(String.valueOf(r.nextInt(MAX - MIN + 1) + MIN));
    }

    /**
     * Set the verification code
     * @param verificationCode verification code that sent to user or admin email address
     */
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    /**
     * Get the verification code
     * @return verification code that sent to user or admin email address
     */
    public String getVerificationCode() {
        return this.verificationCode;
    }
}
