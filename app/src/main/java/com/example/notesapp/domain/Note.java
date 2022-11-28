package com.example.notesapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private final String title;
    private final String details;


    public Note(String title, String details) {
        this.title = title;
        this.details = details;
    }

    protected Note(Parcel in) {
        title = in.readString();
        details = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(details);
    }
}
