package com.example.cordis.ui.playlists;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cordis.data.ImageRepositoryImpl;
import com.example.cordis.data.PlaylistRepositoryImpl;
import com.example.cordis.data.SongRepositoryImpl;
import com.example.cordis.data.UserRepositoryImpl;
import com.example.cordis.domain.ImageRepository;
import com.example.cordis.domain.playlist.GetCreatedPlaylistsUseCase;
import com.example.cordis.domain.playlist.PlaylistModel;
import com.example.cordis.domain.playlist.PlaylistRepository;
import com.example.cordis.domain.song.GetAllSongsUseCase;
import com.example.cordis.domain.song.SongModel;
import com.example.cordis.domain.song.SongRepository;
import com.example.cordis.domain.user.UserRepository;
import com.google.firebase.firestore.auth.User;

import java.util.List;

public class PlaylistsViewModel extends ViewModel {
    MutableLiveData<List<PlaylistModel>> createdPlaylists = new MutableLiveData<>();
    MutableLiveData<GetPlaylistsState> playlistsState = new MutableLiveData<>();
    MutableLiveData<List<SongModel>> songs = new MutableLiveData<>();
    MutableLiveData<GetSongsState> songsState = new MutableLiveData<>();

    public void getPlaylists() {
        playlistsState.postValue(GetPlaylistsState.LOADING);

        try {
            new Thread(() -> {
                UserRepository userRepository = new UserRepositoryImpl();
                PlaylistRepository playlistRepository = new PlaylistRepositoryImpl();
                ImageRepository imageRepository = new ImageRepositoryImpl();
                List<PlaylistModel> playlists = GetCreatedPlaylistsUseCase.execute(
                        userRepository,
                        playlistRepository,
                        imageRepository);
                createdPlaylists.postValue(playlists);
                playlistsState.postValue(GetPlaylistsState.SUCCESS);
            }).start();
        } catch (Exception e) {
            Log.e("PlaylistsViewModel", "Error getting playlists", e);
            playlistsState.postValue(GetPlaylistsState.ERROR);
        }
    }

    public void getAllSongs() {
        songsState.postValue(GetSongsState.LOADING);
        try {
            new Thread(() -> {
                SongRepository songRepository = new SongRepositoryImpl();
                ImageRepository imageRepository = new ImageRepositoryImpl();
                UserRepository userRepository = new UserRepositoryImpl();
                List<SongModel> songs = GetAllSongsUseCase.execute(
                        songRepository, imageRepository, userRepository
                );
                this.songs.postValue(songs);
                songsState.postValue(GetSongsState.SUCCESS);
            }).start();
        } catch (Exception e) {
            songsState.postValue(GetSongsState.ERROR);
            Log.e("PlaylistsViewModel", "Error getting songs", e);
        }
    }
}

enum GetPlaylistsState {
    LOADING,
    SUCCESS,
    ERROR
}

enum GetSongsState {
    LOADING,
    SUCCESS,
    ERROR
}

