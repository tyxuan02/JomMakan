package com.example.jommakan;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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

    public void sendReportedIssue(String user_email_address, String description, Bitmap image, Context context) throws MessagingException {
        String recipientEmail = "jommakan2007@gmail.com";
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

        Message message = null;

        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Report An Issue");

            BodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(description + "\n\nFrom: " + user_email_address);

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(textBodyPart);

            if (image != null) {
                // Create a new file to save the image
                File imageFile = new File(context.getExternalFilesDir(null), "image.jpg");

                // Obtain an OutputStream from the file
                OutputStream outputStream = new FileOutputStream(imageFile);

                // Write the image to the OutputStream
                image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

                // Flush and close the OutputStream
                outputStream.flush();
                outputStream.close();

                // Create a new MimeBodyPart to hold the image file
                BodyPart imageBodyPart = new MimeBodyPart();
                imageBodyPart.setDataHandler(new DataHandler(new FileDataSource(imageFile)));
                imageBodyPart.setFileName("image.jpg");
                multipart.addBodyPart(imageBodyPart);
            }

            message.setContent(multipart);

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }

        assert message != null;
        Transport.send(message);
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
