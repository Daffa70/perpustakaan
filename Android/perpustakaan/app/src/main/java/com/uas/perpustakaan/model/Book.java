package com.uas.perpustakaan.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String imageUrl;
    private String title;
    private String content;
    private String author;
    private String createdAt;
    private String id;
    private String releaseBook;

    public String getReleaseBook() {
        return releaseBook;
    }

    public void setReleaseBook(String releaseBook) {
        this.releaseBook = releaseBook;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageUrl);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.author);
        dest.writeString(this.createdAt);
        dest.writeString(this.id);
        dest.writeString(this.releaseBook);
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.imageUrl = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.author = in.readString();
        this.createdAt = in.readString();
        this.id = in.readString();
        this.releaseBook = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

}
