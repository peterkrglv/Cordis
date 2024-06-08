package com.example.cordis.ui.song_chords;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cordis.data.ImageRepositoryImpl;
import com.example.cordis.data.PlaylistRepositoryImpl;
import com.example.cordis.data.UserRepositoryImpl;
import com.example.cordis.domain.ImageRepository;
import com.example.cordis.domain.playlist.GetCreatedPlaylistsUseCase;
import com.example.cordis.domain.playlist.PlaylistModel;
import com.example.cordis.domain.playlist.PlaylistRepository;
import com.example.cordis.domain.song.AddSongToPlaylistUseCase;
import com.example.cordis.domain.song.SongModel;
import com.example.cordis.domain.user.UserRepository;
import java.util.List;


public class SongChordsViewModel extends ViewModel {
    MutableLiveData<List<PlaylistModel>> createdPlaylists = new MutableLiveData<>();
    MutableLiveData<GetPlaylistsChoicesState> playlistsState = new MutableLiveData<>();
    public void getPlaylists() {
        playlistsState.postValue(GetPlaylistsChoicesState.LOADING);

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
                playlistsState.postValue(GetPlaylistsChoicesState.SUCCESS);
            }).start();
        } catch (Exception e) {
            Log.e("PlaylistsViewModel", "Error getting playlists", e);
            playlistsState.postValue(GetPlaylistsChoicesState.ERROR);
        }
    }

    public void addSongToPlaylist(PlaylistModel playlist, SongModel song) {
        try {
            new Thread(() -> {
                PlaylistRepository playlistRepository = new PlaylistRepositoryImpl();
                AddSongToPlaylistUseCase.execute(playlistRepository, playlist, song);
            }).start();
        } catch (Exception e) {
            Log.e("PlaylistsViewModel", "Error adding song to playlist", e);
        }
    }
}

enum GetPlaylistsChoicesState {
    LOADING,
    SUCCESS,
    ERROR
}