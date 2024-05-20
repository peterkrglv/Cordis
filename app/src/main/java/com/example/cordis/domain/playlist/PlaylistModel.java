package com.example.cordis.domain.playlist;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.cordis.domain.song.SongModel;

import java.util.List;

public class PlaylistModel implements Parcelable {
    //Based on the playlist_item.xml
    private String playlistName;
    private String playlistOwner;
    private String playlistDescription;
    private Bitmap playlistImage;
    private List<SongModel> songs;

    public PlaylistModel(String playlistName, String playlistOwner,
                         String playlistDescription, Bitmap playlistImage) {
        this.playlistName = playlistName;
        this.playlistOwner = playlistOwner;
        this.playlistDescription = playlistDescription;
        this.playlistImage = playlistImage;
    }

    public static final Creator<PlaylistModel> CREATOR = new Creator<PlaylistModel>() {
        @Override
        public PlaylistModel createFromParcel(Parcel in) {
            return new PlaylistModel(in);
        }

        @Override
        public PlaylistModel[] newArray(int size) {
            return new PlaylistModel[size];
        }
    };

    protected PlaylistModel(Parcel in) {
        playlistName = in.readString();
        playlistOwner = in.readString();
        playlistDescription = in.readString();
        playlistImage = in.readParcelable(Bitmap.class.getClassLoader());
        songs = in.createTypedArrayList(SongModel.CREATOR);
    }


    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getPlaylistOwner() {
        return playlistOwner;
    }

    public void setPlaylistOwner(String playlistOwner) {
        this.playlistOwner = playlistOwner;
    }

    public String getPlaylistDescription() {
        return playlistDescription;
    }

    public void setPlaylistDescription(String playlistDescription) {
        this.playlistDescription = playlistDescription;
    }

    public Bitmap getPlaylistImage() {
        return playlistImage;
    }

    public void setPlaylistImage(Bitmap playlistImage) {
        this.playlistImage = playlistImage;
    }

    public List<SongModel> getSongs() {
        return songs;
    }

    public void setSongs(List<SongModel> songs) {
        this.songs = songs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.playlistName);
        dest.writeString(this.playlistOwner);
        dest.writeString(this.playlistDescription);
        dest.writeParcelable(this.playlistImage, flags);
        dest.writeTypedList(this.songs);
    }
}
