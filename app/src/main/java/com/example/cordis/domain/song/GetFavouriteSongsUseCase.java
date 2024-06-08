package com.example.cordis.domain.song;

import com.example.cordis.domain.ImageRepository;
import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class GetFavouriteSongsUseCase {
    public static List<SongModel> execute(
            UserRepository userRepo,
            SongRepository songsRepo,
            ImageRepository imageRepository) {
        UserModel user = userRepo.getCurrentUser();
        List<String> songsIds = user.getFavouriteSongs();
        List<SongModel> songModels = new ArrayList<>();
        for (String song: songsIds) {
            SongModel songModel = GetSongUseCase.execute(song, songsRepo, imageRepository);
            songModel.setFavourite(true);
            songModels.add(songModel);
        }
        return songModels;
    }
}
