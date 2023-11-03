package com.example.rickmorty_api.SQL;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rickmorty_api.R;


public class FavoriteCharactersActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_characters);

        RecyclerView recyclerView = findViewById(R.id.favoriteCharactersRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        CharacterDAO characterDAO = new CharacterDAO(this);
        characterDAO.open();
        Cursor cursor = characterDAO.getFavoriteCharacters();

        FavoriteCharacterAdapter adapter = new FavoriteCharacterAdapter(cursor);
        recyclerView.setAdapter(adapter);

        characterDAO.close();
        cursor.close();
    }
}
