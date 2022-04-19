package com.poa.server.util;

import cn.hutool.core.net.URLEncoder;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.poa.server.exception.PoaException;
import com.poa.server.security.UserInfoBO;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AzureAPI {

    private static CloseableHttpClient httpclient = HttpClients.createDefault();

//    @Value("${dpoa.azure-api-prefix}")
    private static String API_PREFIX = "https://graph.microsoft.com/v1.0/";

    @Value("${dpoa.b2c-info.domain}")
    private String B2C_DOMAIN;


    @Value("${dpoa.ad-api-info.api-client-id}")
    private String API_CLIENT_ID;

    @Value("${dpoa.ad-api-info.api-client-secret}")
    private String API_CLIENT_SECRET;

    //使用注解注入
    @Autowired
    private RestTemplate restTemplate;


    public  static void test1(){
        HttpPost httpPost = new HttpPost("https://login.microsoftonline.com/da6daa47-8700-4908-a5a0-990bccb64953/oauth2/v2.0/token");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> params = new ArrayList<>();
        NameValuePair grant_type = new BasicNameValuePair("grant_type", "client_credentials");
        NameValuePair scope = new BasicNameValuePair("scope", "https://graph.microsoft.com/.default");

        NameValuePair client_id = new BasicNameValuePair("client_id", "rzc7Q~stFnqLkepyLX3-Pe9uK30e7p2JnsS.1");
        NameValuePair client_secret = new BasicNameValuePair("client_secret", "");


        params.add(client_id);
        params.add(scope);
        params.add(client_secret);
        params.add(grant_type);

        JSONObject object = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse execute = httpclient.execute(httpPost);
            HttpEntity entity = execute.getEntity();
            object = JSONUtil.parseObj(EntityUtils.toString(entity));
        } catch (IOException e) {
            throw new PoaException(400, "get azure api token error!");
        }

        System.out.println("申请Token成功===");
        System.out.println(object.toString());

        String access_token = object.getStr("access_token");
        //String access_token = object.getStr("access_token");


        httpPost = new HttpPost(API_PREFIX + "users");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Bearer " + access_token);

        JSONObject info = new JSONObject();
        // basic info
        info.set("surname", "surname");
        info.set("givenName", "givenName");
        info.set("displayName", "givenName_surname");
        info.set("passwordPolicies", "DisablePasswordExpiration");

        // identities
        JSONArray identities = new JSONArray();
        JSONObject identity = new JSONObject();
        identity.set("signInType", "emailAddress");
        identity.set("issuer", "curadevtest.onmicrosoft.com");//TODO
        identity.set("issuerAssignedId", "1318000311qq.com");//TODO
        identities.set(identity);
        info.set("identities", identities);

        // password profile
        JSONObject passwordProfile = new JSONObject();
        String password1 = StringComUtils.randomUpperString(4);
        String password2 = StringComUtils.randomLowerString(4);
        String password3 = StringComUtils.randomNumberString(4);
        String password = password1 + password2 + password3;
        passwordProfile.set("password", password1 + password2 + password3);
        passwordProfile.set("forceChangePasswordNextSignIn", false);
        info.set("passwordProfile", passwordProfile);

        System.out.println("创建用户密码 === " +  password1 + password2 + password3);
        // create
        try {
            httpPost.setEntity(new StringEntity(info.toString()));
            CloseableHttpResponse execute = httpclient.execute(httpPost);
            HttpEntity entity = execute.getEntity();
            JSONObject user = JSONUtil.parseObj(EntityUtils.toString(entity));
            if (user.containsKey("id")) {

                System.out.println("创建用户成功===");
                System.out.println(user.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public  static void test2() throws IOException {
        String token = new AzureAPI().getToken();

        HttpGet httpGet = new HttpGet("https://graph.microsoft.com/v1.0/me");
        httpGet.setHeader("Authorization", "Bearer  " + token);
        httpGet.setHeader("Accept", "application/json");

        CloseableHttpResponse execute = httpclient.execute(httpGet);

        HttpEntity entity = execute.getEntity();

        System.out.println(JSONUtil.parseObj(EntityUtils.toString(entity)));





    }



    public static void main(String[] args) throws Exception {
        // validateToken();


        String token = new AzureAPI().getToken();


        System.out.println("Bearer " + token);

    }

    public static void validateToken() throws Exception {
        String token = new AzureAPI().getToken();

        DecodedJWT jwt = JWT.decode(token);


        System.out.println("JWT Key ID is : " + jwt.getKeyId());


        URL keysURL = new URL("https://login.microsoftonline.com/common/discovery/v2.0/keys");
        JwkProvider provider = new UrlJwkProvider(keysURL);
        Jwk jwk = provider.get(jwt.getKeyId());

      /*  System.out.println("解析Token数据");
        System.out.println(Jwts.parser().setSigningKey(jwk.getPublicKey()).parseClaimsJws(token).getBody());*/


        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
        algorithm.verify(jwt);


        System.out.println("校验成功===========");
    }
    /**
     * get user info
     *
     * @param token azure auth token
     */
    public JSONObject usersMe(String token) throws IOException {
        HttpGet httpGet = new HttpGet(API_PREFIX + "me?$select=id,givenName,surname,userType&$expand=extensions");
        httpGet.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse execute = httpclient.execute(httpGet);

        HttpEntity entity = execute.getEntity();

        return JSONUtil.parseObj(EntityUtils.toString(entity));
    }

    /**
     * get user info by user id
     *
     * @param id    azure ad user object id
     * @param token azure auth token
     */
    public JSONObject usersById(String id, String token) throws IOException {
        HttpGet httpGet = new HttpGet(API_PREFIX + "users/" + id + "?$select=id,givenName,surname,displayName,country," +
                "state,streetAddress,city,otherMails,postalCode,userPrincipalName,email," +
                "extension_01106f319c374ff4bca6a029170e4e9a_role,extension_01106f319c374ff4bca6a029170e4e9a_sex," +
                "extension_01106f319c374ff4bca6a029170e4e9a_phoneNumber,extension_01106f319c374ff4bca6a029170e4e9a_createdBy," +
                "extension_01106f319c374ff4bca6a029170e4e9a_countryCode,extension_01106f319c374ff4bca6a029170e4e9a_address2," +
                "identities,createdDateTime");
        httpGet.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse execute = httpclient.execute(httpGet);

        HttpEntity entity = execute.getEntity();

        return JSONUtil.parseObj(EntityUtils.toString(entity));
    }

    /**
     * get user Group
     *
     * @param token azure auth token
     */
    public JSONObject usersMyGroup(String token) throws IOException {
        HttpGet httpGet = new HttpGet(API_PREFIX + "me/memberOf");
        httpGet.setHeader("Authorization", "Bearer " + token);

        CloseableHttpResponse execute = httpclient.execute(httpGet);

        HttpEntity entity = execute.getEntity();

        return JSONUtil.parseObj(EntityUtils.toString(entity));
    }

    public String getToken() {
//        BoundValueOperations<String, String> valueOps = redisTemplate.boundValueOps("api-token");
//
//        if (valueOps.get() != null) {
//            return valueOps.get();
//        }

//        HttpPost httpPost = new HttpPost("https://login.microsoftonline.com/46d1d867-ecad-4286-86af-5af3e0794a93/oauth2/v2.0/token");
        HttpPost httpPost = new HttpPost("https://login.microsoftonline.com/46d1d867-ecad-4286-86af-5af3e0794a93/oauth2/v2.0/token");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> params = new ArrayList<>();
//        NameValuePair client_id = new BasicNameValuePair("client_id", "e131e114-6fc6-4fc3-99e0-ae86a473f1b4");
//        NameValuePair scope = new BasicNameValuePair("scope", "https://graph.microsoft.com/.default");
//        NameValuePair client_secret = new BasicNameValuePair("client_secret", "-6pXkZA6.3MWkbv8k5Ub_h~s8O7-emBhWK");
        NameValuePair client_id = new BasicNameValuePair("client_id", "9fe10508-405d-4617-8dee-52bca87e202f");
        NameValuePair scope = new BasicNameValuePair("scope", "https://poadevadb2c.onmicrosoft.com/api1/.default");
        NameValuePair client_secret = new BasicNameValuePair("client_secret", "NKu8Q~cMK5xOE6C5LVRLe5R8YEy7qT8Lp95a-c2f");
        NameValuePair grant_type = new BasicNameValuePair("grant_type", "client_credentials");
        params.add(client_id);
        params.add(scope);
        params.add(client_secret);
        params.add(grant_type);

        JSONObject object = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse execute = httpclient.execute(httpPost);
            HttpEntity entity = execute.getEntity();
            object = JSONUtil.parseObj(EntityUtils.toString(entity));
        } catch (Exception e) {

            System.out.println(e);

        }

        System.out.println("请求Token成功=======" + object.getStr("access_token"));


//        if (object.containsKey("access_token")) {
//            // save redis
//            valueOps.expire(20, TimeUnit.MINUTES);
//            valueOps.set(object.getStr("access_token"));

            return object.getStr("access_token");
//        }

//        throw new DpoaException(ExceptionObj.of(HttpStatus.BAD_REQUEST, "get azure api token error!"));
    }

    /**
     * find user list
     *
     * @return user list
     */
    public JSONArray userList(Map<String, String> param) {
        String url = API_PREFIX + "users?$select=id,givenName,surname,displayName,country,state," +
                "streetAddress,city,otherMails,postalCode,userPrincipalName,email,extension_01106f319c374ff4bca6a029170e4e9a_role," +
                "extension_01106f319c374ff4bca6a029170e4e9a_sex,extension_01106f319c374ff4bca6a029170e4e9a_phoneNumber," +
                "extension_01106f319c374ff4bca6a029170e4e9a_address2,extension_01106f319c374ff4bca6a029170e4e9a_createdBy," +
                "extension_01106f319c374ff4bca6a029170e4e9a_countryCode,identities,createdDateTime";

        // filter
        StringBuilder filter = new StringBuilder("&$filter=");
        if (StringUtils.isNotBlank(param.get("role"))) {
            filter.append("startswith(extension_01106f319c374ff4bca6a029170e4e9a_role,'" + param.get("role") + "') ");
        }

        if (StringUtils.isNotBlank(param.get("lastName"))) {
            filter.append("and (startswith(givenName, '" + param.get("lastName") + "')");
            filter.append("or startswith(surname, '" + param.get("lastName") + "'))");
        }

        if (StringUtils.isNotBlank(param.get("firstName"))) {
            filter.append("and (startswith(givenName, '" + param.get("firstName") + "')");
            filter.append("or startswith(surname, '" + param.get("firstName") + "'))");
        }

        if (StringUtils.isNotBlank(param.get("phoneNumber"))) {
            filter.append("and startswith(extension_01106f319c374ff4bca6a029170e4e9a_phoneNumber, '" + param.get("phoneNumber") + "')");
        }

        // TODO other param


        url = url + URLEncoder.DEFAULT.encode(filter.toString(), StandardCharsets.UTF_8);

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Authorization", "Bearer " + getToken());

        try {
            CloseableHttpResponse execute = httpclient.execute(httpGet);
            HttpEntity entity = execute.getEntity();
            JSONObject object = JSONUtil.parseObj(EntityUtils.toString(entity));
            if (object.containsKey("value") && !object.getJSONArray("value").isEmpty()) {
                return object.getJSONArray("value");
            }

            return new JSONArray();
        } catch (IOException e) {
            return new JSONArray();
        }
    }

    /**
     * create user
     *
     * @param userInfo user information
     * @return user
     */
    public JSONObject createUser(UserInfoBO userInfo) {
        HttpPost httpPost = new HttpPost(API_PREFIX + "users");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Bearer " + getToken());

        JSONObject info = new JSONObject();
        // basic info
        info.set("givenName", userInfo.getFirstName());
        info.set("surname", userInfo.getLastName());
        info.set("displayName", userInfo.getFirstName() + " " + userInfo.getLastName());
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
        info.set("passwordPolicies", "DisablePasswordExpiration");

        // identities
        JSONArray identities = new JSONArray();
        JSONObject identity = new JSONObject();
        identity.set("signInType", "emailAddress");
        identity.set("issuer", B2C_DOMAIN);
        identity.set("issuerAssignedId", userInfo.getEmail());
        identities.set(identity);
        info.set("identities", identities);

        // password profile
        JSONObject passwordProfile = new JSONObject();
        String password1 = StringComUtils.randomUpperString(4);
        String password2 = StringComUtils.randomLowerString(4);
        String password3 = StringComUtils.randomNumberString(4);
        String password = password1 + password2 + password3;
        passwordProfile.set("password", password1 + password2 + password3);
        passwordProfile.set("forceChangePasswordNextSignIn", false);
        info.set("passwordProfile", passwordProfile);

        System.out.println("创建用户密码 === " +  password1 + password2 + password3);
        // create
        try {
            httpPost.setEntity(new StringEntity(info.toString()));
            CloseableHttpResponse execute = httpclient.execute(httpPost);
            HttpEntity entity = execute.getEntity();
            JSONObject user = JSONUtil.parseObj(EntityUtils.toString(entity));
            if (user.containsKey("id")) {
                // send email to email
                String content = MailContentUtil.successfulRegistration(userInfo.getLastName() + " " + userInfo.getFirstName(),
                        "<a>https://testdevad.canadapoaregistry.com/auth/login<a>", userInfo.getEmail(), password);
               // mailService.sendSimpleEmail(userInfo.getEmail(), "Successful Registration", content);

                user.putOnce("isNew", true);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    /**
     * update user information
     */
    public void updateUser(UserInfoBO userInfo) {
        if (StringUtils.isNotBlank(userInfo.getId())) {
            HttpPatch httpPatch = new HttpPatch(API_PREFIX + "users/" + userInfo.getId());
            httpPatch.setHeader("Content-Type", "application/json");
            httpPatch.setHeader("Authorization", "Bearer " + getToken());

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
                httpPatch.setEntity(new StringEntity(info.toString()));
                CloseableHttpResponse execute = httpclient.execute(httpPatch);
                if (!(execute.getStatusLine().getStatusCode() == HttpStatus.NO_CONTENT.value())) {// update error
                    HttpEntity entity = execute.getEntity();
                    JSONObject result = JSONUtil.parseObj(EntityUtils.toString(entity));
                    if (result.containsKey("error") && result.getJSONObject("error").containsKey("message")) {
                        throw new PoaException(500, result.getJSONObject("error").getStr("message"));
                    }
                    throw new PoaException(500, "call azure api error!");
                }

            } catch (Exception e) {
                throw new PoaException(500, e.getMessage());
            }

        } else {
            throw new PoaException(400, "user id can not be empty!");
        }
    }

    /**
     * change password
     *
     * @param userId user id
     * @param password password
     */
    public void changePassword(String userId, String password) {
        if (StringUtils.isNotBlank(userId)) {
            HttpPatch httpPatch = new HttpPatch(API_PREFIX + "users/" + userId);
            httpPatch.setHeader("Content-Type", "application/json");
            httpPatch.setHeader("Authorization", "Bearer " + getToken());
            JSONObject obj = new JSONObject();
            Map<String, Object> map = new HashMap<>();
            map.put("forceChangePasswordNextSignIn", false);
            map.put("password", password);

            obj.putOpt("passwordProfile", map);
            obj.putOpt("passwordPolicies", "DisablePasswordExpiration");

            try {
                httpPatch.setEntity(new StringEntity(obj.toString()));
                CloseableHttpResponse execute = httpclient.execute(httpPatch);
                if (!(execute.getStatusLine().getStatusCode() == HttpStatus.NO_CONTENT.value())) {// update error
                    HttpEntity entity = execute.getEntity();
                    JSONObject result = JSONUtil.parseObj(EntityUtils.toString(entity));
                    if (result.containsKey("error") && result.getJSONObject("error").containsKey("message")) {
                        throw new PoaException(500, result.getJSONObject("error").getStr("message"));
                    }
                    throw new PoaException(500, "call azure api error!");
                }

            } catch (Exception e) {
                throw new PoaException(500, e.getMessage());
            }

        } else {
            throw new PoaException(400, "user id can not be empty!");
        }
    }
}
