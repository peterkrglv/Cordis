package com.example.cordis.ui.playlists;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cordis.databinding.FragmentPlaylistsBinding;
import com.example.cordis.domain.playlist.PlaylistModel;
import com.example.cordis.domain.song.SongModel;

import java.util.ArrayList;
import java.util.List;

public class PlaylistsFragment extends Fragment {
    FragmentPlaylistsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlaylistsBinding.inflate(inflater, container, false);


        //random list with a few playlist models
        List<PlaylistModel> playlists = new ArrayList<>();
        playlists.add(new PlaylistModel("Playlist 1", "Owner 1", "Description 1", null));
        playlists.add(new PlaylistModel("Playlist 2", "Owner 2", "Description 2", null));
        playlists.add(new PlaylistModel("Playlist 3", "Owner 3", "Description 3", null));
        playlists.add(new PlaylistModel("Playlist 4", "Owner 4", "Description 4", null));

        //random songs(songModel: private String songName;
        //    private String songArtist;
        //    private String tuning;
        //    private String capo;
        //    private String chords;
        //    private Bitmap songImage;
        //    private String owner;) in the playlist1
        List<SongModel> songs = new ArrayList<>();
        songs.add(new SongModel("Song 1", "Artist 1", "Tuning 1", "Capo 1", "Chords 1", null, "Owner 1"));
        songs.add(new SongModel("Song 2", "Artist 2", "Tuning 2", "Capo 2", "Chords 2", null, "Owner 2"));
        songs.add(new SongModel("Song 3", "Artist 3", "Tuning 3", "Capo 3", "Chords 3", null, "Owner 3"));
        songs.add(new SongModel("Song 4", "Artist 4", "Tuning 4", "Capo 4", "Chords 4", null, "Owner 4"));
        playlists.get(0).setSongs(songs);
        PlaylistsAdapter playlistsAdapter = new PlaylistsAdapter(playlists, binding.getRoot());
        playlistsAdapter.setOnPlaylistClickListener(new PlaylistsAdapter.OnPlaylistClickListener() {
            @Override
            public void onPlaylistClick(PlaylistModel playlist) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("playlist", playlist);
                findNavController(binding.getRoot()).navigate(com.example.cordis.R.id.action_playlistsFragment_to_songsInPlaylistFragment, bundle);
            }
        });

        binding.playlistsRecyclerView.setAdapter(playlistsAdapter);
        binding.playlistsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        return binding.getRoot();
    }
}
