package com.example.notesapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;

public class Note implements Parcelable {
    private String title;
    private String details;
    private Date createdDate;


    public Note(String title, String details, Date createdDate) {
        this.title = title;
        this.details = details;
        this.createdDate = createdDate;
    }

    protected Note(Parcel in) {
        title = in.readString();
        details = in.readString();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetails(String details) {
        this.details = details;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(title, note.title) && Objects.equals(details, note.details) && Objects.equals(createdDate, note.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, details, createdDate);
    }
}
