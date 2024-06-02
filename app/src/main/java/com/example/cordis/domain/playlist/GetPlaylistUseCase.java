package com.example.cordis.domain.playlist;

public class GetPlaylistUseCase {
    public static PlaylistModel execute(String playlistId, PlaylistRepository playlistRepository) {
        return playlistRepository.getPlaylist(playlistId);
    }
}
