package com.poa.server.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import com.poa.server.entity.User;
import com.poa.server.exception.PoaException;
import com.poa.server.util.MailContentUtil;
import com.poa.server.util.UserUtil;
import org.apache.commons.lang3.StringUtils;


import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class AzureAPI {

    @Autowired
    private RestTemplate restTemplate;


    private String API_PREFIX = "https://graph.microsoft.com/v1.0/";

    @Value("${dpoa.b2c-info.domain}")
    private String B2C_DOMAIN;


/*    @Autowired
    private PoaMailService mailService;*/

    @Value("${dpoa.ad-api-info.api-client-id}")
    private String API_CLIENT_ID;

    @Value("${dpoa.ad-api-info.api-client-secret}")
    private String API_CLIENT_SECRET;



    public HttpEntity getHttpEntity(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return new HttpEntity<>(null, headers);
    }



    /**
     * get user info
     *
     * @param token azure auth token
     */
    public JSONObject usersMe(String token) throws IOException {
        String url = API_PREFIX + "me?$select=id,givenName,surname,displayName&$expand=extensions";



        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ilg1ZVhrNHh5b2pORnVtMWtsMll0djhkbE5QNC1jNTdkTzZRR1RWQndhTmsifQ.eyJpc3MiOiJodHRwczovL2N1cmFkZXZ0ZXN0LmIyY2xvZ2luLmNvbS9kYTZkYWE0Ny04NzAwLTQ5MDgtYTVhMC05OTBiY2NiNjQ5NTMvdjIuMC8iLCJleHAiOjE2NTAyMDM2MTAsIm5iZiI6MTY1MDIwMjQxMCwiYXVkIjoiOTY2MGFlODEtMTM5Ni00MjBhLWJmYzQtZTYzZDhhYjZlM2U3Iiwib2lkIjoiNzk3ZWJlZWQtODU3NC00NDRjLWJlNzMtZmEwNjhlYTY3ZGEzIiwic3ViIjoiNzk3ZWJlZWQtODU3NC00NDRjLWJlNzMtZmEwNjhlYTY3ZGEzIiwiZ2l2ZW5fbmFtZSI6IjQiLCJmYW1pbHlfbmFtZSI6IjIiLCJuYW1lIjoiNCAyIiwiZW1haWxzIjpbIjEzMTgwMDAzOTBAcXEuY29tIl0sInRmcCI6IkIyQ18xX1NpZ251cF9TaWduaW4iLCJub25jZSI6IjMxM2E2ZmE1LTk5OTQtNDhjMi05NjgzLWM1MTI1N2M4NzI3MSIsInNjcCI6InUucmVhZCIsImF6cCI6Ijk2NjBhZTgxLTEzOTYtNDIwYS1iZmM0LWU2M2Q4YWI2ZTNlNyIsInZlciI6IjEuMCIsImlhdCI6MTY1MDIwMjQxMH0.cBqovLbXwdnNtx7Tb0b3odsw-_b-6ba7eUYsL3tBgpRYP3SI3nB5iJx4IWbef26xcDRSQnLFVIDJKFh1dasNuH2kKk70ihuupcgx-xHtMvQxJjwe4TmHYQmO9o2s0ry_f0bgV3O5TaDRNBP_Cv-J-Ro3R3M6thefBLfljWUbD22o13zhbPsx8hE3GpR_6kN50H0mWlDtGPdDNBW2KKa_nDnTVkGM56iKztAzELDGPwlpvacMYoyo_iIG4xZKK9_XKIL_uIyA4pH3IXwna3f1e05aKTUlpqTb6hKaknl-nSVTgPns-7TolTHAmBlc3qYQ3yZPGi9SERb9PpEgRcmhTw";
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(token), String.class);

        return JSONUtil.parseObj(exchange.getBody());
    }

    /**
     * get user info by user id
     *
     * @param id    azure ad user object id
     * @param token azure auth token
     */
    public JSONObject usersById(String id, String token) throws IOException {
        String url = API_PREFIX + "users/" + id + "?$select=id,givenName,surname,displayName,country," +
                "state,streetAddress,city,otherMails,postalCode,userPrincipalName,email," +
                "extension_01106f319c374ff4bca6a029170e4e9a_role,extension_01106f319c374ff4bca6a029170e4e9a_sex," +
                "extension_01106f319c374ff4bca6a029170e4e9a_phoneNumber,extension_01106f319c374ff4bca6a029170e4e9a_createdBy," +
                "extension_01106f319c374ff4bca6a029170e4e9a_countryCode,extension_01106f319c374ff4bca6a029170e4e9a_address2," +
                "identities,createdDateTime";

        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(token), String.class);

        return JSONUtil.parseObj(exchange.getBody());
    }

    /**
     * get user Group
     *
     * @param token azure auth token
     */
    public JSONObject usersMyGroup(String token) throws IOException {
        String url = API_PREFIX + "me/memberOf";
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(token), String.class);

        return JSONUtil.parseObj(exchange.getBody());
    }

    public String getToken() {
        String url = "https://login.microsoftonline.com/da6daa47-8700-4908-a5a0-990bccb64953/oauth2/v2.0/token";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        LinkedMultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("client_id", API_CLIENT_ID);
        paramMap.add("scope", "https://graph.microsoft.com/.default");
        paramMap.add("client_secret", API_CLIENT_SECRET);
        paramMap.add("grant_type", "client_credentials");
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, headers);

        ResponseEntity<Map> exchange = restTemplate.postForEntity(url, httpEntity, Map.class);

        try {
            return exchange.getBody().get("access_token").toString();
        } catch (Exception e) {
            throw new PoaException("get azure api token error!");
        }
    }
    /**
     * generate active code
     *
     * @param count
     * @return
     * @throws Exception
     */
    public static String randomNumberString(int count) {
        StringBuilder builder = new StringBuilder();
        String s = "1234567890";
        char[] c = s.toCharArray();
        Random random = new Random();
        for( int i = 0; i < count; i ++) {
            builder.append(c[random.nextInt(c.length)]);
        }

        return builder.toString();
    }
    public static String randomLowerString(int count) {
        StringBuilder builder = new StringBuilder();
        String s = "abcdefghijklmnopqrstuvwxyz";
        char[] c = s.toCharArray();
        Random random = new Random();
        for( int i = 0; i < count; i ++) {
            builder.append(c[random.nextInt(c.length)]);
        }

        return builder.toString();
    }
    /**
     * generate active code
     *
     * @param count
     * @return
     * @throws Exception
     */
    public static String randomUpperString(int count) {
        StringBuilder builder = new StringBuilder();
        String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] c = s.toCharArray();
        Random random = new Random();
        for( int i = 0; i < count; i ++) {
            builder.append(c[random.nextInt(c.length)]);
        }

        return builder.toString();
    }
    /**
     * create user
     *
     * @param user user information
     * @return user
     */
    public JSONObject createUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + getToken());


        JSONObject info = new JSONObject();
        // basic info
        info.set("givenName", user.getFirstName());
        info.set("surname", user.getLastName());
        info.set("displayName", user.getFirstName() + " " + user.getLastName());
        /*if (StringUtils.isNotBlank(userInfo.getProvinceOrTerritory())) {
            info.set("state", userInfo.getProvinceOrTerritory());
        }
        if (StringUtils.isNotBlank(userInfo.getCity())) {
            info.set("city", userInfo.getCity());
        }

        if (StringUtils.isNotBlank(userInfo.getAddress1())) {
            info.set("streetAddress", userInfo.getAddress1());
        }
        if (StringUtils.isNotBlank(userInfo.getPostalCode())) {
            info.set("postalCode", userInfo.getPostalCode());
        }
        if (StringUtils.isNotBlank(userInfo.getAddress2())) {
            info.set("extension_01106f319c374ff4bca6a029170e4e9a_address2", userInfo.getAddress2());
        }
        if (StringUtils.isNotBlank(userInfo.getCountryCode())) {
            info.set("extension_01106f319c374ff4bca6a029170e4e9a_countryCode", userInfo.getCountryCode());
        }
        if (StringUtils.isNotBlank(userInfo.getPhoneNumber())) {
            info.set("extension_01106f319c374ff4bca6a029170e4e9a_phoneNumber", userInfo.getPhoneNumber());
        }
        if (StringUtils.isNotBlank(userInfo.getRole())) {
            info.set("extension_01106f319c374ff4bca6a029170e4e9a_role", userInfo.getRole());
            if ("user".equalsIgnoreCase(userInfo.getRole())) {
                info.set("extension_01106f319c374ff4bca6a029170e4e9a_createdBy", UserUtil.getUserId());
            }
        }*/
        /*info.set("state", userInfo.getProvinceOrTerritory());
        info.set("city", userInfo.getCity());
        info.set("streetAddress", userInfo.getAddress1());
        info.set("postalCode", userInfo.getPostalCode());
        info.set("extension_01106f319c374ff4bca6a029170e4e9a_address2", userInfo.getAddress2());
        info.set("extension_01106f319c374ff4bca6a029170e4e9a_countryCode", userInfo.getCountryCode());
        info.set("extension_01106f319c374ff4bca6a029170e4e9a_phoneNumber", userInfo.getPhoneNumber());
        info.set("extension_01106f319c374ff4bca6a029170e4e9a_createdBy", UserUtil.getUserId());*/


        info.set("passwordPolicies", "DisablePasswordExpiration");

        // identities
        JSONArray identities = new JSONArray();
        JSONObject identity = new JSONObject();
        identity.set("signInType", "emailAddress");
        identity.set("issuer", B2C_DOMAIN);
        identity.set("issuerAssignedId", user.getEmail());
        identities.set(identity);
        info.set("identities", identities);

        // password profile
        JSONObject passwordProfile = new JSONObject();
        String password1 = randomUpperString(4);
        String password2 = randomLowerString(4);
        String password3 = randomNumberString(4);
        String password = password1 + password2 + password3;

        System.out.println("密码===" + password1 + password2 + password3);

        passwordProfile.set("password", password1 + password2 + password3);
        passwordProfile.set("forceChangePasswordNextSignIn", false);
        info.set("passwordProfile", passwordProfile);

        // create
        try {
            String url = API_PREFIX + "users";
            HttpEntity httpEntity = new HttpEntity<>(info, headers);
            ResponseEntity<JSONObject> exchange = restTemplate.postForEntity(url, httpEntity, JSONObject.class);

            JSONObject userObj = exchange.getBody();
            if (userObj.containsKey("id")) {
                // send email to email
/*                String content = MailContentUtil.successfulRegistration(userInfo.getLastName() + " " + userInfo.getFirstName(),
                        "<a>https://testdevad.canadapoaregistry.com/auth/login<a>", userInfo.getEmail(), password);
                mailService.sendSimpleEmail(userInfo.getEmail(), "Successful Registration", content);*/

                userObj.putOnce("isNew", true);
                return userObj;
            }
        } catch (Exception e) {
            throw e;
        }

        return null;
    }

    /**
     * update user information
     */
    public void updateUser(UserInfoBO userInfo) {
        if (StringUtils.isNotBlank(userInfo.getId())) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("Authorization", "Bearer " + getToken());

            JSONObject info = new JSONObject();
            // basic info
            if (StringUtils.isNotBlank(userInfo.getFirstName())) {
                info.set("givenName", userInfo.getFirstName());
            }
            if (StringUtils.isNotBlank(userInfo.getLastName())) {
                info.set("surname", userInfo.getLastName());
            }
            if (StringUtils.isNotBlank(userInfo.getProvinceOrTerritory())) {
                info.set("state", userInfo.getProvinceOrTerritory());
            }
            if (StringUtils.isNotBlank(userInfo.getCity())) {
                info.set("city", userInfo.getCity());
            }
            if (StringUtils.isNotBlank(userInfo.getAddress1())) {
                info.set("streetAddress", userInfo.getAddress1());
            }
            if (StringUtils.isNotBlank(userInfo.getPostalCode())) {
                info.set("postalCode", userInfo.getPostalCode());
            }
            if (StringUtils.isNotBlank(userInfo.getAddress2())) {
                info.set("extension_01106f319c374ff4bca6a029170e4e9a_address2", userInfo.getAddress2());
            }
            if (StringUtils.isNotBlank(userInfo.getCountryCode())) {
                info.set("extension_01106f319c374ff4bca6a029170e4e9a_countryCode", userInfo.getCountryCode());
            }
            if (StringUtils.isNotBlank(userInfo.getPhoneNumber())) {
                info.set("extension_01106f319c374ff4bca6a029170e4e9a_phoneNumber", userInfo.getPhoneNumber());
            }
            if (StringUtils.isNotBlank(userInfo.getRole())) {
                info.set("extension_01106f319c374ff4bca6a029170e4e9a_role", userInfo.getRole());
            }
            if (StringUtils.isNotBlank(userInfo.getState())) {
                info.set("state", userInfo.getState());
            }

            try {
                String url = API_PREFIX + "users/" + userInfo.getId();
                HttpEntity httpEntity = new HttpEntity<>(null, headers);
                ResponseEntity<JSONObject> exchange = restTemplate.postForEntity(url, httpEntity, JSONObject.class);

                JSONObject body = exchange.getBody();
                if (body.containsKey("error") && body.getJSONObject("error").containsKey("message")) {
                    throw new PoaException(500, body.getJSONObject("error").getStr("message"));
                }
                throw new PoaException(500, "call azure api error!");
            } catch (Exception e) {
                throw new PoaException(500, e.getMessage());
            }

        } else {
            throw new PoaException(400, "user id can not be empty!");
        }
    }


}
