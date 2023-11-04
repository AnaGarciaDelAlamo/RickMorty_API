package com.example.rickmorty_api.character;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rickmorty_api.R;
import com.example.rickmorty_api.SQL.CharacterDatabaseHelper;
import com.example.rickmorty_api.SQL.FavoritesActivity;

public class CharacterDetailActivity extends AppCompatActivity {

    private Character character;
    private WebView detailWebView;

    private SQLiteDatabase database;
    private boolean isFavorite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);

        detailWebView = findViewById(R.id.webview);
        WebSettings webSettings = detailWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("character")) {
            character = (Character) intent.getSerializableExtra("character"); // Asignar el objeto Character a la variable character

            // Verificar si el personaje ya es un favorito
            CharacterDatabaseHelper dbHelper = new CharacterDatabaseHelper(this);
            database = dbHelper.getWritableDatabase();
            isFavorite = checkIfFavorite(character.getName());

            String htmlData = generateCharacterDetailsHTML(character);
            detailWebView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
        }
    }

    public void addToFavorites(View view) {
        if (isFavorite) {
            removeFromFavorites(character.getName());
            isFavorite = false;
            Toast.makeText(this, "Eliminado de favoritos", Toast.LENGTH_SHORT).show();
        } else {
            addToFavorites(character.getName());
            isFavorite = true;
            Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show();
        }
    }

    private void addToFavorites(String characterName) {
        ContentValues values = new ContentValues();
        values.put(CharacterDatabaseHelper.COLUMN_NAME, characterName);
        database.insert(CharacterDatabaseHelper.TABLE_FAVORITES, null, values);
    }

    private void removeFromFavorites(String characterName) {
        database.delete(CharacterDatabaseHelper.TABLE_FAVORITES,
                CharacterDatabaseHelper.COLUMN_NAME + " = ?",
                new String[]{characterName});
    }

    private boolean checkIfFavorite(String characterName) {
        String[] columns = {CharacterDatabaseHelper.COLUMN_ID};
        String selection = CharacterDatabaseHelper.COLUMN_NAME + " = ?";
        String[] selectionArgs = {characterName};
        Cursor cursor = database.query(
                CharacterDatabaseHelper.TABLE_FAVORITES,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }







    //Elementos webView
    private String generateCharacterDetailsHTML(Character character) {
        String htmlData = "<html><body>";
        htmlData += "<div style='text-align: center;'>";
        htmlData += "<h1>" + character.getName() + "</h1>";
        htmlData += "<img src='" + character.getImage() + "' />";
        htmlData += "<p><strong>Status:</strong> " + character.getStatus() + "</p>";
        htmlData += "<p><strong>Species:</strong> " + character.getSpecies() + "</p>";
        htmlData += "<p><strong>Gender:</strong> " + character.getGender() + "</p>";
        htmlData += "<p><strong>Type:</strong> " + character.getType() + "</p>";
        htmlData += "<p><strong>Location:</strong> " + character.getLocation().getName() + "</p>";
        htmlData += "<p><strong>Origin:</strong> " + character.getOrigin().getName() + "</p>";
        htmlData += "<p><strong>Episodes:</strong></p><ul>" + character.getEpisode().get(0);
        htmlData += "</div>";
        return htmlData;
    }

    public void showFavorites(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

}
