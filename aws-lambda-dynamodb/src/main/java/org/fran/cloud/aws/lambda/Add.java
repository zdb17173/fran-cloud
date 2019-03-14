package org.fran.cloud.aws.lambda;

import java.util.Map;

/**
 * @author fran
 * @Description
 * @Date 2019/3/13 15:54
 */
public class Add {
    String id;
    Map<String, Integer> item;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Integer> getItem() {
        return item;
    }

    public void setItem(Map<String, Integer> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Add{" +
                "id='" + id + '\'' +
                ", item=" + item +
                '}';
    }
}
