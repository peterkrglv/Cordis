package com.example.cordis.domain.song;

import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;

import java.util.HashSet;
import java.util.List;

public class SetFavouritesUseCase {
    public static List<SongModel> execute(UserRepository userRepo, List<SongModel> songs) {
        UserModel user = userRepo.getCurrentUser();
        HashSet<String> favSongs = new HashSet<>(user.getFavouriteSongs());
        for (SongModel song : songs) {
            if (favSongs.contains(song.getSongId())) {
                song.setFavourite(true);
            }
        }
        return songs;
    }
}
