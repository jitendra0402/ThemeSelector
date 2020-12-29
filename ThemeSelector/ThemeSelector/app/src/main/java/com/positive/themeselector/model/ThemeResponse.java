package com.positive.themeselector.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ThemeResponse implements Serializable {

    @SerializedName("data")
    public ArrayList<ThemeCategory> data = null;

    @SerializedName("flag")
    public boolean flag;
    @SerializedName("message")
    public String message;
    @SerializedName("code")
    public int code;
}
