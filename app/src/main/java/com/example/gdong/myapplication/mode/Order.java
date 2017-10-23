package com.example.gdong.myapplication.mode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Gdong on 2017/10/16.
 */

public class Order extends BmobObject implements Serializable{
    private String id;
    private List<String> image_urls;
    private String detail_xiadan;
    private String detail_jiaoqi;
    private String detail_queren;

    public Order(String id, List<String> image_urls, String detail_xiadan, String detail_jiaoqi, String detail_queren, String remark, String companyid) {
        this.id = id;
        this.image_urls = image_urls;
        this.detail_xiadan = detail_xiadan;
        this.detail_jiaoqi = detail_jiaoqi;
        this.detail_queren = detail_queren;
        this.remark = remark;
        this.companyid = companyid;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", image_urls=" + image_urls +
                ", detail_xiadan='" + detail_xiadan + '\'' +
                ", detail_jiaoqi='" + detail_jiaoqi + '\'' +
                ", detail_queren='" + detail_queren + '\'' +
                ", remark='" + remark + '\'' +
                ", companyid='" + companyid + '\'' +
                '}';

    }

    private String remark;
    private String companyid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getImage_urls() {
        return image_urls;
    }

    public void setImage_urls(List<String> image_urls) {
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

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }
}
