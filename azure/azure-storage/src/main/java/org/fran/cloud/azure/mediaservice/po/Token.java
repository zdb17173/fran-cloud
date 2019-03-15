package org.fran.cloud.azure.mediaservice.po;

public class Token {
    String token_type;
    String expires_in;
    String ext_expires_in;
    String expires_on;
    String not_before;
    String resource;
    String access_token;

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getExt_expires_in() {
        return ext_expires_in;
    }

    public void setExt_expires_in(String ext_expires_in) {
        this.ext_expires_in = ext_expires_in;
    }

    public String getExpires_on() {
        return expires_on;
    }

    public void setExpires_on(String expires_on) {
        this.expires_on = expires_on;
    }

    public String getNot_before() {
        return not_before;
    }

    public void setNot_before(String not_before) {
        this.not_before = not_before;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
