package org.fran.cloud.azure.mediaservice;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.fran.cloud.azure.mediaservice.po.Result;
import org.fran.cloud.azure.mediaservice.po.Token;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MediaServiceTest extends AbstractHttpClientService{


    public Result<Token> login(){

        HttpPost post = new HttpPost("https://login.partner.microsoftonline.cn/cgtn.partner.onmschina.cn/oauth2/token");
        post.setHeader("Content-type", "application/x-www-form-urlencoded");
        post.setHeader("Keep-Alive", "true");

        try {
            List<NameValuePair> param = new ArrayList<>();
            param.add(getFormParam("grant_type", "client_credentials"));
            param.add(getFormParam("client_id", "afaf437d-9933-45de-b1e5-086bf6f2a752"));
            param.add(getFormParam("client_secret", "/3ZfR2FO39gSTQJz2w4xoJmY9RmiL7lvmbD+AVLUBGk="));
            param.add(getFormParam("resource", "https://rest.media.chinacloudapi.cn"));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "UTF-8");

//            String body = "";
//            StringEntity entity = new StringEntity(body, StandardCharsets.UTF_8);
            post.setEntity(entity);

            HttpResponse r = client.execute(post);

            try(InputStream stream = r.getEntity().getContent()){
                Token token = mapper.readValue(stream, Token.class);
                return new Result<>(token);
            }catch (Exception e){
                return new Result<>(null, 500, e.getMessage());
            }
//            byte[] res = input2byte(r.getEntity().getContent());
//            System.out.println(new String(res, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            return new Result<>(null, 500, e.getMessage());
        }

    }

    public static NameValuePair getFormParam(String key, String value){
        return new NameValuePair() {
            @Override
            public String getName() {
                return key;
            }

            @Override
            public String getValue() {
                return value;
            }
        };
    }

    public static void main(String[] agrs){
        MediaServiceTest t = new MediaServiceTest();
        t.init();
        t.login();
    }
}
