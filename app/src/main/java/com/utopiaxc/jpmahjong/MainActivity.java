package com.utopiaxc.jpmahjong;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.utopiaxc.jpmahjong.Activities.ActivityAbout;
import com.utopiaxc.jpmahjong.Activities.ActivityLinks;
import com.utopiaxc.jpmahjong.Activities.ActivitySettings;
import com.utopiaxc.jpmahjong.adapter.FragmentPagerAdapters;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private Context context;

    //程序入口
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //本处设置主题颜色
        SharedPreferences sharedPreferences = getSharedPreferences("Theme", MODE_PRIVATE);
        int theme = sharedPreferences.getInt("theme", R.style.DefaultTheme);
        setTheme(theme);
        getApplication().setTheme(theme);

        //本处设置沉浸任务栏颜色
        if (sharedPreferences.getBoolean("isImmersion", false)) {
            Window window = getWindow();
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            window.setStatusBarColor(typedValue.data);
        }

        //设置Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        //设置顶栏
        setActionBar();
        //设置页面Fragment适配器
        setPaperView();
        //设置页面标签器
        setTab();
        //抽屉按钮监听
        setNavigationView();
    }

    //设置顶栏
    private void setActionBar() {
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_white_24dp);
        }

    }

    //设置页面Fragment适配器
    private void setPaperView() {
        viewPager = findViewById(R.id.view_pager);
        FragmentPagerAdapters fragmentPagerAdapters =
                new FragmentPagerAdapters(this, getSupportFragmentManager(),
                        FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(fragmentPagerAdapters);

    }

    //设置页面标签器
    private void setTab() {
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    //重写构件监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("ItemSelected");
        drawerLayout.openDrawer(GravityCompat.START);
        return true;
    }

    //抽屉按钮监听
    private void setNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("ShowToast")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_item_share:
                        new AlertDialog.Builder(context)
                                .setTitle(getString(R.string.alert))
                                .setMessage(getString(R.string.alert_share))
                                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        openApplicationMarket(getPackageName());
                                    }
                                })
                                .setNegativeButton(getString(R.string.cancel), null)
                                .create()
                                .show();
                        break;

                    case R.id.navigation_item_feedback:
                        new AlertDialog.Builder(context)
                                .setTitle(getString(R.string.alert))
                                .setMessage(getString(R.string.alert_feedback))
                                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String url = "https://github.com/UtopiaXC/JapaneseMahjongWiki/issues";
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse(url));
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton(getString(R.string.cancel), null)
                                .create()
                                .show();
                        break;

                    case R.id.navigation_item_about:
                        Intent intent_about = new Intent(MainActivity.this, ActivityAbout.class);
                        startActivity(intent_about);

                        break;

                    case R.id.navigation_item_settings:
                        Intent intent_settings = new Intent(MainActivity.this, ActivitySettings.class);
                        startActivity(intent_settings);

                        break;

                    case R.id.navigation_item_links:
                        Intent intent_links = new Intent(MainActivity.this, ActivityLinks.class);
                        startActivity(intent_links);

                        break;

                    case R.id.navigation_item_random:
                        Toast.makeText(context, "好累QAQ，这功能不想写呜呜呜", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.header_image:
                        Toast.makeText(context, "想换头像嘛，不给你换的哦:)", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        View header=navigationView.getHeaderView(0);
        ImageView image_avatar= header.findViewById(R.id.header_image);
        image_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "想换头像嘛，不给你换的哦o(￣ε￣*)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //打开应用市场，用于分享
    private void openApplicationMarket(String packageName) {
        try {
            String str = "market://details?id=" + packageName;
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            localIntent.setData(Uri.parse(str));
            startActivity(localIntent);
        } catch (Exception e) {
            // 打开应用商店失败 可能是没有手机没有安装应用市场
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.share_fail), Toast.LENGTH_SHORT).show();
            // 调用系统浏览器进入商城
            String url = "http://coolapk.com";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }

}