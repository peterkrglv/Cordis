package com.example.cordis.ui.created_songs_playlist;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cordis.data.ImageRepositoryImpl;
import com.example.cordis.data.SongRepositoryImpl;
import com.example.cordis.data.UserRepositoryImpl;
import com.example.cordis.domain.ImageRepository;
import com.example.cordis.domain.song.FavouriteSongUseCase;
import com.example.cordis.domain.song.GetCreatedSongsUseCase;
import com.example.cordis.domain.song.SongModel;
import com.example.cordis.domain.song.SongRepository;
import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;

import java.util.List;

public class CreatedSongsViewModel extends ViewModel {

    MutableLiveData<List<SongModel>> createdSongs = new MutableLiveData<>();
    MutableLiveData<GetCreatedSongsState> createdSongsState = new MutableLiveData<>();
    MutableLiveData<UserModel> currentUser = new MutableLiveData<>();
    MutableLiveData<FavouriteSongState> favouriteSongState = new MutableLiveData<>();
    public void getCreatedSongs() {
        createdSongsState.postValue(GetCreatedSongsState.LOADING);

        try {
            new Thread(() -> {
                UserRepository userRepository = new UserRepositoryImpl();
                SongRepository songRepository = new SongRepositoryImpl();
                ImageRepository imageRepository = new ImageRepositoryImpl();
                List<SongModel> songs = GetCreatedSongsUseCase.execute(
                        userRepository,
                        songRepository,
                        imageRepository);
                createdSongs.postValue(songs);
                createdSongsState.postValue(GetCreatedSongsState.SUCCESS);
            }).start();
        } catch (Exception e) {
            Log.d("tagat", "AAAAAAAA BLYAT");
            createdSongsState.postValue(GetCreatedSongsState.ERROR);
        }
    }

    public void setFavouriteState(SongModel song) {
        favouriteSongState.postValue(FavouriteSongState.LOADING);
        try {
            new Thread(() -> {
                UserRepository userRepository = new UserRepositoryImpl();
                if (FavouriteSongUseCase.execute(userRepository, song)) {
                    favouriteSongState.postValue(FavouriteSongState.SUCCESS);
                } else {
                    favouriteSongState.postValue(FavouriteSongState.ERROR);
                }
            }).start();
        } catch (Exception e) {
            favouriteSongState.postValue(FavouriteSongState.ERROR);
        }
    }
}


enum GetCreatedSongsState {
    LOADING,
    SUCCESS,
    ERROR
}

enum FavouriteSongState {
    LOADING,
    SUCCESS,
    ERROR
}