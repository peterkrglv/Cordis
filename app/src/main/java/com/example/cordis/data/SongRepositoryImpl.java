package com.example.cordis.data;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.cordis.domain.song.SongModel;
import com.example.cordis.domain.song.SongRepository;
import com.example.cordis.domain.user.UserModel;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SongRepositoryImpl implements SongRepository {
    @Override
    public Boolean createSong(SongModel song) {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("songs").document(song.getSongId()).set(song);
            db.collection("users").document(song.getOwner())
                    .update("createdSongs", FieldValue.arrayUnion(song.getSongId()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean updateSong(SongModel song) {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("songs").document(song.getSongId()).set(song);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public SongModel getSong(String songId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentSnapshot doc = null;
        try {
            doc = Tasks.await(db.collection("songs").document(songId).get());
        } catch (ExecutionException | InterruptedException e) {
        }
        if (doc != null && doc.exists()) {
            return doc.toObject(SongModel.class);
        } else {
            return null;
        }
    }

    @Override
    public Boolean deleteSong(String songId) {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("songs").document(songId).delete();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}