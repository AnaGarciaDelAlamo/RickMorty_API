package com.example.rickmorty_api.character;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rickmorty_api.R;

public class CharacterDetailActivity extends AppCompatActivity {
    private WebView detailWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);

        detailWebView = findViewById(R.id.webview);
        WebSettings webSettings = detailWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("character")) {
            Character character = (Character) intent.getSerializableExtra("character");

            String htmlData = generateCharacterDetailsHTML(character);
            detailWebView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
        }
    }

    private String generateCharacterDetailsHTML(Character character) {
        String htmlData = "<html><body>";
        htmlData += "<h1>" + character.getName() + "</h1>";
        htmlData += "<img src='" + character.getImage() + "' />";
        htmlData += "<p><strong>Status:</strong> " + character.getStatus() + "</p>";
        htmlData += "<p><strong>Species:</strong> " + character.getSpecies() + "</p>";
        htmlData += "<p><strong>Gender:</strong> " + character.getGender() + "</p>";
        htmlData += "<p><strong>Type:</strong> " + character.getType() + "</p>";
        htmlData += "<p><strong>Location:</strong> " + character.getLocation().getName() + "</p>";

        // Lista de episodios, suponiendo que Episode tiene un método getName()
        // Si tienes los nombres de episodios, puedes reemplazar la URL con esos nombres.
        htmlData += "<p><strong>Episodes:</strong></p><ul>";
    /*for (String episodeUrl : character.getEpisode()) {
        htmlData += "<li>" + episodeUrl + "</li>"; // Deberías cambiar esto por los nombres de los episodios si están disponibles
    }*/
        htmlData += "</ul>";
        htmlData += "</body></html>";
        return htmlData;
    }

}