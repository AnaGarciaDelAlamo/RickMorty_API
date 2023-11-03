package com.example.rickmorty_api.character;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rickmorty_api.R;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterViewHolder> {
    private List<Character> characterList;

    public CharacterAdapter(List<Character> characterList) {
        this.characterList = characterList;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_item, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Character character = characterList.get(position);
        holder.characterName.setText("Name: " + character.getName());
        holder.characterStatus.setText("Status: " + character.getStatus());

        Glide.with(holder.itemView.getContext())
                .load(character.getImage())
                .into(holder.characterImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CharacterDetailActivity.class);
                intent.putExtra("character", character); // Pasa el objeto Character en la intención
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }
}
