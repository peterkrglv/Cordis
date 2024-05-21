package com.example.cordis.domain.user;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private String uid;
    private String name;
    private List<String> playlists;
    private List<String> favourites;
    private List<String> createdSongs;
    private List<String> createdPlaylists;

    public UserModel() {
        uid = "";
        name = "";
        playlists = new ArrayList<>();
        favourites = new ArrayList<>();
        createdSongs = new ArrayList<>();
        createdPlaylists = new ArrayList<>();
    }

    public UserModel(String uid, String name) {
        this.uid = uid;
        this.name = name;
        playlists = new ArrayList<>();
        favourites = new ArrayList<>();
        createdSongs = new ArrayList<>();
        createdPlaylists = new ArrayList<>();
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public List<String> getPlaylists() {
        return playlists;
    }

    public List<String> getFavourites() {
        return favourites;
    }

    public List<String> getCreatedSongs() {
        return createdSongs;
    }

    public List<String> getCreatedPlaylists() {
        return createdPlaylists;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlaylists(List<String> playlists) {
        this.playlists = playlists;
    }

    public void setFavourites(List<String> favourites) {
        this.favourites = favourites;
    }

    public void setCreatedSongs(List<String> createdSongs) {
        this.createdSongs = createdSongs;
    }

    public void setCreatedPlaylists(List<String> createdPlaylists) {
        this.createdPlaylists = createdPlaylists;
    }
}
