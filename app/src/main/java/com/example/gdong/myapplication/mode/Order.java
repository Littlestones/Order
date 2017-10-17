package com.example.gdong.myapplication.mode;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Gdong on 2017/10/16.
 */

public class Order {
    private String id;
    private ArrayList<String> image_urls;
    private String detail_xiadan;
    private String detail_jiaoqi;
    private String detail_queren;
    private String remark;

    public String getId() {
        return id;
    }

    public Order(String id, ArrayList<String> image_urls, String detail_xiadan, String detail_jiaoqi, String detail_queren, String remark) {
        this.id = id;
        this.image_urls = image_urls;
        this.detail_xiadan = detail_xiadan;
        this.detail_jiaoqi = detail_jiaoqi;
        this.detail_queren = detail_queren;
        this.remark = remark;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getImage_urls() {
        return image_urls;
    }

    public void setImage_urls(ArrayList<String> image_urls) {
        this.image_urls = image_urls;
    }

    public String getDetail_xiadan() {
        return detail_xiadan;
    }

    public void setDetail_xiadan(String detail_xiadan) {
        this.detail_xiadan = detail_xiadan;
    }

    public String getDetail_jiaoqi() {
        return detail_jiaoqi;
    }

    public void setDetail_jiaoqi(String detail_jiaoqi) {
        this.detail_jiaoqi = detail_jiaoqi;
    }

    public String getDetail_queren() {
        return detail_queren;
    }

    public void setDetail_queren(String detail_queren) {
        this.detail_queren = detail_queren;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }




}
