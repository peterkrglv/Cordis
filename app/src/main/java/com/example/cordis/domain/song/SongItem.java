package com.example.cordis.domain.song;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SongItem implements Parcelable {
    private String songId;
    private String songName;
    private String songArtist;
    private String tuning;
    private String capo;
    private String chords;
    private String owner;

    public SongItem() {
    }

    public SongItem(String songId, String songName, String songArtist, String tuning, String capo, String chords, String owner) {
        this.songId = songId;
        this.songName = songName;
        this.songArtist = songArtist;
        this.tuning = tuning;
        this.capo = capo;
        this.chords = chords;
        this.owner = owner;
    }

    public SongItem(SongModel songModel) {
        this.songId = songModel.getSongId();
        this.songName = songModel.getSongName();
        this.songArtist = songModel.getSongArtist();
        this.tuning = songModel.getTuning();
        this.capo = songModel.getCapo();
        this.chords = songModel.getChords();
        this.owner = songModel.getOwner();
    }

    protected SongItem(Parcel in) {
        songId = in.readString();
        songName = in.readString();
        songArtist = in.readString();
        tuning = in.readString();
        capo = in.readString();
        chords = in.readString();
        owner = in.readString();
    }

    public static final Creator<SongItem> CREATOR = new Creator<SongItem>() {
        @Override
        public SongItem createFromParcel(Parcel in) {
            return new SongItem(in);
        }

        @Override
        public SongItem[] newArray(int size) {
            return new SongItem[size];
        }
    };

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getTuning() {
        return tuning;
    }

    public void setTuning(String tuning) {
        this.tuning = tuning;
    }

    public String getCapo() {
        return capo;
    }

    public void setCapo(String capo) {
        this.capo = capo;
    }

    public String getChords() {
        return chords;
    }

    public void setChords(String chords) {
        this.chords = chords;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(songId);
        dest.writeString(songName);
        dest.writeString(songArtist);
        dest.writeString(tuning);
        dest.writeString(capo);
        dest.writeString(chords);
        dest.writeString(owner);
    }
}
