package com.example.cordis.ui.create;

import static androidx.navigation.Navigation.findNavController;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cordis.R;
import com.example.cordis.databinding.FragmentCreatePlaylistBinding;

public class CreatePlaylistFragment extends Fragment {
    FragmentCreatePlaylistBinding binding;
    CreatePlaylistViewModel viewModel;
    Bitmap playlistImage;
    private static final int PICK_IMAGE_REQUEST = 1;

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
                    Toast.makeText(getContext(), "Error creating playlist", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        viewModel.playlistImage.observe(getViewLifecycleOwner(), bitmap -> {
            binding.playlistImage.setImageBitmap(bitmap);
        });


        binding.playlistImage.setOnClickListener(v -> {
            mGetContent.launch("image/*");
        });

        binding.buttonCreate.setOnClickListener(v -> {
            viewModel.createPlaylist(binding.playlistName.getText().toString(), binding.playlistDescription.getText().toString());
            //findNavController(v).navigate(R.id.action_createPlaylistFragment_to_playlistsFragment);
        });

        binding.buttonCancel.setOnClickListener(v -> {
            findNavController(v).navigate(R.id.action_createPlaylistFragment_to_playlistsFragment);
        });


        return binding.getRoot();
    }

    private final ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if (uri != null) {
                        viewModel.setPlaylistImage(getContext(), uri);
                    }
                }
            }
    );
}