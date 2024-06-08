package com.example.cordis.ui.songs_in_playlist;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cordis.data.ImageRepositoryImpl;
import com.example.cordis.data.SongRepositoryImpl;
import com.example.cordis.data.UserRepositoryImpl;
import com.example.cordis.domain.ImageRepository;
import com.example.cordis.domain.playlist.PlaylistModel;
import com.example.cordis.domain.song.FavouriteSongUseCase;
import com.example.cordis.domain.song.GetSongsFromPlaylistUseCase;
import com.example.cordis.domain.song.SongModel;
import com.example.cordis.domain.song.SongRepository;
import com.example.cordis.domain.user.UserRepository;

import java.util.List;

public class SongsInPlaylistViewModel extends ViewModel {
    MutableLiveData<List<SongModel>> songs = new MutableLiveData<>();
    MutableLiveData<SongsState> getSongsState = new MutableLiveData<>();

    public void getSongs(PlaylistModel playlist) {
        getSongsState.postValue(SongsState.LOADING);
        try {
            new Thread(() -> {
                SongRepository songRepository = new SongRepositoryImpl();
                ImageRepository imageRepository = new ImageRepositoryImpl();
                UserRepository userRepository = new UserRepositoryImpl();
                List<SongModel> songsList = GetSongsFromPlaylistUseCase.execute(
                        playlist, songRepository, imageRepository, userRepository
                );
                songs.postValue(songsList);
                getSongsState.postValue(SongsState.SUCCESS);
            }).start();
        } catch (Exception e) {
            getSongsState.postValue(SongsState.ERROR);
        }
    }

    public void setFavouriteState(SongModel song) {
        try {
            new Thread(() -> {
                UserRepository userRepository = new UserRepositoryImpl();
                FavouriteSongUseCase.execute(userRepository, song);
            }).start();
        } catch (Exception e) {
            Log.e("PlaylistsViewModel", "Error setting favourite state", e);
        }
    }
}

enum SongsState {
    LOADING,
    SUCCESS,
    ERROR
}