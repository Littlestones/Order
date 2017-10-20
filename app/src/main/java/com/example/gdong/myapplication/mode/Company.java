package com.example.gdong.myapplication.mode;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by Gdong on 2017/10/16.
 */

public class Company  extends BmobObject implements Serializable {
    private String id;

    public Company(String id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String name;
    private String icon;

}
