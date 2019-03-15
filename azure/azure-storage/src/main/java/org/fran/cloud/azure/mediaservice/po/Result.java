package org.fran.cloud.azure.mediaservice.po;

public class Result<T> {
    T data;
    int status;
    String description;

    public Result(T data){
        this.status = 200;
    }

    public Result(T data, int status, String description) {
        this.data = data;
        this.status = status;
        this.description = description;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

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
}
