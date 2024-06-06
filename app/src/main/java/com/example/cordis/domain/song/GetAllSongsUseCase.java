package com.example.cordis.domain.song;

import com.example.cordis.domain.ImageRepository;

import java.util.ArrayList;
import java.util.List;

public class GetAllSongsUseCase {
    public static List<SongModel> execute(SongRepository songRepository, ImageRepository imageRepository) {
        List<SongItem> songsItems = songRepository.getAllSongs();
        List<SongModel> songs = new ArrayList<>();
        for (SongItem songItem : songsItems) {
            byte[] image = imageRepository.downloadImage(songItem.getSongId(), "songImages");
            SongModel songModel = new SongModel(songItem);
            songModel.setSongImage(image);
            songs.add(songModel);
        }
        return songs;
    }
}
