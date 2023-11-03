package com.example.rickmorty_api.character;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rickmorty_api.R;
import com.example.rickmorty_api.SQL.CharacterDAO;
import com.example.rickmorty_api.SQL.FavoriteCharactersActivity;

import java.util.List;

public class CharacterDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);

        int characterId = getIntent().getIntExtra("character_id", 0);
        String characterName = getIntent().getStringExtra("character_name");
        String characterStatus = getIntent().getStringExtra("character_status");
        String characterSpecies = getIntent().getStringExtra("character_species");
        String characterType = getIntent().getStringExtra("character_type");
        String characterGender = getIntent().getStringExtra("character_gender");
        String characterOrigin = getIntent().getStringExtra("character_origin");
        String characterLocation = getIntent().getStringExtra("character_location");
        //String characterEpisode = getIntent().getStringExtra("character_episode");



        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView statusTextView = findViewById(R.id.statusTextView);
        TextView speciesTextView = findViewById(R.id.speciesTextView);
        TextView typeTextView = findViewById(R.id.typeTextView);
        TextView genderTextView = findViewById(R.id.genderTextView);
        TextView originTextView = findViewById(R.id.originTextView);
        TextView locationTextView = findViewById(R.id.locationTextView);
        //TextView episodeTextView = findViewById(R.id.episodeTextView);

        nameTextView.setText("Name: " + characterName);
        statusTextView.setText("Status: " + characterStatus);
        speciesTextView.setText("Species: " + characterSpecies);
        typeTextView.setText("Type: " + characterType);
        genderTextView.setText("Gender: " + characterGender);
        originTextView.setText("Origin: " + characterOrigin);
        locationTextView.setText("Location: " + characterLocation);
        //episodeTextView.setText("Primera aparición: " + characterEpisode);


        Button addToFavoritesButton = findViewById(R.id.botonFav);
        addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener el nombre del personaje
                String characterName = getIntent().getStringExtra("character_name");

                // Llamar al método para agregar el personaje a favoritos
                CharacterDAO characterDAO = new CharacterDAO(view.getContext());
                characterDAO.open();
                long result = characterDAO.addFavoriteCharacter(characterName);
                characterDAO.close();

                if (result != -1) {
                    Toast.makeText(view.getContext(), "Agregado a favoritos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(view.getContext(), "Error al agregar a favoritos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button verfavoritos = findViewById(R.id.btnVerFav);
        verfavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CharacterDetailActivity.this, FavoriteCharactersActivity.class);
                startActivity(intent);
            }
        });


    }
}


