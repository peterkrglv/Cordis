package com.example.cordis.ui.create;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cordis.R;
import com.example.cordis.databinding.FragmentCreatePlaylistBinding;

public class CreatePlaylistFragment extends Fragment {
    FragmentCreatePlaylistBinding binding;
    CreatePlaylistViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreatePlaylistBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CreatePlaylistViewModel.class);
        viewModel.createPlaylistState.observe(getViewLifecycleOwner(), createPlaylistState -> {
            switch (createPlaylistState) {
                case LOADING:
                    binding.blockingView.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    findNavController(binding.getRoot()).navigate(R.id.action_createPlaylistFragment_to_playlistsFragment);
                    break;
                case ERROR:
                    binding.blockingView.setVisibility(View.GONE);
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        binding.buttonCreate.setOnClickListener(v -> {
            viewModel.createPlaylist(binding.playlistName.getText().toString(), binding.playlistDescription.getText().toString());
            findNavController(v).navigate(R.id.action_createPlaylistFragment_to_playlistsFragment);
        });

        binding.buttonCancel.setOnClickListener(v -> {
            findNavController(v).navigate(R.id.action_createPlaylistFragment_to_playlistsFragment);
        });


        return binding.getRoot();
    }
}