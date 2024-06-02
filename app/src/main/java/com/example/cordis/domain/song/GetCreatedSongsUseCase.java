package com.example.cordis.domain.song;

import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class GetCreatedSongsUseCase {
    public static List<SongModel> execute(String uid, UserRepository userRepo, SongRepository songsRepo) {
        UserModel user = userRepo.getUser(uid);
        List<String> songsIds = user.getCreatedSongs();
        List<SongModel> songModels = new ArrayList<>();
        for (String song: songsIds) {
            songModels.add(songsRepo.getSong(song));
        }
        return songModels;
    }
}
