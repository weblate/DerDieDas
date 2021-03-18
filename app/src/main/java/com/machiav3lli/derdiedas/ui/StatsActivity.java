package com.machiav3lli.derdiedas.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.machiav3lli.derdiedas.R;
import com.machiav3lli.derdiedas.databinding.ActivityStatsBinding;
import com.machiav3lli.derdiedas.utils.DatabaseUtil;
import com.machiav3lli.derdiedas.utils.FileUtils;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class StatsActivity extends AppCompatActivity {

    private ActivityStatsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
            setWordStats();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        binding.back.setOnClickListener(v -> onBackPressed());
        binding.statsInfo.setOnClickListener(v -> onFullWords());
    }

    private void setWordStats() throws UnsupportedEncodingException {
        long allNouns = FileUtils.getNounsCount(this);
        long remainingNouns = new DatabaseUtil(this).getNounsCount();
        long learnedWords = allNouns - remainingNouns;
        ((TextView) findViewById(R.id.word_stats)).setText(
                String.format(Locale.ENGLISH, "%d / %d", learnedWords, allNouns)
        );
    }

    public void onFullWords() {
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
