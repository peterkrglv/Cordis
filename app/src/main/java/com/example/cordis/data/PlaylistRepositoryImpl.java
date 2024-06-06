package com.example.cordis.data;

import android.util.Log;

import com.example.cordis.domain.playlist.PlaylistItem;
import com.example.cordis.domain.playlist.PlaylistModel;
import com.example.cordis.domain.playlist.PlaylistRepository;
import com.example.cordis.domain.song.SongModel;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.concurrent.ExecutionException;

public class PlaylistRepositoryImpl implements PlaylistRepository {
    @Override
    public Boolean createPlaylist(PlaylistItem playlist) {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("playlists").document(playlist.getPlaylistId()).set(playlist);
            db.collection("users").document(playlist.getPlaylistOwner())
                    .update("createdPlaylists", FieldValue.arrayUnion(playlist.getPlaylistId()));
            return true;

        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public Boolean updatePlaylist(PlaylistItem playlist) {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("playlists").document(playlist.getPlaylistId()).set(playlist);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public PlaylistItem getPlaylist(String playlistId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentSnapshot doc = null;
        try {
            doc = Tasks.await(db.collection("playlists").document(playlistId).get());
        } catch (ExecutionException | InterruptedException e) {
        }
        if (doc != null && doc.exists()) {
            return doc.toObject(PlaylistItem.class);
        } else {
            return null;
        }
    }

    @Override
    public Boolean deletePlaylist(String playlistId) {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("playlists").document(playlistId).delete();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
