package com.example.cordis.domain.song;

public class GetSongUseCase {
    public static SongModel execute(String songId, SongRepository songRepository) {
        SongItem songItem = songRepository.getSong(songId);
        return new SongModel(songItem);
    }
}
