package com.example.rickmorty_api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatDelegate;
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
    private Switch darkModeSwitch;

    private SharedPreferences sharedPreferences;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.reciclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        characterList = new ArrayList<>();
        characterAdapter = new CharacterAdapter(characterList);
        recyclerView.setAdapter(characterAdapter);

        // Spinner
        spinner = findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.species_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Preferencias de usuario
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Inicializar vistas y configuración del modo claro/oscuro
        darkModeSwitch = (Switch) findViewById(R.id.darkModeSwitch);

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setDarkMode(isChecked);
        });

        // Restaurar el estado del modo oscuro
        boolean isDarkMode = sharedPreferences.getBoolean("darkMode", false);
        setDarkMode(isDarkMode);

        // Recuperar la última categoría seleccionada
       // String lastSelectedSpecies = sharedPreferences.getString("selectedSpecies", "Ninguno");
        //spinner.setSelection(adapter.getPosition(lastSelectedSpecies));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedSpecies = spinner.getSelectedItem().toString();
                filterCharactersBySpecies(selectedSpecies);

                // Guardar la selección en SharedPreferences
                //sharedPreferences.edit().putString("selectedSpecies", selectedSpecies).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                spinner.setSelection(adapter.getPosition("Ninguno"));

                // Guardar la selección en SharedPreferences
                //sharedPreferences.edit().putString("selectedSpecies", "Ninguno").apply();

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

    private void filterCharactersBySpecies(String species) {
        List<Character> filteredCharacters = new ArrayList<>();

        if ("Todos".equals(species)) {
            filteredCharacters.addAll(characterList);
        } else {
            for (Character character : characterList) {
                if (character.getSpecies().equals(species)) {
                    filteredCharacters.add(character);
                }
            }
        }

        characterAdapter.setCharacters(filteredCharacters);
    }

    private void setDarkMode(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            darkModeSwitch.setText("Modo Oscuro");
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            darkModeSwitch.setText("Modo Claro");
        }

        sharedPreferences.edit().putBoolean("darkMode", isDarkMode).apply();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.species_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
