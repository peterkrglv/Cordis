package com.example.cordis.ui.songs_in_playlist;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cordis.data.SongRepositoryImpl;
import com.example.cordis.data.UserRepositoryImpl;
import com.example.cordis.domain.song.GetCreatedSongsUseCase;
import com.example.cordis.domain.song.SongModel;
import com.example.cordis.domain.song.SongRepository;
import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class CreatedSongsViewModel extends ViewModel {

    MutableLiveData<List<SongModel>> createdSongs = new MutableLiveData<>();
    MutableLiveData<GetCreatedSongsState> createdSongsState = new MutableLiveData<>();
    MutableLiveData<UserModel> currentUser = new MutableLiveData<>();

    public void getCreatedSongs() {
        createdSongsState.postValue(GetCreatedSongsState.LOADING);

        try {
            new Thread(() -> {
                UserRepository userRepository = new UserRepositoryImpl();
                SongRepository songRepository = new SongRepositoryImpl();
                String uid = FirebaseAuth.getInstance().getUid();
                List<SongModel> songs = GetCreatedSongsUseCase.execute(uid, userRepository, songRepository);
                createdSongs.postValue(songs);
                createdSongsState.postValue(GetCreatedSongsState.SUCCESS);
            }).start();
        } catch (Exception e) {
            Log.d("tagat", "AAAAAAAA BLYAT");
            createdSongsState.postValue(GetCreatedSongsState.ERROR);
        }
    }
}


enum GetCreatedSongsState {
    LOADING,
    SUCCESS,
    ERROR
}