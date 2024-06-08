package com.example.cordis.ui.song_chords;

import static androidx.navigation.Navigation.findNavController;
import static com.example.cordis.Methods.byteArrayToBitmap;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cordis.R;
import com.example.cordis.databinding.FragmentSongChordsBinding;
import com.example.cordis.domain.song.SongModel;

public class SongChordsFragment extends Fragment {

    FragmentSongChordsBinding binding;
    SongChordsViewModel viewModel;
    NavController navController;
    ObjectAnimator scroller;
    Integer fontSize = 16;
    Integer scrollSpeed = 35;
    Integer maxScrollSpeed = 10;
    Integer minScrollSpeed = 35;
    Integer deltaScrollSpeed = 5;
    SongModel song;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSongChordsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SongChordsViewModel.class);
        Bundle bundle = getArguments();

        if (bundle != null) {
            song = bundle.getParcelable("song");
            binding.songName.setText(song.getSongName());
            binding.songArtist.setText(song.getSongArtist());
            binding.tuningText.setText(song.getTuning());
            binding.capoText.setText(song.getCapo());
            binding.chords.setText(song.getChords());
            binding.songImage.setImageBitmap(byteArrayToBitmap(song.getSongImage()));
        }
        binding.chords.setTextSize(fontSize);

        setUpFontChange();
        setUpScrolling();
        setUpMoreOptions();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = findNavController(view);
    }

    private void setUpMoreOptions() {
        binding.moreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetChords bottomSheetDialog = new BottomSheetChords();
                bottomSheetDialog.setBottomSheetListener(() -> {
                    bottomSheetDialog.dismiss();
                    setUpPlaylistChoice();
                });
                bottomSheetDialog.show(getParentFragmentManager(), "Tag");
            }
        });
    }

    private void setUpPlaylistChoice() {
        BottomSheetChoosePlaylist choosePlaylistDialog = new BottomSheetChoosePlaylist();

        viewModel.getPlaylists();
        viewModel.createdPlaylists.observe(getViewLifecycleOwner(), playlists -> {
            choosePlaylistDialog.adapter.setPlaylists(playlists);
            choosePlaylistDialog.adapter.notifyDataSetChanged();
        });

        choosePlaylistDialog.setBottomSheetListener(playlist -> {
            viewModel.addSongToPlaylist(playlist, song);
            choosePlaylistDialog.dismiss();
        });
        choosePlaylistDialog.show(getParentFragmentManager(), "Tag");
    }

    private void setUpScrolling() {
        binding.nestedScrollView.post(new Runnable() {

            @Override
            public void run() {
                int bottomOfPage = binding.nestedScrollView.getChildAt(0).getHeight();

                binding.scrollPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (scroller != null && scroller.isRunning()) {
                            scroller.cancel();
                        }
                        if (binding.chords.getScrollY() < bottomOfPage) {
                            if (scrollSpeed > maxScrollSpeed) {
                                scrollSpeed -= deltaScrollSpeed;
                            }

                            Integer y = binding.nestedScrollView.getScrollY();
                            scroller = ObjectAnimator.ofInt(
                                    binding.nestedScrollView,
                                    "scrollY",
                                    y,
                                    bottomOfPage);
                            scroller.setDuration((bottomOfPage - y) * scrollSpeed);
                            addScrollerListener();
                            scroller.start();
                        }
                    }
                });

                binding.scrollMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        scroller.cancel();
                        if (scrollSpeed < minScrollSpeed) {
                            Integer y = binding.nestedScrollView.getScrollY();
                            scroller = ObjectAnimator.ofInt(
                                    binding.nestedScrollView,
                                    "scrollY",
                                    binding.nestedScrollView.getScrollY(),
                                    bottomOfPage);
                            scrollSpeed += deltaScrollSpeed;
                            scroller.setDuration((bottomOfPage - y) * scrollSpeed);
                            addScrollerListener();
                            scroller.start();
                        }
                    }
                });

                binding.scrollText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (scroller.isRunning()) {
                            scroller.cancel();
                            scrollSpeed = minScrollSpeed;
                        }
                    }
                });
            }
        });
    }

    private void addScrollerListener() {
        scroller.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                binding.scrollText.setText(getResources().getString(R.string.stop));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                binding.scrollText.setText(getResources().getString(R.string.scroll));
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                binding.scrollText.setText(getResources().getString(R.string.scroll));
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    private void setUpFontChange() {
        binding.fontMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fontSize > 6) {
                    fontSize -= 2;
                    binding.chords.setTextSize(fontSize);
                }
            }
        });

        binding.fontPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fontSize < 30) {
                    fontSize += 2;
                    binding.chords.setTextSize(fontSize);
                }
            }
        });
    }
}