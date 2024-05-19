package com.example.cordis.ui.playlists;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class PlaylistsAdapter extends RecyclerView.Adapter<PlaylistsAdapter.PlaylistsViewHolder>{
    class PlaylistsViewHolder extends RecyclerView.ViewHolder {
        public PlaylistsViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public PlaylistsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PlaylistsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
