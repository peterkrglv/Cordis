package com.example.cordis.domain.song;

import java.util.List;

public interface SongRepository {
    String createSong(SongItem song);
    Boolean updateSong(SongItem song);
    SongItem getSong(String songId);
    Boolean deleteSong(String songId);

    List<SongItem> getAllSongs();
}
