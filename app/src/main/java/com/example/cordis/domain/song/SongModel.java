package com.example.cordis.domain.song;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SongModel implements Parcelable {
    private String songName;
    private String songArtist;
    private String tuning;
    private String capo;
    private String chords;
    private Bitmap songImage;
    private String owner;

    public SongModel(String songName, String songArtist, String tuning, String capo, String chords, Bitmap songImage, String owner) {
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
        songImage = in.readParcelable(Bitmap.class.getClassLoader());
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

    public Bitmap getSongImage() {
        return songImage;
    }

    public void setSongImage(Bitmap songImage) {
        this.songImage = songImage;
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
        dest.writeString(this.songName);
        dest.writeString(this.songArtist);
        dest.writeString(this.tuning);
        dest.writeString(this.capo);
        dest.writeString(this.chords);
        dest.writeParcelable(this.songImage, flags);
        dest.writeString(this.owner);
    }

}
