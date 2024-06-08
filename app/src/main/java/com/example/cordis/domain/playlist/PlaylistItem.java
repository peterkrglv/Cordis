package com.example.cordis.domain.playlist;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.cordis.domain.song.SongModel;

import java.util.ArrayList;
import java.util.List;

public class PlaylistItem implements Parcelable {
    private String playlistId = "";
    private String playlistName = "";
    private String playlistOwner = "";
    private String playlistOwnerName = "";
    private String playlistDescription = "";
    private List<String> songs = new ArrayList<>();

    public PlaylistItem() {
        playlistId = "";
        playlistName = "";
        playlistOwner = "";
        playlistOwnerName = "";
        playlistDescription = "";
        songs = new ArrayList<>();
    }

    public PlaylistItem(String playlistId, String playlistName, String playlistOwner, String playlistOwnerName, String playlistDescription, List<String> songs) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.playlistOwner = playlistOwner;
        this.playlistOwnerName = playlistOwnerName;
        this.playlistDescription = playlistDescription;
        this.songs = songs;
    }

    public PlaylistItem(PlaylistModel playlist) {
        this.playlistId = playlist.getPlaylistId();
        this.playlistName = playlist.getPlaylistName();
        this.playlistOwner = playlist.getPlaylistOwner();
        this.playlistOwnerName = playlist.getPlaylistOwnerName();
        this.playlistDescription = playlist.getPlaylistDescription();
        this.songs = playlist.getSongs();
    }

    protected PlaylistItem(Parcel in) {
        playlistId = in.readString();
        playlistName = in.readString();
        playlistOwner = in.readString();
        playlistOwnerName = in.readString();
        playlistDescription = in.readString();
        in.readStringList(songs);
    }

    public static final Creator<PlaylistItem> CREATOR = new Creator<PlaylistItem>() {
        @Override
        public PlaylistItem createFromParcel(Parcel in) {
            return new PlaylistItem(in);
        }

        @Override
        public PlaylistItem[] newArray(int size) {
            return new PlaylistItem[size];
        }
    };

    public String getPlaylistId() {
        return playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public String getPlaylistOwner() {
        return playlistOwner;
    }

    public String getPlaylistOwnerName() {
        return playlistOwnerName;
    }

    public String getPlaylistDescription() {
        return playlistDescription;
    }

    public List<String> getSongs() {
        return songs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.playlistId);
        dest.writeString(this.playlistName);
        dest.writeString(this.playlistOwner);
        dest.writeString(this.playlistOwnerName);
        dest.writeString(this.playlistDescription);
        dest.writeStringList(this.songs);
    }
}
