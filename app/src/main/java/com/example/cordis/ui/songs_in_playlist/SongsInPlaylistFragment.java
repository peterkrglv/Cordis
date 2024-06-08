package com.example.cordis.ui.songs_in_playlist;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cordis.Methods;
import com.example.cordis.R;
import com.example.cordis.databinding.FragmentSongsInPlaylistBinding;
import com.example.cordis.domain.playlist.PlaylistModel;
import com.example.cordis.domain.song.SongModel;
import com.example.cordis.ui.adapters.SongsAdapter;

import java.util.ArrayList;


public class SongsInPlaylistFragment extends Fragment {
    FragmentSongsInPlaylistBinding binding;

    SongsInPlaylistViewModel viewModel;
    SongsAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSongsInPlaylistBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SongsInPlaylistViewModel.class);

        binding.blockingView.setOnClickListener(v -> {});
        setUpCard();

        viewModel.getSongsState.observe(getViewLifecycleOwner(), songs -> {
            switch (viewModel.getSongsState.getValue()) {
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
                    Toast.makeText(getContext(), "Error while loading songs from db", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        viewModel.songs.observe(getViewLifecycleOwner(), songs -> {
            adapter.setSongs(songs);
            adapter.notifyDataSetChanged();
        });

        viewModel.getSongs((PlaylistModel) getArguments().getParcelable("playlist"));

        return binding.getRoot();
    }

    private void setUpCard() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            PlaylistModel playlist = bundle.getParcelable("playlist");
            binding.playlistName.setText(playlist.getPlaylistName());
            binding.playlistOwner.setText(playlist.getPlaylistOwnerName());
            binding.playlistDescription.setText(playlist.getPlaylistDescription());
            if (playlist.getPlaylistImage() != null) {
                binding.playlistImage.setImageBitmap(Methods.byteArrayToBitmap(playlist.getPlaylistImage()));
            }
            setUpSongsRecycler();
        }
    }

    private void setUpSongsRecycler() {
        adapter = new SongsAdapter(new ArrayList<SongModel>());
        binding.songsRecycler.setAdapter(adapter);
        binding.songsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnSongClickListener(new SongsAdapter.onSongClickListener() {
            @Override
            public void onSongClick(SongModel song) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("song", song);
                findNavController(binding.getRoot()).navigate(R.id.action_songsInPlaylistFragment_to_songChordsFragment, bundle);
            }
        });
    }
}