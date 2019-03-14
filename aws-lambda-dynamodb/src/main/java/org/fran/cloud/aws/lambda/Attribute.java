package org.fran.cloud.aws.lambda;

/**
 * @author fran
 * @Description
 * @Date 2019/3/13 15:30
 */
public class Attribute {
    String key;
    String value;
    String type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
