/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.whatsapp;

import java.io.IOException;
import java.net.URLEncoder;
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
public class WhatsAppNotificationService {

    public static String sendNotification(String userId, String password, String mobileNo, int timeout, int minTimeout, int maxTimeout, String message, String GIVEN_KEY_FOR_ENCRYPTION, Boolean quickReply, String header) throws IOException, Exception {
        String result = null;
        StringBuilder urlString = new StringBuilder();
        String Base64_Encoded_Encrypted_Data = "";
        String urlForEncrypt = "";
        if (mobileNo.length() == 10) {
            if (GIVEN_KEY_FOR_ENCRYPTION.isEmpty()) {
                urlString.append("https://media.smsgupshup.com/GatewayAPI/rest?").append("userid=")
                        .append(userId).append("&method=SendMessage&format=json&password=").append(password).append("&send_to=").append(mobileNo).append("&v=1.1&auth_scheme=plain&msg=")
                        .append(URLEncoder.encode(message, "UTF-8"))
                        .append("&msg_type=TEXT");
                if (quickReply) {
                    urlString.append("&isTemplate=true");
                }
                if (!header.isEmpty()) {
                    urlString.append("&header=").append(URLEncoder.encode(header, "UTF-8"));
                }
            } else {
                urlForEncrypt = "method=SendMessage&format=json&password=" + password + "&send_to=" + mobileNo + "&v=1.1&auth_scheme=plain&msg=" + URLEncoder.encode(message, "UTF-8") + "&msg_type=HSM";
                if (quickReply) {
                    urlForEncrypt += "&isTemplate=true";
                }
                if (!header.isEmpty()) {
                    urlForEncrypt += "&header=" + URLEncoder.encode(header, "UTF-8");
                }
                Base64_Encoded_Encrypted_Data = AESEncrypt.encrypt(urlForEncrypt, GIVEN_KEY_FOR_ENCRYPTION);
                urlString.append("https://media.smsgupshup.com/GatewayAPI/rest?").append("userid=")
                        .append(userId)
                        .append("&encrdata=")
                        .append(Base64_Encoded_Encrypted_Data);
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
            CloseableHttpResponse response1 = null;

            response1 = httpclient.execute(httpGet);

            try {
                HttpEntity entity1 = response1.getEntity();
                result = EntityUtils.toString(entity1);
                EntityUtils.consumeQuietly(entity1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response1.close();
            }
            return result;
        } else {
            return "Invalid Phone Number";
        }

    }

//    public static void main(String[] args) {
//        String msg = "Welcome%20to%20Gupshup%20API";
//        try {
//            System.out.println(WhatsAppNotificationService.sendNotification("", "", "", 1, 1, 1, msg, "", Boolean.TRUE, ""));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
        }
