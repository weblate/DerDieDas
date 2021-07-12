package com.machiav3lli.derdiedas.utils;

import android.content.Context;

import com.machiav3lli.derdiedas.data.Noun;
import com.machiav3lli.derdiedas.data.NounDao;
import com.machiav3lli.derdiedas.data.NounDatabase;

import java.util.List;

public class DatabaseUtil {
    private final NounDao database;

    public DatabaseUtil(Context context) {
        database = NounDatabase.Companion.getInstance(context).getNounDao();
    }

    public void addAllNouns(List<Noun> nouns) {
        database.deleteAll();
        database.insert(nouns);
    }

    public List<Noun> getAllNouns() {
        return database.getAll();
    }

    public long getNounsCount() {
        return database.count();
    }
}
