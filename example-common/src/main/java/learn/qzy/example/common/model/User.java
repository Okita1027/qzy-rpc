package learn.qzy.example.common.model;

import java.io.Serializable;

/**
 * @author qzy
 * @time 2025年01月01日 17:02 星期三
 * @title 用户
 */
public class User implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
