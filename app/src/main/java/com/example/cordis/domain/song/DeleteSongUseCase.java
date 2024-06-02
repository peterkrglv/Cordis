package com.example.cordis.domain.song;

public class DeleteSongUseCase {
    public static Boolean execute(String songId, SongRepository songRepository) {
        return songRepository.deleteSong(songId);
    }
}
