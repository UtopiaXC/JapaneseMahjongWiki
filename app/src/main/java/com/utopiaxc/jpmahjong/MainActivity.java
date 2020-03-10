package com.utopiaxc.jpmahjong;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.utopiaxc.jpmahjong.adapter.FragmentPagerAdapters;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    //程序入口
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //本处设置主题颜色
        SharedPreferences sharedPreferences = getSharedPreferences("Theme", MODE_PRIVATE);
        int theme = sharedPreferences.getInt("theme", R.style.DefaultTheme);
        setTheme(theme);
        getApplication().setTheme(theme);

        //本处设置沉浸任务栏颜色
        if(sharedPreferences.getBoolean("isImmersion",false)) {
            Window window = getWindow();
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            window.setStatusBarColor(typedValue.data);
        }

        //设置Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置顶栏
        setActionBar();
        //设置页面Fragment适配器
        setPaperView();
        //设置页面标签器
        setTab();

    }

    //设置顶栏
    private void setActionBar(){
        drawerLayout=findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_dehaze_white_24dp);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_white_24dp);

        }
    }

    //设置页面Fragment适配器
    private void setPaperView(){
        viewPager = findViewById(R.id.view_pager);
        FragmentPagerAdapters fragmentPagerAdapters =
                new FragmentPagerAdapters(this,getSupportFragmentManager(),
                        FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(fragmentPagerAdapters);

    }

    //设置页面标签器
    private void setTab(){
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    //重写构件监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }

}