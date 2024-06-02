package com.example.cordis.ui.songs_in_playlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cordis.databinding.ItemSongBinding;
import com.example.cordis.domain.song.SongModel;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongsViewHolder> {
    List<SongModel> songs;
    private onSongClickListener onSongClickListener;

    public interface onSongClickListener {
        void onSongClick(SongModel playlist);

    }

    public void setSongs(List<SongModel> songs) {
        this.songs = songs;
    }

    public void setOnSongClickListener(SongsAdapter.onSongClickListener onSongClickListener) {
        this.onSongClickListener = onSongClickListener;
    }

    public SongsAdapter(List<SongModel> songs) {
        this.songs = songs;
    }

    class SongsViewHolder extends RecyclerView.ViewHolder {
        ItemSongBinding binding;

        public SongsViewHolder(ItemSongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public SongsAdapter.SongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SongsAdapter.SongsViewHolder(
                ItemSongBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(SongsAdapter.SongsViewHolder holder, int position) {
        ItemSongBinding binding = ItemSongBinding.bind(holder.itemView);
        SongModel song = songs.get(position);
        binding.songName.setText(song.getSongName());
        binding.songArtist.setText(song.getSongArtist());
        //binding.songImage.setImageBitmap(song.getSongImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSongClickListener != null) {
                    onSongClickListener.onSongClick(song);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (songs == null) {
            return 0;
        } else {
            return songs.size();
        }
    }
}
