package com.example.cordis.ui.create;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cordis.data.PlaylistRepositoryImpl;
import com.example.cordis.data.UserRepositoryImpl;
import com.example.cordis.domain.playlist.CreatePlaylistUseCase;
import com.example.cordis.domain.playlist.PlaylistModel;
import com.example.cordis.domain.playlist.PlaylistRepository;
import com.example.cordis.domain.user.GetUserUseCase;
import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.index.qual.EnsuresLTLengthOf;

public class CreatePlaylistViewModel extends ViewModel {
    MutableLiveData<CreatePlaylistState> createPlaylistState = new MutableLiveData<>();

    public void createPlaylist(String playlistName, String playlistDescription) {
        createPlaylistState.postValue(CreatePlaylistState.LOADING);
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                PlaylistRepository playlistRepository = new PlaylistRepositoryImpl();
                UserRepository userRepository = new UserRepositoryImpl();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String playlistId = db.collection("playlists").document().getId();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String username = "";       //возможно это костыль
                if (GetUserUseCase.execute(uid, userRepository) != null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    UserModel user = userRepository.getUser(uid);
                    username = user.getName();
                } else {
                    createPlaylistState.postValue(CreatePlaylistState.ERROR);
                }
                PlaylistModel playlist = new PlaylistModel(playlistId, playlistName, uid, username,
                        playlistDescription, null);
                if (CreatePlaylistUseCase.execute(playlist, playlistRepository)) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    createPlaylistState.postValue(CreatePlaylistState.SUCCESS);
                } else {
                    createPlaylistState.postValue(CreatePlaylistState.ERROR);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}

enum CreatePlaylistState {
    LOADING,
    SUCCESS,
    ERROR
}