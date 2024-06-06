package com.example.cordis.domain.playlist;

public interface PlaylistRepository {
    Boolean createPlaylist(PlaylistItem playlist);
    Boolean updatePlaylist(PlaylistItem playlist);
    PlaylistItem getPlaylist(String playlistId);
    Boolean deletePlaylist(String playlistId);
}
