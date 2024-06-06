package com.example.cordis.ui.create;

import static androidx.navigation.Navigation.findNavController;

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
import com.example.cordis.databinding.FragmentCreateSongBinding;


public class CreateSongFragment extends Fragment {
    FragmentCreateSongBinding binding;
    CreateSongViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateSongBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CreateSongViewModel.class);
        viewModel.editSongState.observe(getViewLifecycleOwner(), editSongState -> {
            switch (editSongState) {
                case LOADING:
                    binding.blockingView.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    findNavController(binding.getRoot()).navigate(R.id.action_editSongFragment_to_playlistsFragment);
                    break;
                case ERROR:
                    binding.blockingView.setVisibility(View.GONE);
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        viewModel.songImage.observe(getViewLifecycleOwner(), bitmap -> {
            binding.songImage.setImageBitmap(bitmap);
        });

        binding.songImage.setOnClickListener(v -> {
            mGetContent.launch("image/*");
        });

        binding.buttonCreate.setOnClickListener(v -> {
            viewModel.editSong(binding.songName.getText().toString(), binding.songArtist.getText().toString(), binding.tuning.getText().toString(), binding.capo.getText().toString(), binding.chords.getText().toString());
            findNavController(v).navigate(R.id.action_editSongFragment_to_playlistsFragment);
        });

        binding.buttonCancel.setOnClickListener(v -> {
            findNavController(v).navigate(R.id.action_editSongFragment_to_playlistsFragment);
        });



        return binding.getRoot();
    }

    private final ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if (uri != null) {
                        viewModel.setSongImage(getContext(), uri);
                    }
                }
            }
    );
}