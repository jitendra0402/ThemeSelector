package com.positive.themeselector.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ads implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("message")
    public String message;
    @SerializedName("status")
    public String status;
    @SerializedName("url")
    public String url;
    @SerializedName("version_code")
    public String version_code;

    @SerializedName("appid")
    public String appid;

    @SerializedName("banner")
    public String banner;
    @SerializedName("inter")
    public String inter;
    @SerializedName("reward")
    public String reward;
    @SerializedName("gnative")
    public String gnative;

    @SerializedName("fbanner")
    public String fbanner;
    @SerializedName("finter")
    public String finter;
    @SerializedName("fnative")
    public String fnative;

    @SerializedName("gstatus")
    public String gstatus;
    @SerializedName("fstatus")
    public String fstatus;

    @SerializedName("g2status")
    public String g2status;
    @SerializedName("f2status")
    public String f2status;
}
