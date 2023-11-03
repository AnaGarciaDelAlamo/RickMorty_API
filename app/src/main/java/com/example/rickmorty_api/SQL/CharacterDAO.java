package com.example.rickmorty_api.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CharacterDAO {
    private SQLiteDatabase database;
    private CharacterDatabaseHelper dbHelper;

    public CharacterDAO(Context context) {
        dbHelper = new CharacterDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addFavoriteCharacter(String name) {
        ContentValues values = new ContentValues();
        values.put(CharacterDatabaseHelper.COLUMN_NAME, name);

        return database.insert(CharacterDatabaseHelper.TABLE_NAME, null, values);
    }

    public void removeFavoriteCharacter(long characterId) {
        database.delete(CharacterDatabaseHelper.TABLE_NAME,
                CharacterDatabaseHelper.COLUMN_ID + " = " + characterId, null);
    }

    public Cursor getFavoriteCharacters() {
        String[] allColumns = {CharacterDatabaseHelper.COLUMN_ID, CharacterDatabaseHelper.COLUMN_NAME};
        Cursor cursor = database.query(CharacterDatabaseHelper.TABLE_NAME, allColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}

