package org.fran.cloud.azure.mediaservice;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

public abstract class AbstractHttpClientService {

    protected HttpClient client;
    protected ObjectMapper mapper;
    protected RequestConfig requestConfig;

    private Integer connectTimeout;
    private Integer connectionRequestTimeout;
    private Integer socketTimeOut;

    public void init(){

        if(connectTimeout == null || connectionRequestTimeout == null|| socketTimeOut == null){
            connectTimeout = 10000;
            connectionRequestTimeout = 100000;
            socketTimeOut = 100000;
        }

        client = HttpClientBuilder.create().build();
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeOut)
                .build();
    }

    public String execute(HttpUriRequest request) throws IOException {
        InputStream stream = null;
        try {
            HttpResponse response = client.execute(request);
            stream = response.getEntity().getContent();
            return new String(input2byte(stream));
        }catch (SocketTimeoutException e){
            throw e;
        }catch (ConnectTimeoutException e) {
            throw e;
        }catch (ConnectException e) {
            throw e;
        }catch (Exception e){
            throw e;
        }finally {
            if(stream!=null){
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    protected static  byte[] input2byte(InputStream inStream) {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        byte[] in2b = null;
        int rc = 0;
        try {
            while ((rc = inStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            in2b= swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                swapStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return in2b;
    }


    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public void setSocketTimeOut(Integer socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }
}
