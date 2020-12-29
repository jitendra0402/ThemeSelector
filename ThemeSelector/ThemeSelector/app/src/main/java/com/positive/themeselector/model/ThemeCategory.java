package com.positive.themeselector.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ThemeCategory implements Serializable {


    @SerializedName("Cat_Id")
    public int Cat_Id;
    @SerializedName("Category_Name")
    public String Category_Name;
    @SerializedName("Icon")
    public String Icon;
    @SerializedName("status")
    public int status;
    @SerializedName("background")
    public String background;

    @SerializedName("videos")
    public ArrayList<Theme> themes;

}
