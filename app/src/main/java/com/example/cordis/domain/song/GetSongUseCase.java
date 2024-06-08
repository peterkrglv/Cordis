package com.example.cordis.domain.song;

import com.example.cordis.domain.ImageRepository;

public class GetSongUseCase {
    public static SongModel execute(
            String songId,
            SongRepository songRepository,
            ImageRepository imageRepository) {
        SongItem songItem = songRepository.getSong(songId);
        byte[] image = imageRepository.downloadImage(songItem.getSongId(), "songImages");
        SongModel songModel = new SongModel(songItem);
        songModel.setSongImage(image);
        return songModel;
    }
}
