package com.example.rickmorty_api.SQL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rickmorty_api.R;

public class FavoritesActivity extends Activity {
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_activity);

        CharacterDatabaseHelper dbHelper = new CharacterDatabaseHelper(this);
        database = dbHelper.getReadableDatabase();

        // Obtener la lista de nombres de personajes favoritos desde la base de datos
        String[] favoriteCharacterNames = getAllFavoriteCharacterNames();

        ListView listView = findViewById(R.id.favoriteCharactersList);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoriteCharacterNames);
        listView.setAdapter(adapter);
    }

    private String[] getAllFavoriteCharacterNames() {
        String[] projection = {
                CharacterDatabaseHelper.COLUMN_NAME
        };

        Cursor cursor = database.query(
                CharacterDatabaseHelper.TABLE_FAVORITES,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        String[] characterNames = new String[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String characterName = cursor.getString(cursor.getColumnIndex(CharacterDatabaseHelper.COLUMN_NAME));
            characterNames[i] = characterName;
            i++;
        }

        cursor.close();
        return characterNames;
    }
}
