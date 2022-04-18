package com.poa.server.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.poa.server.exception.PoaException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class UserUtil {

    /*public static UserDetailsImpl getCurrentUser() {
        try {
            return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new DpoaException(Constants.CommonExceptionObj.UNAUTHORIZED.getObj());
        }
    }

    public static UserEntity userToUserEntity(User user, UserEntity userEntity) {
        BeanUtil.copyProperties(user, userEntity);
        userEntity.setLastName(user.getGivenName());
        userEntity.setFirstName(user.getSurname());
        userEntity.setEmail(user.getEmail());
        userEntity.setUsername(user.getEmail());
        userEntity.setPhoneNumber(user.getMobilePhone());
        userEntity.setCity(user.getCity());
        userEntity.setPostalCode(user.getPostalCode());
        return userEntity;
    }*/

    /**
     * get login user id
     *
     * @return user id
     */
    public static String getUserId() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getHeader("User-ID");
    }

    /**
     * get login user id
     *
     * @return user id
     */
    public static String getUserRole() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getHeader("User-Role");
    }

    /**
     * get login user email
     *
     * @return user email
     */
    public static String getUserEmail() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getHeader("User-Email");
    }

    /**
     * get user email list
     *
     * @param userInfo info by azure obj
     * @return email address list
     */
    public static List<String> getUserEmail(JSONObject userInfo) {
        List<String> mailList = new ArrayList<>();
        if (userInfo.containsKey("otherMails") && !userInfo.getJSONArray("otherMails").isEmpty()) {
            JSONArray mails = userInfo.getJSONArray("otherMails");
            mailList.addAll(mails.toList(String.class));
        }
        if (userInfo.containsKey("identities")
                && userInfo.getJSONArray("identities") != null && !userInfo.getJSONArray("identities").isEmpty()) {
            JSONArray identities = userInfo.getJSONArray("identities");
            for (Object identity : identities) {
                JSONObject obj1 = (JSONObject) identity;
                if (obj1.getStr("signInType").equals("emailAddress")) {
                    mailList.add(obj1.getStr("issuerAssignedId"));
                }
            }
        }

        return mailList;
    }

    public static String getPhone(JSONObject user) {
        if (user == null || StringUtils.isBlank(user.getStr("id"))) {
            throw new PoaException(400, "this account does not exists!");
        }
        String phoneNumber = user.getStr("extension_01106f319c374ff4bca6a029170e4e9a_phoneNumber");
        String countryCode = user.getStr("extension_01106f319c374ff4bca6a029170e4e9a_countryCode");
        return countryCode + phoneNumber;
    }

    public static String getName(JSONObject user) {
        if (user == null || StringUtils.isBlank(user.getStr("id"))) {
            throw new PoaException(400, "this account does not exists!");
        }
        return user.getStr("givenName") + " " + user.getStr("surname");
    }
}
