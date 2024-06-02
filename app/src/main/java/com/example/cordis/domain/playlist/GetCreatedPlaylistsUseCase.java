package com.example.cordis.domain.playlist;

import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class GetCreatedPlaylistsUseCase {
    public static List<PlaylistModel> execute(String uid, UserRepository userRepository, PlaylistRepository playlistRepository) {
        UserModel user = userRepository.getUser(uid);
        List<String> playlistIds = user.getCreatedPlaylists();
        List<PlaylistModel> playlists = new ArrayList<>();
        for (String playlist: playlistIds) {
            playlists.add(playlistRepository.getPlaylist(playlist));
        }
        return playlists;
    }
}
