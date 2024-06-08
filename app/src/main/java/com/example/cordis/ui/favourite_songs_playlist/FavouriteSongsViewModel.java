package com.example.cordis.ui.favourite_songs_playlist;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cordis.data.ImageRepositoryImpl;
import com.example.cordis.data.SongRepositoryImpl;
import com.example.cordis.data.UserRepositoryImpl;
import com.example.cordis.domain.ImageRepository;
import com.example.cordis.domain.song.FavouriteSongUseCase;
import com.example.cordis.domain.song.GetFavouriteSongsUseCase;
import com.example.cordis.domain.song.SongModel;
import com.example.cordis.domain.song.SongRepository;
import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;

import java.util.List;

public class FavouriteSongsViewModel extends ViewModel {

    MutableLiveData<List<SongModel>> favouriteSongs = new MutableLiveData<>();
    MutableLiveData<GetFavouriteSongsState> favouriteSongsState = new MutableLiveData<>();
    MutableLiveData<UserModel> currentUser = new MutableLiveData<>();
    MutableLiveData<FavouriteSongState> favouriteSongState = new MutableLiveData<>();
    public void getFavouriteSongs() {
        favouriteSongsState.postValue(GetFavouriteSongsState.LOADING);

        try {
            new Thread(() -> {
                UserRepository userRepository = new UserRepositoryImpl();
                SongRepository songRepository = new SongRepositoryImpl();
                ImageRepository imageRepository = new ImageRepositoryImpl();
                List<SongModel> songs = GetFavouriteSongsUseCase.execute(
                        userRepository,
                        songRepository,
                        imageRepository);
                favouriteSongs.postValue(songs);
                favouriteSongsState.postValue(GetFavouriteSongsState.SUCCESS);
            }).start();
        } catch (Exception e) {
            Log.d("tagat", "AAAAAAAA BLYAT");
            favouriteSongsState.postValue(GetFavouriteSongsState.ERROR);
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


enum GetFavouriteSongsState {
    LOADING,
    SUCCESS,
    ERROR
}

enum FavouriteSongState {
    LOADING,
    SUCCESS,
    ERROR
}