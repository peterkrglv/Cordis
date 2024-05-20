package com.example.cordis.ui.playlists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cordis.databinding.ItemPlaylistBinding;
import com.example.cordis.domain.playlist.PlaylistModel;

import java.util.List;

public class PlaylistsAdapter extends RecyclerView.Adapter<PlaylistsAdapter.PlaylistsViewHolder> {
    List<PlaylistModel> playlists;
    View view;
    private OnPlaylistClickListener onPlaylistClickListener;

    public interface OnPlaylistClickListener {
        void onPlaylistClick(PlaylistModel playlist);

    }

    public void setOnPlaylistClickListener(OnPlaylistClickListener onPlaylistClickListener) {
        this.onPlaylistClickListener = onPlaylistClickListener;
    }

    public PlaylistsAdapter(List<PlaylistModel> playlists, View view) {
        this.playlists = playlists;
        this.view = view;
    }

    class PlaylistsViewHolder extends RecyclerView.ViewHolder {
        ItemPlaylistBinding binding;

        public PlaylistsViewHolder(ItemPlaylistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public PlaylistsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlaylistsViewHolder(
                ItemPlaylistBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                ));
    }

    @Override
    public void onBindViewHolder(PlaylistsViewHolder holder, int position) {
        PlaylistModel playlist = playlists.get(position);
        holder.binding.playlistName.setText(playlist.getPlaylistName());
        holder.binding.playlistDescription.setText(playlist.getPlaylistDescription());
        //binding.playlistImage.setImageBitmap(playlist.getPlaylistImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPlaylistClickListener != null) {
                    onPlaylistClickListener.onPlaylistClick(playlist);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return playlists.size();
    }

}
