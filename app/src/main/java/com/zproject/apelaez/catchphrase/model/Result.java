package com.zproject.apelaez.catchphrase.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable {




    protected Result(Parcel in) {
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
