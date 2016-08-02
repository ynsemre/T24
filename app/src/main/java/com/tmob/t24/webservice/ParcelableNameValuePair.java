package com.tmob.t24.webservice;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.http.NameValuePair;

public class ParcelableNameValuePair implements NameValuePair, Parcelable{

	String name, value;
	
	public ParcelableNameValuePair(String name, String value){
		this.name = name;
		this.value = value;	
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	public int describeContents() {
		return 0;
	}


	public void writeToParcel(Parcel out, int flags) {
		out.writeString(name);
		out.writeString(value);		
	}

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Creator<ParcelableNameValuePair> CREATOR = new Creator<ParcelableNameValuePair>() {
        public ParcelableNameValuePair createFromParcel(Parcel in) {
            return new ParcelableNameValuePair(in);
        }

        public ParcelableNameValuePair[] newArray(int size) {
            return new ParcelableNameValuePair[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private ParcelableNameValuePair(Parcel in) {
		name = in.readString();
		value = in.readString();
    }


}