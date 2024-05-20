package com.example.cordis.ui.songs;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cordis.R;
import com.example.cordis.databinding.FragmentSongsInPlaylistBinding;
import com.example.cordis.domain.playlist.PlaylistModel;
import com.example.cordis.domain.song.SongModel;
import com.example.cordis.ui.songs.SongsAdapter;


public class SongsInPlaylistFragment extends Fragment {
    FragmentSongsInPlaylistBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSongsInPlaylistBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            PlaylistModel playlist = bundle.getParcelable("playlist");
            binding.playlistName.setText(playlist.getPlaylistName());
            binding.playlistOwner.setText(playlist.getPlaylistOwner());
            binding.playlistDescription.setText(playlist.getPlaylistDescription());
            //binding.playlistImage.setImageBitmap(playlist.getPlaylistImage());
            //add songs to recycler
            SongsAdapter songsAdapter = new SongsAdapter(playlist.getSongs(), binding.getRoot());
            binding.songsRecycler.setAdapter(songsAdapter);
            binding.songsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            songsAdapter.setOnSongClickListener(new SongsAdapter.onSongClickListener() {
                @Override
                public void onSongClick(SongModel song) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("song", song);
                    findNavController(binding.getRoot()).navigate(R.id.action_songsInPlaylistFragment_to_songChordsFragment, bundle);
                }
            });
        }


        return binding.getRoot();
    }
}