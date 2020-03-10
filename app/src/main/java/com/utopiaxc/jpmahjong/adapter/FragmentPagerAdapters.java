package com.utopiaxc.jpmahjong.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.utopiaxc.jpmahjong.R;
import com.utopiaxc.jpmahjong.fragments.FragmentRoles;
import com.utopiaxc.jpmahjong.fragments.FragmentTools;
import com.utopiaxc.jpmahjong.fragments.FragmentWiki;

import java.util.ArrayList;
import java.util.List;

public class FragmentPagerAdapters extends FragmentPagerAdapter {
    List<Fragment> fragments;
    Context context;
    private int[] titles={R.string.tab_1,R.string.tab_2,R.string.teb_3};


    public FragmentPagerAdapters(Context context, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

        fragments=new ArrayList<>();
        fragments.add(new FragmentWiki());
        fragments.add(new FragmentRoles());
        fragments.add(new FragmentTools());
        this.context=context;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(titles[position]);
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
