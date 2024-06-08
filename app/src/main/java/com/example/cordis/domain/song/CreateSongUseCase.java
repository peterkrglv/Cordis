package com.example.cordis.domain.song;

import android.util.Log;

import com.example.cordis.domain.ImageRepository;
import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateSongUseCase {
    public static Boolean execute(
            SongModel song,
            SongRepository songRepository,
            UserRepository userRepository,
            ImageRepository imageRepository) {
        UserModel user = userRepository.getCurrentUser();
        if (user == null) {return false;}
        String uid = user.getUid();
        song.setOwner(uid);
        SongItem songItem = new SongItem(song);
        String songId = songRepository.createSong(songItem);
        if(songId != null) {
            if (song.getSongImage() != null) return imageRepository.uploadImage(song.getSongImage(), songId, "songImages");
            else return true;
        } else {
            Log.e("CreateSongViewModel", "createSong: error");
            return false;
        }
    }
}
