package com.machiav3lli.derdiedas.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import com.machiav3lli.derdiedas.Constants;
import com.machiav3lli.derdiedas.R;
import com.machiav3lli.derdiedas.data.Noun;
import com.machiav3lli.derdiedas.utils.DatabaseUtil;
import com.machiav3lli.derdiedas.utils.FileUtils;
import com.machiav3lli.derdiedas.utils.PrefsUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setDayNightTheme(PrefsUtil.getPrefsString(this, Constants.PREFS_THEME));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        createDatabaseIfFirstRun();
    }

    @OnClick(R.id.practice)
    public void startPractice() {
        startActivity(new Intent(this, WordActivity.class));
    }

    @OnClick(R.id.stats)
    public void showStats() {
        startActivity(new Intent(this, StatsActivity.class));
    }

    @OnClick(R.id.settings)
    public void showSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    protected void onResume() {
        setDayNightTheme(PrefsUtil.getPrefsString(this, Constants.PREFS_THEME));
        super.onResume();
    }

    private void createDatabaseIfFirstRun() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("firstrun", true)) {
            String listNouns = null;
            try {
                listNouns = FileUtils.getNounList(this);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String[] nouns = FileUtils.getLines(listNouns);

            final List<Noun> nounList = new ArrayList<>();
            for (String line : nouns) {
                String noun = line.split(",")[0];
                String gender = line.split(",")[1];
                Noun nounObject = new Noun(noun, gender, 0);
                nounList.add(nounObject);
            }

            new Thread() {
                @Override
                public void run() {
                    new DatabaseUtil(MainActivity.this).addAllNouns(nounList);
                }
            }.start();

            prefs.edit().putBoolean("firstrun", false).apply();
        }
    }

    private void setDayNightTheme(String theme) {
        switch (theme) {
            case "light":
                setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            default:
                setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

}
