package com.machiav3lli.derdiedas.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.machiav3lli.derdiedas.R;
import com.machiav3lli.derdiedas.utils.DatabaseUtil;
import com.machiav3lli.derdiedas.utils.FileUtils;

import java.io.UnsupportedEncodingException;

public class StatsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_simple);
        try {
            setWordStats();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void setWordStats() throws UnsupportedEncodingException {
        long allNouns = FileUtils.getNounsCount(this);
        long remainingNouns = new DatabaseUtil(this).getNounsCount();
        long learnedWords = allNouns - remainingNouns;
        ((TextView) findViewById(R.id.word_stats)).setText(learnedWords + " / " + allNouns);
    }

    public void onFullWords(View view) {
        String titleString = getResources().getString(R.string.full_words_title);
        String textString = getResources().getString(R.string.full_words_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setTitle(titleString);
        builder.setMessage(textString);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
