package com.utopiaxc.jpmahjong.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.utopiaxc.jpmahjong.R;

public class ActivitySettings extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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

        setContentView(R.layout.activity_settings);
        super.onCreate(savedInstanceState);
    }
}
