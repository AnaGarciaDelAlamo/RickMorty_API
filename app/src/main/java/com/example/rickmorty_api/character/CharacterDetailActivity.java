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

}
