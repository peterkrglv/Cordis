package com.example.cordis.domain.playlist;

public interface PlaylistRepository {
    Boolean createPlaylist(PlaylistModel playlist);
    Boolean updatePlaylist(PlaylistModel playlist);
    PlaylistModel getPlaylist(String playlistId);
    Boolean deletePlaylist(String playlistId);
}
