package org.fran.cloud.aws.lambda;

import java.util.Map;

/**
 * @author fran
 * @Description
 * @Date 2019/3/13 15:28
 */
public class Data {
    String id;
    Map<String, String> value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getValue() {
        return value;
    }

    public void setValue(Map<String, String> value) {
        this.value = value;
    }
}
