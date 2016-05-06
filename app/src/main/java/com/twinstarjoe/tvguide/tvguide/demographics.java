package com.twinstarjoe.tvguide.tvguide;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class demographics {

    public int num;
    public String img;
    public String lnk;
    public String name;
    public String air;
    public String desc;
    @SerializedName("listings")
    public List episodes;
    public demographics() {

    }
}