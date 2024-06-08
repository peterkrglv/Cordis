package com.example.cordis.domain.song;

import com.example.cordis.domain.user.UserModel;
import com.example.cordis.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
public class FavouriteSongUseCase {
    public synchronized static boolean execute(UserRepository userRepository, SongModel song) {
        UserModel currentUser = userRepository.getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        List<String> favouriteSongs = currentUser.getFavouriteSongs();
        if (favouriteSongs == null) {
            favouriteSongs = new ArrayList<String> ();
        }
        if (favouriteSongs.contains(song.getSongId())) {
            favouriteSongs.remove(song.getSongId());
        } else {
            favouriteSongs.add(song.getSongId());
        }
        currentUser.setFavouriteSongs(favouriteSongs);
        return userRepository.updateUser(currentUser);
    }
}
