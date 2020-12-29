package com.positive.themeselector.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Theme implements Serializable {

    @SerializedName("Id")
    public int Id;
    @SerializedName("Cat_Id")
    public int Cat_Id;
    @SerializedName("Theme_Id")
    public int Theme_Id;
    @SerializedName("Theme_Name")
    public String Theme_Name;

    @SerializedName("Thumnail_Big")
    public String Thumnail_Big;
    @SerializedName("Thumnail_Small")
    public String Thumnail_Small;

    @SerializedName("SoundName")
    public String SoundName;
    @SerializedName("SoundFile")
    public String SoundFile;
    @SerializedName("sound_size")
    public String sound_size;
    @SerializedName("lyrics")
    public String lyrics;


    @SerializedName("GameobjectName")
    public int GameobjectName;
    @SerializedName("Is_Preimum")
    public int Is_Preimum;
    @SerializedName("Status")
    public int Status;
}
