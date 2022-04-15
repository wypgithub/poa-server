package com.poa.server.util;

import java.util.Arrays;
import java.util.List;

public class MailContentUtil {

    private static String SUCCESSFUL_REGISTRATION_CONTENT = "<!DOCTYPE html>" +
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

    private static String SUCCESSFUL_CAPACITY_ASSESSMENT_UPLOAD = "<!DOCTYPE html>" +
            "<html lang=\"en\">" +
            "<head>" +
            "    <meta charset=\"UTF-8\">" +
            "    <title>Title</title>" +
            "</head>" +
            "<body>" +
            "Dear <Client Name>,<br /><br />" +
            "This email is to inform you that <Capacity Assessor> has uploaded a Capacity Assessment Report for <PoA Client Name>, File ID: <File ID>.<br /><br />" +
            "If you believe you have received this email in error or no longer wish to receive emails from PoARegistry.ca, a product of Cura Compliance Inc., please contact us at info@curacompliance.com.<br /><br />" +
            "Thank you for using PoARegistry.ca,<br /><br />" +
            "" +
            "Cura Compliance Inc.<br />" +
            "<span style=\"font-size: 10px\">curacompliance.com</span><br />" +
            "<span style=\"font-size: 10px\">PO Box 20051, Oaktown Plaza, Oakville, ON, L6K 3Y7</span><br />" +
            "</body>" +
            "</html>";

    private static String SUCCESSFUL_POA_REVOCATION = "<!DOCTYPE html>" +
            "<html lang=\"en\">" +
            "<head>" +
            "    <meta charset=\"UTF-8\">" +
            "    <title>Title</title>" +
            "</head>" +
            "<body>" +
            "Dear <Client Name>,<br /><br />" +
            "This email is to inform you that <Lawyer> has revoked a PoA document, File ID: <File ID>.<br /><br />" +
            "If you believe you have received this email in error or no longer wish to receive emails from PoARegistry.ca, a product of Cura Compliance Inc., please contact us at info@curacompliance.com. <br /><br />" +
            "Thank you for using PoARegistry.ca,<br /><br />" +
            "" +
            "Cura Compliance Inc.<br />" +
            "<span style=\"font-size: 10px\">curacompliance.com</span><br />" +
            "<span style=\"font-size: 10px\">PO Box 20051, Oaktown Plaza, Oakville, ON, L6K 3Y7</span><br />" +
            "</body>" +
            "</html>";

    private static String FILE_SHARE_REQUEST = "<!DOCTYPE html>" +
            "<html lang=\"en\">" +
            "<head>" +
            "    <meta charset=\"UTF-8\">" +
            "    <title>Title</title>" +
            "</head>" +
            "<body>" +
            "Dear <Client Name>,<br /><br />" +
            "You are receiving this email because a Power of Attorney document has been shared with you. To preview the document, please click on: <URL>. This link will remain valid for 3 days.<br /><br />" +
            "If you believe you have received this email in error or no longer wish to receive emails from PoARegistry.ca, a product of Cura Compliance Inc., please contact us at info@curacompliance.com. <br /><br />" +
            "" +
            "Thank you for using PoARegistry.ca,<br /><br />" +
            "Cura Compliance Inc.<br />" +
            "<span style=\"font-size: 10px\">curacompliance.com</span><br />" +
            "<span style=\"font-size: 10px\">PO Box 20051, Oaktown Plaza, Oakville, ON, L6K 3Y7</span><br />" +
            "</body>" +
            "</html>";

    private static String SDM_INVITATION_REQUEST = "<!DOCTYPE html>" +
            "<html lang=\"en\">" +
            "<head>" +
            "    <meta charset=\"UTF-8\">" +
            "    <title>Title</title>" +
            "</head>" +
            "<body>" +
            "Dear <Client Name>,<br /><br />" +
            "You are receiving this email because you have been identified to as a Substitution Decision Maker. To view the details, please click on: <URL><br /><br />" +
            "If you believe you have received this email in error or no longer wish to receive emails from PoARegistry.ca, a product of Cura Compliance Inc., please contact us at info@curacompliance.com. <br /><br />" +
            "" +
            "Thank you for using PoARegistry.ca,<br /><br />" +
            "Cura Compliance Inc.<br />" +
            "<span style=\"font-size: 10px\">curacompliance.com</span><br />" +
            "<span style=\"font-size: 10px\">PO Box 20051, Oaktown Plaza, Oakville, ON, L6K 3Y7</span><br />" +
            "</body>" +
            "</html>";

    private static String ASSESSOR_INVITATION_REQUEST = "<!DOCTYPE html>" +
            "<html lang=\"en\">" +
            "<head>" +
            "    <meta charset=\"UTF-8\">" +
            "    <title>Title</title>" +
            "</head>" +
            "<body>" +
            "Dear <Client Name>,<br /><br />" +
            "You are receiving this email because a request for your services has been submitted. To view the details, please click on: <URL><br /><br />" +
            "If you believe you have received this email in error or no longer wish to receive emails from PoARegistry.ca, a product of Cura Compliance Inc., please contact us at info@curacompliance.com. <br /><br />" +
            "" +
            "" +
            "Thank you for using PoARegistry.ca,<br /><br />" +
            "Cura Compliance Inc.<br />" +
            "<span style=\"font-size: 10px\">curacompliance.com</span><br />" +
            "<span style=\"font-size: 10px\">PO Box 20051, Oaktown Plaza, Oakville, ON, L6K 3Y7</span><br />" +
            "</body>" +
            "</html>";

    public static String successfulRegistration(String clientName, String url, String username, String password) {
        return getMailContent("SUCCESSFUL_REGISTRATION_CONTENT", clientName, url, username, password);
    }

    public static String successfulCapacityAssessmentUpload(String clientName, String capacityAssessor, String poAClientName, String fileId) {
        return getMailContent("SUCCESSFUL_CAPACITY_ASSESSMENT_UPLOAD", clientName, capacityAssessor, poAClientName, fileId);
    }

    public static String successfulPoaRevocation(String clientName, String lawyer, String fileId) {
        return getMailContent("SUCCESSFUL_POA_REVOCATION", clientName, lawyer, fileId);
    }

    public static String fileShareRequest(String clientName, String url) {
        return getMailContent("FILE_SHARE_REQUEST", clientName, url);
    }

    public static String sdmInvitationRequest(String clientName, String url) {
        return getMailContent("SDM_INVITATION_REQUEST", clientName, url);
    }

    public static String assessorInvitationRequest(String clientName, String url) {
        return getMailContent("ASSESSOR_INVITATION_REQUEST", clientName, url);
    }


    public static String getMailContent(String type, String... data) {
        String result = "";
        List<String> params =  Arrays.asList(data);
        switch (type) {
            case "SUCCESSFUL_REGISTRATION_CONTENT":
                if (params.size() == 4) {
                    result = new String(SUCCESSFUL_REGISTRATION_CONTENT);
                    result = result.replaceAll("<Client Name>", params.get(0));
                    result = result.replaceAll("<URL>", params.get(1));
                    result = result.replaceAll("<client email address>", params.get(2));
                    result = result.replaceAll("<system assigned password>", params.get(3));
                }
                break;
            case "SUCCESSFUL_CAPACITY_ASSESSMENT_UPLOAD":
                if (params.size() == 4) {
                    result = new String(SUCCESSFUL_CAPACITY_ASSESSMENT_UPLOAD);
                    result = result.replaceAll("<Client Name>", params.get(0));
                    result = result.replaceAll("<Capacity Assessor>", params.get(1));
                    result = result.replaceAll("<PoA Client Name>", params.get(2));
                    result = result.replaceAll("<File ID>", params.get(3));
                }
                break;
            case "SUCCESSFUL_POA_REVOCATION":
                if (params.size() == 3) {
                    result = new String(SUCCESSFUL_POA_REVOCATION);
                    result = result.replaceAll("<Client Name>", params.get(0));
                    result = result.replaceAll("<PoA Client Name>", params.get(1));
                    result = result.replaceAll("<File ID>", params.get(2));
                }
                break;
            case "SDM_INVITATION_REQUEST":
                if (params.size() == 2) {
                    result = new String(SDM_INVITATION_REQUEST);
                    result = result.replaceAll("<Client Name>", params.get(0));
                    result = result.replaceAll("<URL>", params.get(1));
                }
                break;
            case "FILE_SHARE_REQUEST":
                if (params.size() == 2) {
                    result = new String(FILE_SHARE_REQUEST);
                    result = result.replaceAll("<Client Name>", params.get(0));
                    result = result.replaceAll("<URL>", params.get(1));
                }
                break;
            case "ASSESSOR_INVITATION_REQUEST":
                if (params.size() == 2) {
                    result = new String(ASSESSOR_INVITATION_REQUEST);
                    result = result.replaceAll("<Client Name>", params.get(0));
                    result = result.replaceAll("<URL>", params.get(1));
                }
                break;
        }

        return result;
    }

}
