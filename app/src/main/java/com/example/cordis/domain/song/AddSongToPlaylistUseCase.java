package com.example.cordis.domain.song;

import com.example.cordis.domain.playlist.PlaylistItem;
import com.example.cordis.domain.playlist.PlaylistModel;
import com.example.cordis.domain.playlist.PlaylistRepository;

import java.util.List;

public class AddSongToPlaylistUseCase {
    public static void execute(PlaylistRepository playlistRepository, PlaylistModel playlist, SongModel song) {
        List<String> songs = playlist.getSongs();
        songs.add(song.getSongId());
        playlist.setSongs(songs);
        PlaylistItem playlistItem = new PlaylistItem(playlist);
        playlistRepository.updatePlaylist(playlistItem);
    }
}
