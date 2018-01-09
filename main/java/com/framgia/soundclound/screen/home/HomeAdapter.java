package com.framgia.soundclound.screen.home;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framgia.soundclound.R;
import com.framgia.soundclound.data.model.Genres;
import com.framgia.soundclound.databinding.ItemHomeGenresBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bui Danh Nam on 5/1/2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<Genres> mGenres;


    public HomeAdapter(List<Genres> genres) {
        mGenres = genres;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHomeGenresBinding itemHomeGenresBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_home_genres, parent, false);
        return new ViewHolder(itemHomeGenresBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindingdata(mGenres.get(position));
    }

    @Override
    public int getItemCount() {

        if (mGenres == null) {
            mGenres = new ArrayList<>();
        }
        return mGenres.size();
    }

    /**
     * Created by Bui Danh Nam on 5/1/2018.
     */

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemHomeGenresBinding mItemHomeGenresBinding;

        public ViewHolder(ItemHomeGenresBinding itemHomeGenresBinding) {
            super(itemHomeGenresBinding.getRoot());
            mItemHomeGenresBinding = itemHomeGenresBinding;
        }

        public void bindingdata(Genres genres) {
            mItemHomeGenresBinding.setGenres(genres);
            mItemHomeGenresBinding.executePendingBindings();
        }
    }
}
