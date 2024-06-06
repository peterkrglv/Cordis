package com.example.cordis.domain.song;

import com.example.cordis.domain.ImageRepository;
import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class GetCreatedSongsUseCase {
    public static List<SongModel> execute(UserRepository userRepo, SongRepository songsRepo, ImageRepository imageRepository) {
        String uid = FirebaseAuth.getInstance().getUid();
        UserModel user = userRepo.getUser(uid);
        List<String> songsIds = user.getCreatedSongs();
        List<SongModel> songModels = new ArrayList<>();
        for (String song: songsIds) {
            SongItem songItem = songsRepo.getSong(song);
            byte[] image = imageRepository.downloadImage(songItem.getSongId(), "songImages");
            SongModel songModel = new SongModel(songItem);
            songModel.setSongImage(image);
            songModels.add(songModel);
        }
        return songModels;
    }
}
