package com.example.cordis.ui.created_songs_playlist;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cordis.R;
import com.example.cordis.databinding.FragmentCreatedSongsBinding;
import com.example.cordis.ui.adapters.SongsAdapter;

import java.util.ArrayList;

public class CreatedSongsFragment extends Fragment {
    FragmentCreatedSongsBinding binding;
    CreatedSongsViewModel viewModel;
    SongsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreatedSongsBinding.inflate(inflater, container, false);

        setUpAdapter();

        viewModel = new ViewModelProvider(this).get(CreatedSongsViewModel.class);
        viewModel.createdSongsState.observe(getViewLifecycleOwner(), state -> {
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
                    Log.e("CreatedSongsFragment", "Error while loadings songs from db");
                    break;
            }
        });

        viewModel.createdSongs.observe(getViewLifecycleOwner(), obtainedSongs -> {
            adapter.setSongs(obtainedSongs);
            adapter.notifyDataSetChanged();
        });
        viewModel.getCreatedSongs();

        viewModel.favouriteSongState.observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case LOADING:
                    break;
                case SUCCESS:
                    Log.d("CreatedSongsFragment", "Favourite state updated");
                    break;
                case ERROR:
                    Log.e("CreatedSongsFragment", "Error while updating favourite state");
                    break;
            }
        });

        binding.blockingView.setOnClickListener(v -> {});
        return binding.getRoot();
    }

    private void setUpAdapter() {
        adapter = new SongsAdapter(new ArrayList<>());

        adapter.setOnSongClickListener(song -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("song", song);
            findNavController(binding.getRoot()).navigate(R.id.action_createdSongsFragment_to_songChordsFragment, bundle);
        });

        adapter.setOnFavouriteClickListener(song -> {
            viewModel.setFavouriteState(song);
        });

        binding.songsRecycler.setAdapter(adapter);
        binding.songsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}