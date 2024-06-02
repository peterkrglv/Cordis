package com.example.cordis.ui.playlists;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cordis.data.PlaylistRepositoryImpl;
import com.example.cordis.data.UserRepositoryImpl;
import com.example.cordis.domain.playlist.GetCreatedPlaylistsUseCase;
import com.example.cordis.domain.playlist.PlaylistModel;
import com.example.cordis.domain.playlist.PlaylistRepository;
import com.example.cordis.domain.user.UserRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class PlaylistsViewModel extends ViewModel {
    MutableLiveData<List<PlaylistModel>> createdPlaylists = new MutableLiveData<>();
    MutableLiveData<GetPlaylistsState> getPlaylistsState = new MutableLiveData<>();

    public void getPlaylists() {
        getPlaylistsState.postValue(GetPlaylistsState.LOADING);

        try {
            new Thread(() -> {
                UserRepository userRepository = new UserRepositoryImpl();
                PlaylistRepository playlistRepository = new PlaylistRepositoryImpl();
                String uid = FirebaseAuth.getInstance().getUid();
                List<PlaylistModel> playlists = GetCreatedPlaylistsUseCase.execute(
                        uid, userRepository, playlistRepository);
                createdPlaylists.postValue(playlists);
                getPlaylistsState.postValue(GetPlaylistsState.SUCCESS);
            }).start();
        } catch (Exception e) {
            Log.d("tagat", "AAAAAAAA BLYAT");
            getPlaylistsState.postValue(GetPlaylistsState.ERROR);
        }
    }
}

enum GetPlaylistsState {
    LOADING,
    SUCCESS,
    ERROR
}

