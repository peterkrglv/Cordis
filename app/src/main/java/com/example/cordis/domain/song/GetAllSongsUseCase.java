package com.example.cordis.domain.song;

import com.example.cordis.domain.ImageRepository;
import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class GetAllSongsUseCase {
    public static List<SongModel> execute(
            SongRepository songRepository,
            ImageRepository imageRepository,
            UserRepository userRepository
    ) {
        List<SongItem> songsItems = songRepository.getAllSongs();
        List<SongModel> songs = new ArrayList<>();
        for (SongItem songItem : songsItems) {
            SongModel songModel = new SongModel(songItem);
            byte[] image = imageRepository.downloadImage(songItem.getSongId(), "songImages");
            songModel.setSongImage(image);
            songs.add(songModel);
        }
        return SetFavouritesUseCase.execute(userRepository, songs);
    }
}
