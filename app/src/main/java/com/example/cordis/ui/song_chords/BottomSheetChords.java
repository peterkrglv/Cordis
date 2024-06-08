package com.example.cordis.ui.song_chords;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.cordis.databinding.BottomSheetChordsBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetChords extends BottomSheetDialogFragment {

    BottomSheetChordsBinding binding;

    public interface BottomSheetListener {
        void onButtonClicked();
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

        binding = BottomSheetChordsBinding.inflate(inflater, container, false);
        binding.addToPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked();
                dismiss();
            }
        });
        return binding.getRoot();
    }
}
