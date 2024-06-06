package com.example.cordis.domain.playlist;

import com.example.cordis.domain.ImageRepository;

public class GetPlaylistUseCase {
    public static PlaylistModel execute(
            String playlistId,
            PlaylistRepository playlistRepository,
            ImageRepository imageRepository) {
        PlaylistItem playlistItem =  playlistRepository.getPlaylist(playlistId);
        byte[] image = imageRepository.downloadImage(playlistItem.getPlaylistId(), "playlistImages");
        PlaylistModel playlist = new PlaylistModel(playlistItem);
        playlist.setPlaylistImage(image);
        return playlist;
    }
}
