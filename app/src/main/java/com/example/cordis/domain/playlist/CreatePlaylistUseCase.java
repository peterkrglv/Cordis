package com.example.cordis.domain.playlist;

import android.util.Log;

import com.example.cordis.domain.ImageRepository;
import com.example.cordis.domain.user.GetUserUseCase;
import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreatePlaylistUseCase {
    public static boolean execute(
            PlaylistModel playlist,
            PlaylistRepository playlistRepository,
            UserRepository userRepository,
            ImageRepository imageRepository) {
        //Я понимаю, что тут нельзя обращаться к БД
        //Но если в этом месте не задать id
        //То загрузку картинки придется осуществлять после добавления плейлиста в отдельном useCase
        //метод создания плейлиста возвращает id
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String playlistId = db.collection("playlists").document().getId();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String username;
        UserModel user = GetUserUseCase.execute(uid, userRepository);
        if (user != null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            username = user.getName();
        } else {
            Log.e("CreatePlaylistViewModel", "User not found");
            return false;
        }
        playlist.setPlaylistId(playlistId);
        playlist.setPlaylistOwner(uid);
        playlist.setPlaylistOwnerName(username);
        PlaylistItem playlistItem = new PlaylistItem(playlist);
        if(playlistRepository.createPlaylist(playlistItem)) {
            if (playlist.getPlaylistImage() != null) {
                return imageRepository.uploadImage(
                        playlist.getPlaylistImage(),
                        playlistId,
                        "playlistImages");
            }
            else return true;
        } else {
            return false;
        }
    }
}
