package com.example.cordis.ui.playlists;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.cordis.R;
import com.example.cordis.databinding.FragmentPlaylistsBinding;
import com.example.cordis.domain.playlist.PlaylistModel;
import com.example.cordis.ui.adapters.PlaylistsAdapter;
import com.example.cordis.ui.adapters.SongsAdapter;

import java.util.ArrayList;

public class PlaylistsFragment extends Fragment {
    FragmentPlaylistsBinding binding;
    PlaylistsViewModel viewModel;
    PlaylistsAdapter adapter;

    SongsAdapter searchAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlaylistsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(PlaylistsViewModel.class);

        viewModel.playlistsState.observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case LOADING:
                    binding.blockingView.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.addButton.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    binding.blockingView.setVisibility(View.GONE);
                    binding.progressBar.setVisibility(View.GONE);
                    binding.addButton.setVisibility(View.VISIBLE);
                    break;
                case ERROR:
                    binding.blockingView.setVisibility(View.GONE);
                    binding.progressBar.setVisibility(View.GONE);
                    break;
            }
        });

        binding.blockingView.setOnClickListener(v -> {});

        viewModel.createdPlaylists.observe(getViewLifecycleOwner(), obtainedPlaylists -> {
            Log.d("PlaylistsFragment", "Obtained playlists: " + obtainedPlaylists.size());
            adapter.setPlaylists(obtainedPlaylists);
            adapter.notifyDataSetChanged();
        });

        viewModel.songs.observe(getViewLifecycleOwner(), songs -> {
            Log.d("PlaylistsFragment", "Obtained songs: " + songs.size());
            searchAdapter.setSongs(songs);
            searchAdapter.notifyDataSetChanged();
        });

        viewModel.getPlaylists();
        viewModel.getAllSongs();

        setUpPlaylistsAdapter();
        setUpCards();
        setUpAddButton();
        setUpSearch();

        return binding.getRoot();
    }

    private void setUpCards() {
        binding.favouritesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(binding.getRoot()).navigate(R.id.action_playlistsFragment_to_favouriteSongsFragment);
            }
        });

        binding.createdSongsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(binding.getRoot()).navigate(com.example.cordis.R.id.action_playlistsFragment_to_createdSongsFragment);
            }
        });
    }

    private void setUpPlaylistsAdapter() {
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
    }

    private void setUpSearch() {
        searchAdapter = new SongsAdapter(new ArrayList<>());
        binding.searchRecyclerView.setAdapter(searchAdapter);
        binding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.playlistsRecyclerView.setVisibility(View.GONE);
                binding.searchRecyclerView.setVisibility(View.VISIBLE);
                binding.favouritesCard.setVisibility(View.GONE);
                binding.createdSongsCard.setVisibility(View.GONE);
                binding.addButton.setVisibility(View.GONE);
                binding.addPlaylist.setVisibility(View.GONE);
                binding.addSong.setVisibility(View.GONE);
            }
        });

        binding.searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                binding.searchRecyclerView.setVisibility(View.GONE);
                binding.playlistsRecyclerView.setVisibility(View.VISIBLE);
                binding.favouritesCard.setVisibility(View.VISIBLE);
                binding.createdSongsCard.setVisibility(View.VISIBLE);
                binding.addButton.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }

    private void setUpAddButton() {
        binding.addPlaylist.setVisibility(View.GONE);
        binding.addSong.setVisibility(View.GONE);
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.addPlaylist.getVisibility() == View.GONE) {
                    binding.addPlaylist.setVisibility(View.VISIBLE);
                    binding.addSong.setVisibility(View.VISIBLE);
                } else {
                    binding.addPlaylist.setVisibility(View.GONE);
                    binding.addSong.setVisibility(View.GONE);
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

