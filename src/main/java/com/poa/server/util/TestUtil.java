package com.poa.server.util;


public class TestUtil {



    private static String from = "cura@curacompliance.com";

    /*public static Session session() {
        // mail to
        String to = "imxushuai@gmail.com";

        // create prop
        Properties properties = System.getProperties();
        // set prop
        properties.setProperty("mail.smtp.host", "smtp.pepipost.com");
        properties.setProperty("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        // get session
        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("poadevuw", "poadevuw_33c95c0f72e4025f77f9fcfde0813e0f");
            }
        });
    }

    public static void main(String[] args) {
        try {
            MimeMessage message = new MimeMessage(session());
            // Set From
            message.setFrom(new InternetAddress(from));
            // Set To
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("1031893936@qq.com"));
            // Set Subject
            message.setSubject("subject");
            // content
            String content = "<!DOCTYPE html>" +
                    "<html lang=\"en\">" +
                    "<head>" +
                    "    <meta charset=\"UTF-8\">" +
                    "    <title>Title</title>" +
                    "</head>" +
                    "<body>" +
                    "Dear <Client Name>,<br /><br />" +
                    "Welcome to Cura Compliance Inc., where our mission is to improve the accessibility, creditability, and security of the substitute decision-making process. Our product, PoARegistry.ca, provides a simple, secure, and standardized platform for users to search, upload, and share Power of Attorney documents. <br /><br />" +
                    "You have been successfully registered as a PoARegistry.ca user, please log in using:<br /><br />" +
                    "    Website: <URL><br />" +
                    "    Username: <client email address><br />" +
                    "    Password: <system assigned password><br /><br />" +
                    "You are receiving this email because you or your legal representative signed up for services provided by PoARegistry.ca, a product of Cura Compliance Inc. <br /><br />" +
                    "If you believe you have received this email in error or no longer wish to receive emails from PoARegistry.ca, a product of Cura Compliance Inc., please contact us at info@curacompliance.com. <br /><br />" +
                    "Thank you for registering with PoARegistry.ca,<br /><br />" +
                    "Cura Compliance Inc.<br />" +
                    "<span style=\"font-size: 10px\">curacompliance.com</span><br />" +
                    "<span style=\"font-size: 10px\">PO Box 20051, Oaktown Plaza, Oakville, ON, L6K 3Y7</span><br />" +
                    "</body>" +
                    "</html>";
            content = content.replaceAll("<Client Name>", "xu shuai");
            content = content.replaceAll("<URL>", "<a>http://baidu.com</a>");
            content = content.replaceAll("<client email address>", "imxushuai@gmail.com");
            content = content.replaceAll("<system assigned password>", "12312414");
            message.setContent(content, "text/html");

            // send
            Transport.send(message);
            System.out.println("Sent email successfully....");
            System.out.println("mail content:");
//            log.info(content);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }*/
}
