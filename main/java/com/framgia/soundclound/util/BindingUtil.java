package com.framgia.soundclound.util;

import android.databinding.BindingAdapter;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.framgia.soundclound.R;
import com.framgia.soundclound.data.model.Track;
import com.framgia.soundclound.screen.main.TabType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sony on 1/4/2018.
 */

public class BindingUtil {

    @BindingAdapter({"pagerAdapter"})
    public static void setViewPagerAdapter(ViewPager viewPager, PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @BindingAdapter({"selectedColor", "unSelectedColor", "viewPager"})
    public static void setupTabIndicatorListener(final TabLayout tabLayout, final int selectedColor,
                                                 final int unSelectedColor,
                                                 final ViewPager viewPager) {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getIcon() == null) {
                    return;
                }
                tab.getIcon().setColorFilter(ContextCompat.getColor(tabLayout.getContext(),
                        selectedColor), PorterDuff.Mode.SRC_ATOP);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getIcon() == null) {
                    return;
                }
                tab.getIcon().setColorFilter(ContextCompat.getColor(tabLayout.getContext(),
                        unSelectedColor), PorterDuff.Mode.SRC_ATOP);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // noops
            }
        });
    }

    @BindingAdapter({"setTabHome"})
    public static void setUpColorFilterHome(final TabLayout tabLayout, final int selectedHome) {
        if (tabLayout.getTabAt(TabType.HOME).getIcon() != null) {
            int selectedColor = ContextCompat.getColor(tabLayout.getContext(), selectedHome);
            tabLayout.getTabAt(TabType.HOME).getIcon().setColorFilter(selectedColor,
                    PorterDuff.Mode.SRC_ATOP);
        }
    }


    public static List<Track> getToJson(String jsonTrack) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Track>>() {
        }.getType();
        return gson.fromJson(jsonTrack, type);
    }

    public static String getToList(List listObj) {
        if (listObj == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(listObj);

    }

    @BindingAdapter("playlist_adapter")
    public static void setPlaylistAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setAdapter(adapter);
        view.setItemAnimator(new DefaultItemAnimator());
        view.addItemDecoration(new DividerItemDecoration(view.getContext() , LinearLayoutManager.VERTICAL));
    }

    @BindingAdapter("settext")
    public static void setNumberSong(TextView textView, int numberSong) {
        textView.setText(String.valueOf(numberSong) + " "
                + textView.getResources().getString(R.string.song));
    }
    @BindingAdapter("set_image")
    public static void setImage(ImageView view, int resource) {
        if (String.valueOf(resource).isEmpty()) {
            view.setImageResource(R.drawable.img_background_genres);
        }
        view.setImageResource(resource);
    }
    @BindingAdapter("set_image")
    public static void setImage(ImageView view, String url) {
        if (url == null) {
            view.setImageResource(R.mipmap.ic_launcher);
        }
        else {
            view.setImageResource(R.drawable.ic_favorite_gray_24dp);
        }
    }

}

