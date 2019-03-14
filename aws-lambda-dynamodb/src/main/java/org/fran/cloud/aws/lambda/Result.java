package org.fran.cloud.aws.lambda;

/**
 * @author fran
 * @Description
 * @Date 2019/3/13 15:35
 */
public class Result {
    int status;
    String description;
    Object res;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getRes() {
        return res;
    }

    public void setRes(Object res) {
        this.res = res;
    }
}
