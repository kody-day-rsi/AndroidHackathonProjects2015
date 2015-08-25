package rsi.com.applicationstub.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Job implements Parcelable{

    private String position;
    private String location;

    public Job() {
        // default constructor
    }

    public Job(String position, String location) {
        this.position = position;
        this.location = location;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private Job(Parcel in) {
        position = in.readString();
        location = in.readString();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(position);
        dest.writeString(location);
    }

    public static final Parcelable.Creator<Job> CREATOR = new Parcelable.Creator<Job>() {

        @Override public Job createFromParcel(Parcel source) {
            return new Job(source);
        }

        @Override public Job[] newArray(int size) {
            return new Job[size];
        }
    };
}
