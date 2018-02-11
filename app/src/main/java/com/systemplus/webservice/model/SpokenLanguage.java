package com.systemplus.webservice.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpokenLanguage implements Parcelable{

    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;
    @SerializedName("name")
    @Expose
    private String name;

    protected SpokenLanguage(Parcel in) {
        iso6391 = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iso6391);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SpokenLanguage> CREATOR = new Creator<SpokenLanguage>() {
        @Override
        public SpokenLanguage createFromParcel(Parcel in) {
            return new SpokenLanguage(in);
        }

        @Override
        public SpokenLanguage[] newArray(int size) {
            return new SpokenLanguage[size];
        }
    };

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}