package com.example.cordis.ui.chords;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cordis.R;
import com.example.cordis.databinding.FragmentSongChordsBinding;
import com.example.cordis.databinding.FragmentSongsInPlaylistBinding;
import com.example.cordis.domain.song.SongModel;


public class SongChordsFragment extends Fragment {

    FragmentSongChordsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSongChordsBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            SongModel song = bundle.getParcelable("song");
            binding.songName.setText(song.getSongName());
            binding.songArtist.setText(song.getSongArtist());
            binding.tuning.setText(song.getTuning());
            binding.capo.setText(song.getCapo());
            binding.chords.setText(song.getChords());
            binding.songImage.setImageBitmap(song.getSongImage());
        }


        return binding.getRoot();
    }
}