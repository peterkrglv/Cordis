package com.example.cordis.ui.create;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cordis.data.SongRepositoryImpl;
import com.example.cordis.domain.song.CreateSongUseCase;
import com.example.cordis.domain.song.SongModel;
import com.example.cordis.domain.song.SongRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateSongViewModel extends ViewModel {
    MutableLiveData<CreateSongState> editSongState = new MutableLiveData<>();
    public void editSong(String songName, String songArtist, String tuning, String capo, String chords) {
        editSongState.postValue(CreateSongState.LOADING);
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                SongRepository songRepository = new SongRepositoryImpl();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String songId = db.collection("songs").document().getId();
                SongModel song = new SongModel(songId, songName, songArtist, tuning, capo, chords, null, FirebaseAuth.getInstance().getCurrentUser().getUid());
                if (CreateSongUseCase.execute(song, songRepository)) {
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

        }
        ).start();
    }
}


enum CreateSongState {
    LOADING,
    SUCCESS,
    ERROR
}
