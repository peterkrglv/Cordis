package com.example.cordis.domain.song;

public class UpdateUserUseCase {
    public static Boolean execute(SongModel song, SongRepository songRepository) {
        return songRepository.updateSong(new SongItem(song));
    }
}
