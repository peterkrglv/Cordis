package com.example.cordis.ui.create;


import static com.example.cordis.Methods.makeImageSquare;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cordis.Methods;
import com.example.cordis.data.ImageRepositoryImpl;
import com.example.cordis.data.PlaylistRepositoryImpl;
import com.example.cordis.data.UserRepositoryImpl;
import com.example.cordis.domain.ImageRepository;
import com.example.cordis.domain.playlist.CreatePlaylistUseCase;
import com.example.cordis.domain.playlist.PlaylistModel;
import com.example.cordis.domain.playlist.PlaylistRepository;
import com.example.cordis.domain.user.UserRepository;

import java.io.IOException;

public class CreatePlaylistViewModel extends ViewModel {
    MutableLiveData<CreatePlaylistState> createPlaylistState = new MutableLiveData<>();
    MutableLiveData<Bitmap> playlistImage = new MutableLiveData<>();
    public void createPlaylist(String playlistName, String playlistDescription) {
        createPlaylistState.postValue(CreatePlaylistState.LOADING);
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                PlaylistRepository playlistRepository = new PlaylistRepositoryImpl();
                UserRepository userRepository = new UserRepositoryImpl();
                ImageRepository imageRepository = new ImageRepositoryImpl();
                PlaylistModel playlist = new PlaylistModel(null,
                        playlistName,
                        null,
                        null,
                        playlistDescription,
                        Methods.bitmapToByteArray(playlistImage.getValue()));
                if (CreatePlaylistUseCase.execute(playlist, playlistRepository, userRepository, imageRepository)) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    createPlaylistState.postValue(CreatePlaylistState.SUCCESS);
                } else {
                    Log.e("CreatePlaylistViewModel", "createPlaylist: error");
                    createPlaylistState.postValue(CreatePlaylistState.ERROR);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void setPlaylistImage(Context context, Uri image) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Bitmap squareImage = makeImageSquare(context, image);
                playlistImage.postValue(squareImage);
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