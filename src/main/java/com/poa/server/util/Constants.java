package com.poa.server.util;


import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * application constants
 */
public final class Constants {

    public static class FileType{
        public static final int Profile = 1;
        public static final int Document = 2;
    }

    public static class ProfileStatus{
        public static final String Open = "Open";

    }

    public static class AoeStatus{
        public static final String Separate = "separateFile";

    }

    public static class PermissionType{
        public static final int Registry = 1;
        public static final int Files = 2;
    }






/*




    public static final ConcurrentHashMap<String, Integer> contactUsLimitingIpMap = new ConcurrentHashMap<>();

    */
/**
     * notary relation type
     *//*

    public static final String NOTARY_RELATION_AFFILIATED_THIRD_PARTY = "AffiliatedThirdParty";
    public static final String NOTARY_RELATION_WITNESS = "Witness";

    */
/**
     * poa relation type
     *//*

    public static final String POA_RELATION_SDM = "SDM";
    public static final String POA_RELATION_WITNESS = "Witness";

    */
/**
     * audit log action type
     *//*

    public static final String AUDIT_LOG_ACTION_UPLOAD = "Upload";
    public static final String AUDIT_LOG_ACTION_SAVE_DRAFT = "Save Draft";
    public static final String AUDIT_LOG_ACTION_VIEW = "View";
    public static final String AUDIT_LOG_ACTION_SHARE = "Share";
    public static final String AUDIT_LOG_ACTION_ACTIVATED = "Activated";
    public static final String AUDIT_LOG_ACTION_REVOKED = "Revoked";

    */
/**
     * audit log type
     *//*

    public static final String AUDIT_LOG_POA = "PoA";
    public static final String AUDIT_LOG_NOTARY = "Notary";

    */
/**
     * poa upload or compose
     *//*

    public static final String POA_UPLOAD = "upload";
    public static final String POA_COMPOSE = "compose";

    */
/**
     * permanent time
     *//*

    public static final Date PERMANENT_TIME = new Date(32472115200000L);



    */
/**
     * poa status
     *//*

    public enum PoaStatusEnum {
        ACTIVATED("Activated"),
        REVOKED("Revoked"),
        READY("Ready"),
        DRAFT("Draft"),
        ;

        private String code;
        PoaStatusEnum(String code) {
            this.code = code;
        }
        public String getCode() {
            return this.code;
        }
    }

    */
/**
     * poa type
     *//*

    public enum PoaTypeEnum {
        PERSONAL_HEALTH("Personal Health"),
        FINANCE_PROPERTY("Finance Property"),
        ;

        private String code;
        PoaTypeEnum(String code) {
            this.code = code;
        }
        public String getCode() {
            return this.code;
        }
    }

    */
/**
     * notary status
     *//*

    public enum NotaryStatusEnum {
        VERIFIED("Verified"),
        PENDING_VERIFIED("PendingVerified"),
        DRAFT("draft"),
        ;

        private String code;
        NotaryStatusEnum(String code) {
            this.code = code;
        }
        public String getCode() {
            return this.code;
        }
    }

    */
/**
     * user from source
     *//*

    public enum UserSourceEnum {
        GOOGLE("Google"),
        FACEBOOK("Facebook"),
        AZURE_AD("AzureAD"),
        LOCAL_SYSTEM("LocalSystem"),
        ;

        private String code;
        UserSourceEnum(String code) {
            this.code = code;
        }
        public String getCode() {
            return this.code;
        }
    }

    */
/**
     * user status
     *//*

    public enum UserStatusEnum {
        TO_BE_ACTIVATED("ToBeActivated"),
        ACTIVATED("Activated"),
        DISABLE("Disable"),
        ;

        private String code;
        UserStatusEnum(String code) {
            this.code = code;
        }
        public String getCode() {
            return this.code;
        }
    }

    */
/**
     * entity type
     *//*

    public enum EntityTypeEnum {
        POA("PoA"),
        NOTARY("Notary"),
        AFFIDAVIT("Affidavit")
        ;

        private String code;
        EntityTypeEnum(String code) {
            this.code = code;
        }
        public String getCode() {
            return this.code;
        }
    }

    */
/**
     * requests type
     *//*

    public enum RequestsTypeEnum {
        POA_ACCESS("PoA_Access"),
        ASSESSOR_REQUEST("Assessor_Request"),
        SDM_REQUEST("SDM_Request"),
        SHARE("Share"),
        ;

        private String code;
        RequestsTypeEnum(String code) {
            this.code = code;
        }
        public String getCode() {
            return this.code;
        }
    }

    */
/**
     * requests status
     *//*

    public enum RequestsStatusEnum {
        PENDING("Pending"),
        ACCEPTED("Accepted"),
        DECLINED("Declined"),
        COMPLETED("Completed")
        ;

        private String code;
        RequestsStatusEnum(String code) {
            this.code = code;
        }
        public String getCode() {
            return this.code;
        }
    }

    */
/**
     * share recipient type
     *//*

    public enum ShareRecipientTypeEnum {
        SDM("SDM"),
        LAWYER("Lawyer"),
        DOCTOR("Doctor"),
        BANK("Bank"),
        THIRD_PARTY("Third_Party"),
        ;

        private String code;
        ShareRecipientTypeEnum(String code) {
            this.code = code;
        }
        public String getCode() {
            return this.code;
        }
    }

    */
/**
     * template type
     *//*

    public enum TemplateTypeEnum {
        COMPOSE("compose"),
        AFFIDAVIT("affidavit"),
        ;

        private String code;
        TemplateTypeEnum(String code) {
            this.code = code;
        }
        public String getCode() {
            return this.code;
        }
    }

    */
/**
     * notice state
     *//*

    public enum NoticeStateEnum {
        UNREAD("Unread"),
        READED("Readed")
        ;

        private String code;
        NoticeStateEnum(String code) {
            this.code = code;
        }
        public String getCode() {
            return this.code;
        }
    }

    */
/**
     * notice action type
     *//*

    public static final String OPERATOR_REGEX = "\\$\\{operatorId\\}";
    public static final String RECEIVER_REGEX = "\\$\\{receiverId\\}";
    public enum NoticeActionEnum {
        REQUEST_CAPACITY_ASSESSMENT("REQUEST_CAPACITY_ASSESSMENT", "${operatorId} invites you to be an ability assessor"),
        REQUEST_SDM("REQUEST_SDM", "${operatorId} invites you to be an substitute decision maker"),
        UPLOAD_POA("UPLOAD_POA", "${operatorId} uploaded PoA to the registry"),
        REVOKE_POA("REVOKE_POA", "${operatorId} revoked PoA"),
        ACCEPT_SDM("ACCEPT_SDM", "${operatorId} agree to become substitute decision maker"),
        DECLINE_SDM("DECLINE_SDM", "${operatorId} refuse to become substitute decision maker"),
        ACCESS_POA_DECLINE("ACCESS_POA_DECLINE", "${operatorId} rejected your POA access request"),
        ACCESS_POA_ACCEPT("ACCESS_POA_DECLINE", "${operatorId} agreed to your POA access request"),
        ACCEPT_ASSESSOR("ACCEPT_ASSESSOR", "${operatorId} agree to become ability assessor"),
        DECLINE_ASSESSOR("DECLINE_ASSESSOR", "${operatorId} refuse to become ability assessor"),
        UPLOAD_ASSESSMENT("UPLOAD_ASSESSMENT", "${operatorId} uploaded the ability assessment document"),
        REVOKE_ADDITIONAL_CONSENT("REVOKE_ADDITIONAL_CONSENT", "${operatorId} revoked additional consent"),
        REVOKE_SDM_CONSENT("REVOKE_SDM_CONSENT", "${operatorId} revoked SDM consent"),
        ;

        private String code;
        private String message;

        NoticeActionEnum(String code) {
            this.code = code;
            this.message = "";
        }
        NoticeActionEnum(String code, String message) {
            this.code = code;
            this.message = message;
        }
        public String getCode() {
            return this.code;
        }
        public String getMessage() {
            return this.message;
        }
    }

    */
/**
     * notice action type
     *//*

    public enum NoticeActionTypeEnum {
        SDM("SDM"),
        ASSESSOR("Assessor"),
        CONSENT("Consent"),
        ;

        private String code;
        NoticeActionTypeEnum(String code) {
            this.code = code;
        }
        public String getCode() {
            return this.code;
        }
    }

    */
/**
     * authority expire type
     *//*

    public enum AuthorityExpireTypeEnum {
        PERMANENT("Permanent"),
        TIME_LIMIT("Time_Limit")
        ;

        private String code;
        AuthorityExpireTypeEnum(String code) {
            this.code = code;
        }
        public String getCode() {
            return this.code;
        }
    }

    */
/**
     * poa authority authority type
     *//*

    public enum AuthorityTypeEnum {
        ALL("All", 1),
        ACCESS("Access", 2),
        ACCESS_SHARE("AccessAndShare", 3),
        NONE("None", 4)
        ;

        private String code;
        private int orderNumber;
        AuthorityTypeEnum(String code, int orderNumber) {
            this.code = code;
            this.orderNumber = orderNumber;
        }
        public String getCode() {
            return this.code;
        }
        public int getOrderNumber() {
            return orderNumber;
        }
    }

    */
/**
     * assessment status
     *//*

    public enum AssessmentsStatusEnum {
        NEW("New"),
        ACCEPT("Accept"),
        DECLINE("Decline"),
        COMPLETED("Completed")
        ;

        private String code;
        AssessmentsStatusEnum(String code) {
            this.code = code;
        }
        public String getCode() {
            return this.code;
        }
    }

    */
/**
     * user consent flag
     *//*

    public enum UserConsentFlagEnum {
        PENDING("Pending"),
        ACCEPT("Accept"),
        DECLINE("Decline")
        ;

        private String code;
        UserConsentFlagEnum(String code) {
            this.code = code;
        }
        public String getCode() {
            return this.code;
        }

    }
*/

}
