package com.example.cordis.domain.song;

public class CreateSongUseCase {
    public static Boolean execute(SongModel song, SongRepository songRepository) {

        return songRepository.createSong(song);
    }
}
