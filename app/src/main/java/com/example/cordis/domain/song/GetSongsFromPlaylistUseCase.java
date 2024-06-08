package com.example.cordis.domain.song;

import com.example.cordis.domain.ImageRepository;
import com.example.cordis.domain.playlist.PlaylistModel;
import com.example.cordis.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class GetSongsFromPlaylistUseCase {
    public static List<SongModel> execute(
            PlaylistModel playlist,
            SongRepository songsRepository,
            ImageRepository imageRepository,
            UserRepository userRepository
    ) {
        List<String> songsIds = playlist.getSongs();
        List<SongModel> songModels = new ArrayList<>();
        for (String song: songsIds) {
            SongModel songModel = GetSongUseCase.execute(song, songsRepository, imageRepository);
            songModels.add(songModel);
        }
        return SetFavouritesUseCase.execute(userRepository, songModels);
    }
}
