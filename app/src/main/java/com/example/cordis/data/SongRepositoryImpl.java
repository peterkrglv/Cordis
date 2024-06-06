package com.example.cordis.data;

import com.example.cordis.domain.song.SongItem;
import com.example.cordis.domain.song.SongRepository;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SongRepositoryImpl implements SongRepository {
    @Override
    public Boolean createSong(SongItem song) {
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
    public Boolean updateSong(SongItem song) {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("songs").document(song.getSongId()).set(song);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public SongItem getSong(String songId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentSnapshot doc = null;
        try {
            doc = Tasks.await(db.collection("songs").document(songId).get());
        } catch (ExecutionException | InterruptedException e) {
        }
        if (doc != null && doc.exists()) {
            return doc.toObject(SongItem.class);
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

    @Override
    public List<SongItem> getAllSongs() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<SongItem> songs = new ArrayList<>();
        try {
            List<DocumentSnapshot> docs = Tasks.await(db.collection("songs").get()).getDocuments();
            for (DocumentSnapshot doc : docs) {
                songs.add(doc.toObject(SongItem.class));
            }
        } catch (ExecutionException | InterruptedException e) {
        }
        return songs;
    }
}