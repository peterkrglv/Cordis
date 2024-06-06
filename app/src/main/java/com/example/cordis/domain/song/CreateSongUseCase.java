package com.example.cordis.domain.song;

import android.util.Log;

import com.example.cordis.domain.ImageRepository;
import com.example.cordis.domain.user.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateSongUseCase {
    public static Boolean execute(
            SongModel song,
            SongRepository songRepository,
            UserRepository userRepository,
            ImageRepository imageRepository) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String songId = db.collection("songs").document().getId();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String username;
        song.setSongId(songId);
        song.setOwner(uid);
        SongItem songItem = new SongItem(song);
        if(songRepository.createSong(songItem)) {
            if (song.getSongImage() != null) return imageRepository.uploadImage(song.getSongImage(), songId, "songImages");
            else return true;
        } else {
            Log.e("CreateSongViewModel", "createSong: error");
            return false;
        }
    }
}
