package com.dipesh.auctionapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Dipeshdhakal on 5/17/16.
 */
public class AuctionItem implements Parcelable {

    String name;
    String description;
    long uniqueID;
    User postedUser;
    Date expiryDateTime;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(long uniqueID) {
        this.uniqueID = uniqueID;
    }

    public User getPostedUser() {
        return postedUser;
    }

    public void setPostedUser(User postedUser) {
        this.postedUser = postedUser;
    }

    public Date getExpiryDateTime() {
        return expiryDateTime;
    }

    public void setExpiryDateTime(Date expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }

    public AuctionItem(){

    }

    protected AuctionItem(Parcel in) {
        name = in.readString();
        description = in.readString();
        uniqueID = in.readLong();
        postedUser = (User) in.readValue(User.class.getClassLoader());
        long tmpExpiryDateTime = in.readLong();
        expiryDateTime = tmpExpiryDateTime != -1 ? new Date(tmpExpiryDateTime) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeLong(uniqueID);
        dest.writeValue(postedUser);
        dest.writeLong(expiryDateTime != null ? expiryDateTime.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AuctionItem> CREATOR = new Parcelable.Creator<AuctionItem>() {
        @Override
        public AuctionItem createFromParcel(Parcel in) {
            return new AuctionItem(in);
        }

        @Override
        public AuctionItem[] newArray(int size) {
            return new AuctionItem[size];
        }
    };
}