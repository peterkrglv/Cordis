package com.example.cordis.domain.playlist;

public class CreatePlaylistUseCase {
    public static boolean execute(PlaylistModel playlist, PlaylistRepository playlistRepository) {
        return playlistRepository.createPlaylist(playlist);
    }
}
