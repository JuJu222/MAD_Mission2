package com.example.mission2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String fullName;
    private String age;
    private String address;

    public User(String fullName, String age, String address) {
        this.fullName = fullName;
        this.age = age;
        this.address = address;
    }

    protected User(Parcel in) {
        fullName = in.readString();
        age = in.readString();
        address = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullName);
        dest.writeString(age);
        dest.writeString(address);
    }
}
