package com.example.rickmorty_api;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickmorty_api.character.Character;
import com.example.rickmorty_api.character.CharacterAdapter;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CharacterAdapter characterAdapter;
    private List<Character> characterList;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.reciclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        characterList = new ArrayList<>();
        characterAdapter = new CharacterAdapter(characterList);

        recyclerView.setAdapter(characterAdapter);

        //Spinner
        spinner = findViewById(R.id.spinnerCategory);

        ArrayAdapter<CharSequence> speciesAdapter = ArrayAdapter.createFromResource(this, R.array.species_array, android.R.layout.simple_spinner_item);
        speciesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(speciesAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedSpecies = speciesAdapter.getItem(position).toString();
                filterCharactersBySpecies(selectedSpecies);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // No es necesario implementar nada aquí
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RickAndMortyApi api = retrofit.create(RickAndMortyApi.class);
        Call<ApiResponse> call = api.getCharacters();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    characterList.addAll(response.body().getResults());
                    characterAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterCharactersBySpecies(String selectedSpecies) {
        List<Character> filteredCharacters = new ArrayList<>();
        for (Character character : characterList) {
            if (character.getSpecies().equals(selectedSpecies)) {
                filteredCharacters.add(character);
            }
        }
        characterAdapter.setCharacters(filteredCharacters);
        characterAdapter.notifyDataSetChanged();
    }


}
