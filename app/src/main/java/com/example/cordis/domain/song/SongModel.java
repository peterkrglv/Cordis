package com.example.cordis.domain.song;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SongModel implements Parcelable {

    private String songId = "";
    private String songName = "";
    private String songArtist = "";
    private String tuning = "";
    private String capo = "";
    private String chords = "";
    private byte[] songImage = new byte[0];
    private String owner = "";

    public SongModel() {
    }

    public SongModel(SongItem songItem) {
        this.songId = songItem.getSongId();
        this.songName = songItem.getSongName();
        this.songArtist = songItem.getSongArtist();
        this.tuning = songItem.getTuning();
        this.capo = songItem.getCapo();
        this.chords = songItem.getChords();
        this.owner = songItem.getOwner();
    }

    public SongModel(String songId, String songName, String songArtist, String tuning, String capo,
                     String chords, byte[] songImage, String owner) {
        this.songId = songId;
        this.songName = songName;
        this.songArtist = songArtist;
        this.tuning = tuning;
        this.capo = capo;
        this.chords = chords;
        this.songImage = songImage;
        this.owner = owner;
    }

    protected SongModel(Parcel in) {
        songName = in.readString();
        songArtist = in.readString();
        tuning = in.readString();
        capo = in.readString();
        chords = in.readString();
        songImage = in.createByteArray();
    }

    public static final Creator<SongModel> CREATOR = new Creator<SongModel>() {
        @Override
        public SongModel createFromParcel(Parcel in) {
            return new SongModel(in);
        }

        @Override
        public SongModel[] newArray(int size) {
            return new SongModel[size];
        }
    };

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

    public byte[] getSongImage() {
        return songImage;
    }

    public void setSongImage(byte[] songImage) {
        this.songImage = songImage;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.songName);
        dest.writeString(this.songArtist);
        dest.writeString(this.tuning);
        dest.writeString(this.capo);
        dest.writeString(this.chords);
        dest.writeByteArray(this.songImage);
        dest.writeString(this.owner);
    }

}
