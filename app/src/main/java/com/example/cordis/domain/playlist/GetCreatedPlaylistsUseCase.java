package com.example.cordis.domain.playlist;

import com.example.cordis.domain.ImageRepository;
import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class GetCreatedPlaylistsUseCase {
    public static List<PlaylistModel> execute(UserRepository userRepository, PlaylistRepository playlistRepository, ImageRepository imageRepository) {
        String uid = FirebaseAuth.getInstance().getUid();
        UserModel user = userRepository.getUser(uid);
        List<String> playlistIds = user.getCreatedPlaylists();
        List<PlaylistModel> playlists = new ArrayList<>();
        for (String id: playlistIds) {
            PlaylistItem playlistItem = playlistRepository.getPlaylist(id);
            byte[] image = imageRepository.downloadImage(playlistItem.getPlaylistId(), "playlistImages");
            PlaylistModel playlist = new PlaylistModel(playlistItem);
            playlist.setPlaylistImage(image);
            playlists.add(playlist);
        }
        return playlists;
    }
}
