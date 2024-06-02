package com.example.cordis.ui.playlists;

import static androidx.navigation.Navigation.findNavController;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cordis.R;
import com.example.cordis.databinding.FragmentPlaylistsBinding;
import com.example.cordis.domain.playlist.PlaylistModel;

import java.util.ArrayList;

public class PlaylistsFragment extends Fragment {
    FragmentPlaylistsBinding binding;
    PlaylistsViewModel viewModel;
    PlaylistsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlaylistsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(PlaylistsViewModel.class);
        viewModel.getPlaylistsState.observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case LOADING:
                    binding.blockingView.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    binding.blockingView.setVisibility(View.GONE);
                    binding.progressBar.setVisibility(View.GONE);
                    break;
                case ERROR:
                    binding.blockingView.setVisibility(View.GONE);
                    binding.progressBar.setVisibility(View.GONE);
                    break;
            }
        });

        viewModel.createdPlaylists.observe(getViewLifecycleOwner(), obtainedPlaylists -> {
            Log.d("PlaylistsFragment", "Obtained playlists: " + obtainedPlaylists.size());
            adapter.setPlaylists(obtainedPlaylists);
            adapter.notifyDataSetChanged();
        });

        viewModel.getPlaylists();

        adapter = new PlaylistsAdapter(new ArrayList<>(), binding.getRoot());
        binding.playlistsRecyclerView.setAdapter(adapter);
        binding.playlistsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnPlaylistClickListener(new PlaylistsAdapter.OnPlaylistClickListener() {
            @Override
            public void onPlaylistClick(PlaylistModel playlist) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("playlist", playlist);
                findNavController(binding.getRoot()).navigate(com.example.cordis.R.id.action_playlistsFragment_to_songsInPlaylistFragment, bundle);
            }
        });

        binding.createdSongsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(binding.getRoot()).navigate(com.example.cordis.R.id.action_playlistsFragment_to_createdSongsFragment);
            }
        });

        binding.favouritesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findNavController(binding.getRoot()).navigate(R.id.action_playlistsFragment_to_favouritesFragment);
            }
        });

        setUpAddButton();

        return binding.getRoot();
    }

    private void setUpAddButton() {
        binding.addPlaylist.setVisibility(View.INVISIBLE);
        binding.addSong.setVisibility(View.INVISIBLE);
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.addPlaylist.getVisibility() == View.INVISIBLE) {
                    binding.addPlaylist.setVisibility(View.VISIBLE);
                    binding.addSong.setVisibility(View.VISIBLE);
                } else {
                    binding.addPlaylist.setVisibility(View.INVISIBLE);
                    binding.addSong.setVisibility(View.INVISIBLE);
                }
            }
        });

        binding.addSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(binding.getRoot()).navigate(com.example.cordis.R.id.action_playlistsFragment_to_editSongFragment);
            }
        });

        binding.addPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(binding.getRoot()).navigate(com.example.cordis.R.id.action_playlistsFragment_to_createPlaylistFragment);
            }
        });
    }
}

