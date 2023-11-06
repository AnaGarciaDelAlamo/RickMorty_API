package com.example.rickmorty_api.character;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
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

    private ImageButton btnShare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);

        detailWebView = findViewById(R.id.webview);
        WebSettings webSettings = detailWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("character")) {
            character = (Character) intent.getSerializableExtra("character");

            // Verificar si el personaje ya es un favorito
            CharacterDatabaseHelper dbHelper = new CharacterDatabaseHelper(this);
            database = dbHelper.getWritableDatabase();
            isFavorite = checkIfFavorite(character.getName());

            String htmlData = generateCharacterDetailsHTML(character);
            detailWebView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
        }

        btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareCharacter();
            }
        });
    }


    private void shareCharacter() {
        String characterName = character.getName();
        String characterDetail = "Name: " + characterName +
                "\nStatus: " + character.getStatus() +
                "\nSpecies: " + character.getSpecies() +
                "\nGender: " + character.getGender() +
                "\nType: " + character.getType() +
                "\nLocation: " + character.getLocation().getName() +
                "\nOrigin: " + character.getOrigin().getName();

        String imageUrl = character.getImage();
        String shareMessage = characterDetail + "\nImage URL: " + imageUrl;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

        startActivity(Intent.createChooser(shareIntent, "Compartir Personaje"));
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


        if (character.getEpisode() != null && !character.getEpisode().isEmpty()) {
            String firstEpisodeUrl = character.getEpisode().get(0);
            String firstEpisodeName = extractEpisodeNameFromUrl(firstEpisodeUrl);
            htmlData += "<p><strong>First Episode:</strong> " + firstEpisodeName + "</p>";
        }

        htmlData += "</div>";
        return htmlData;
    }

    // Función para extraer el nombre del episodio desde su URL
    private String extractEpisodeNameFromUrl(String episodeUrl) {
        String[] parts = episodeUrl.split("/");
        if (parts.length > 0) {
            String episodeNumber = parts[parts.length - 1];
            return "Episode " + episodeNumber;
        }
        return "Unknown Episode";
    }

    public void showFavorites(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

}
