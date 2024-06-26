package com.example.cordis.ui.adapters;

import static com.example.cordis.Methods.byteArrayToBitmap;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cordis.R;
import com.example.cordis.databinding.ItemSongBinding;
import com.example.cordis.domain.song.SongModel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongsViewHolder> implements Filterable {
    List<SongModel> songs = new ArrayList<>();
    List<SongModel> songsAll = new ArrayList<>();
    private onSongClickListener onSongClickListener;
    private onFavouriteClickListener onFavouriteClickListener;

    public interface onSongClickListener {
        void onSongClick(SongModel playlist);

    }

    public interface onFavouriteClickListener {
        void onFavouriteClick(SongModel song);
    }

    public void setSongs(List<SongModel> songs) {

        this.songs = songs;
        this.songsAll = new ArrayList<>(songs);
    }

    public void setOnSongClickListener(SongsAdapter.onSongClickListener onSongClickListener) {
        this.onSongClickListener = onSongClickListener;
    }

    public void setOnFavouriteClickListener(SongsAdapter.onFavouriteClickListener onFavouriteClickListener) {
        this.onFavouriteClickListener = onFavouriteClickListener;
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
        if (song.getSongImage() != null) {
            binding.songImage.setImageBitmap(byteArrayToBitmap(song.getSongImage()));
        } else {
            binding.songImage.setImageResource(R.drawable.music_note);
        }
        if (song.getFavourite()) {
            binding.likeButton.setIconResource(R.drawable.favorite_filled);
        } else {
            binding.likeButton.setIconResource(R.drawable.favorite);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSongClickListener != null) {
                    onSongClickListener.onSongClick(song);
                }
            }
        });

        holder.binding.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songs.get(position).setFavourite(!songs.get(position).getFavourite());
                notifyDataSetChanged();
                //notifyItemChanged(holder.getAdapterPosition());
                if (onFavouriteClickListener != null) {
                    onFavouriteClickListener.onFavouriteClick(song);
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<SongModel> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(songsAll);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (SongModel song : songsAll) {
                        if (song.getSongName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(song);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                songs.clear();
                songs.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }
}

