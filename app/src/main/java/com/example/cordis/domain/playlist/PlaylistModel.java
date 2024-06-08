package com.example.cordis.domain.playlist;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelWriter.writeByteArray;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.cordis.domain.song.SongModel;

import java.util.ArrayList;
import java.util.List;

public class PlaylistModel implements Parcelable {
    private String playlistId = "";
    private String playlistName = "";
    private String playlistOwner = "";
    private String playlistOwnerName = "";
    private String playlistDescription = "";
    private byte[] playlistImage = new byte[0];
    private List<String> songs = new ArrayList<>();

    public PlaylistModel() {
        playlistId = "";
        playlistName = "";
        playlistOwner = "";
        playlistOwnerName = "";
        playlistDescription = "";
        playlistImage = new byte[0];
    }

    public PlaylistModel(PlaylistItem playlistItem) {
        this.playlistId = playlistItem.getPlaylistId();
        this.playlistName = playlistItem.getPlaylistName();
        this.playlistOwner = playlistItem.getPlaylistOwner();
        this.playlistOwnerName = playlistItem.getPlaylistOwnerName();
        this.playlistDescription = playlistItem.getPlaylistDescription();
        this.songs = playlistItem.getSongs();
    }

    public PlaylistModel(
            String playlistId, String playlistName, String playlistOwner,
            String playlistOwnerName, String playlistDescription, byte[] playlistImage) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.playlistOwner = playlistOwner;
        this.playlistOwnerName = playlistOwnerName;
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
        playlistId = in.readString();
        playlistName = in.readString();
        playlistOwner = in.readString();
        playlistOwnerName = in.readString();
        playlistDescription = in.readString();
        playlistImage = in.createByteArray();
        in.readStringList(songs);
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

    public byte[] getPlaylistImage() {
        return playlistImage;
    }

    public void setPlaylistImage(byte[] playlistImage) {
        this.playlistImage = playlistImage;
    }

    public List<String> getSongs() {
        return songs;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setOwner(String owner) {
        this.playlistOwner = owner;
    }

    public String getOwner() {
        return playlistOwner;
    }

    public String getPlaylistOwnerName() {return playlistOwnerName;}

    public void setPlaylistOwnerName(String playlistOwnerName) {this.playlistOwnerName = playlistOwnerName;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.playlistName);
        dest.writeString(this.playlistOwner);
        dest.writeString(this.playlistDescription);
        dest.writeByteArray(this.playlistImage);
        dest.writeStringList(this.songs);
    }
}
