package com.example.cordis.domain.song;

import java.util.List;

public interface SongRepository {
    Boolean createSong(SongModel song);
    Boolean updateSong(SongModel song);
    SongModel getSong(String songId);
    Boolean deleteSong(String songId);
}
