package com.grad.fingerprintgraduation;


import android.os.Parcel;
import android.os.Parcelable;

public class EmployeeModel implements Parcelable{
    String Uid;
    String name;
    String address;
    String bd;

    public EmployeeModel(String uid, String name, String address, String bd) {
        Uid = uid;
        this.name = name;
        this.address = address;
        this.bd = bd;
    }

    protected EmployeeModel(Parcel in) {
        Uid = in.readString();
        name = in.readString();
        address = in.readString();
        bd = in.readString();
    }

    public static final Creator<EmployeeModel> CREATOR = new Creator<EmployeeModel>() {
        @Override
        public EmployeeModel createFromParcel(Parcel in) {
            return new EmployeeModel(in);
        }

        @Override
        public EmployeeModel[] newArray(int size) {
            return new EmployeeModel[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public String getBd() {
        return bd;
    }


    public String getUid() {
        return Uid;
    }


    public String getName() {
        return name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Uid);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(bd);
    }
}
