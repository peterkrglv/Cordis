package com.example.cordis.ui.create;

import static com.example.cordis.Methods.bitmapToByteArray;
import static com.example.cordis.Methods.makeImageSquare;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cordis.data.ImageRepositoryImpl;
import com.example.cordis.data.SongRepositoryImpl;
import com.example.cordis.data.UserRepositoryImpl;
import com.example.cordis.domain.ImageRepository;
import com.example.cordis.domain.song.CreateSongUseCase;
import com.example.cordis.domain.song.SongModel;
import com.example.cordis.domain.song.SongRepository;
import com.example.cordis.domain.user.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateSongViewModel extends ViewModel {
    MutableLiveData<CreateSongState> editSongState = new MutableLiveData<>();
    MutableLiveData<Bitmap> songImage = new MutableLiveData<>();

    public void editSong(String songName, String songArtist, String tuning, String capo, String chords) {
        editSongState.postValue(CreateSongState.LOADING);
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                SongRepository songRepository = new SongRepositoryImpl();
                ImageRepository imageRepository = new ImageRepositoryImpl();
                UserRepository userRepository = new UserRepositoryImpl();
                SongModel song = new SongModel(
                        null, songName, songArtist, tuning, capo, chords,
                        bitmapToByteArray(songImage.getValue()),
                        FirebaseAuth.getInstance().getCurrentUser().getUid());
                if (CreateSongUseCase.execute(song, songRepository, userRepository, imageRepository)) {
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    editSongState.postValue(CreateSongState.SUCCESS);
                } else {
                    editSongState.postValue(CreateSongState.ERROR);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }).start();
    }

    public void setSongImage(Context context, Uri image) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Bitmap squareImage = makeImageSquare(context, image);
                songImage.postValue(squareImage);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}


enum CreateSongState {
    LOADING,
    SUCCESS,
    ERROR
}
