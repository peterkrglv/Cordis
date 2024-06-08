package com.example.cordis.ui.song_chords;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cordis.databinding.BottomSheetChoosePlaylistBinding;
import com.example.cordis.domain.playlist.PlaylistModel;
import com.example.cordis.ui.adapters.PlaylistsAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomSheetChoosePlaylist extends BottomSheetDialogFragment {

    BottomSheetChoosePlaylistBinding binding;
    PlaylistsAdapter adapter;

    public interface BottomSheetListener {
        void onButtonClicked(PlaylistModel playlist);
    }

    private BottomSheetListener mListener;

    public void setBottomSheetListener(BottomSheetListener listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        binding = BottomSheetChoosePlaylistBinding.inflate(inflater, container, false);
        adapter = new PlaylistsAdapter(new ArrayList<>(), binding.getRoot());
        binding.playlistsRecyclerView.setAdapter(adapter);
        binding.playlistsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnPlaylistClickListener(playlist -> {
            mListener.onButtonClicked(playlist);
            dismiss();
        });
        return binding.getRoot();
    }
}
