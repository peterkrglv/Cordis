package com.example.cordis.domain.song;

public class GetSongUseCase {
    public static SongModel execute(String songId, SongRepository songRepository) {
        return songRepository.getSong(songId);
    }
}
