/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.whatsapp;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author altius
 */
public class WhatsAppOptInAndOptOutService {

    public static String optInUser(String userId, String password, String phoneNo, int timeout, int minTimeout, int maxTimeout, String GIVEN_KEY_FOR_ENCRYPTION) throws IOException, Exception {
        String result = "";
        StringBuilder urlString = new StringBuilder();
        if (GIVEN_KEY_FOR_ENCRYPTION.isEmpty() || GIVEN_KEY_FOR_ENCRYPTION.equals("")) {
            urlString.append("https://media.smsgupshup.com/GatewayAPI/rest?userid=").append(userId).append("&method=OPT_IN&format=json&password=").append(password).append("&phone_number=").append(phoneNo).append("&v=1.1&auth_scheme=plain&channel=WHATSAPP");
        } else {
            String Base64_Encoded_Encrypted_Data = AESEncrypt.encrypt("method=OPT_IN&format=json&password=" + password + "&phone_number=" + phoneNo + "&v=1.1&auth_scheme=plain&channel=WHATSAPP", GIVEN_KEY_FOR_ENCRYPTION);
            urlString.append("https://media.smsgupshup.com/GatewayAPI/rest?userid=").append(userId).append("&encrdata=").append(Base64_Encoded_Encrypted_Data);
        }

        CloseableHttpClient httpclient = null;
        if (timeout == 1) {
            RequestConfig config = RequestConfig.custom().setConnectTimeout(maxTimeout * 1000).setConnectionRequestTimeout(maxTimeout * 1000).setSocketTimeout(maxTimeout * 1000).build();
            httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        } else {
            RequestConfig config = RequestConfig.custom().setConnectTimeout(minTimeout * 1000).setConnectionRequestTimeout(minTimeout * 1000).setSocketTimeout(minTimeout * 1000).build();
            httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        }
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(urlString.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        CloseableHttpResponse response = null;
        response = httpclient.execute(httpGet);

        try {
            HttpEntity entity1 = response.getEntity();
            result = EntityUtils.toString(entity1);
            EntityUtils.consumeQuietly(entity1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.close();
        }
        return result;
    }

    public static String optOutUser(String userId, String password, String phoneNo, int timeout, int minTimeout, int maxTimeout, String GIVEN_KEY_FOR_ENCRYPTION) throws IOException, Exception {
        String result = "";
        StringBuilder urlString = new StringBuilder();

        if (GIVEN_KEY_FOR_ENCRYPTION.isEmpty() || GIVEN_KEY_FOR_ENCRYPTION.equals("")) {
            urlString.append("https://media.smsgupshup.com/GatewayAPI/rest?userid=").append(userId).append("&method=OPT_OUT&format=json&password=").append(password).append("&phone_number=").append(phoneNo).append("&v=1.1&auth_scheme=plain&channel=WHATSAPP");
        } else {
            String Base64_Encoded_Encrypted_Data = AESEncrypt.encrypt("method=OPT_OUT&format=json&password=" + password + "&phone_number=" + phoneNo + "&v=1.1&auth_scheme=plain&channel=WHATSAPP", GIVEN_KEY_FOR_ENCRYPTION);
            urlString.append("https://media.smsgupshup.com/GatewayAPI/rest?userid=").append(userId).append("&encrdata=").append(Base64_Encoded_Encrypted_Data);
        }

        CloseableHttpClient httpclient = null;
        if (timeout == 1) {
            RequestConfig config = RequestConfig.custom().setConnectTimeout(maxTimeout * 1000).setConnectionRequestTimeout(maxTimeout * 1000).setSocketTimeout(maxTimeout * 1000).build();
            httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        } else {
            RequestConfig config = RequestConfig.custom().setConnectTimeout(minTimeout * 1000).setConnectionRequestTimeout(minTimeout * 1000).setSocketTimeout(minTimeout * 1000).build();
            httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        }
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(urlString.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        CloseableHttpResponse response = null;
        response = httpclient.execute(httpGet);

        try {
            HttpEntity entity1 = response.getEntity();
            result = EntityUtils.toString(entity1);
            EntityUtils.consumeQuietly(entity1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.close();
        }
        return result;
    }

//    public static void main(String[] args) throws Exception {
    /* Note that values in query String are URL encoded. */
//        String queryString = "method=SendMessage&send_to=919XXXXXXXXX&msg=This%20is%20a%20test%20message&msg_type=TEXT&auth_scheme=plain&password=password&v=1.1&format=text ";
//        System.out.println(WhatsAppOptInAndOptOutService.optOutUser("", "", "", 1, 1, 1, ""));
//    }
}
