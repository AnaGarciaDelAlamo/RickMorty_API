package com.example.rickmorty_api.SQL;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickmorty_api.R;

public class FavoriteCharacterAdapter extends RecyclerView.Adapter<FavoriteCharacterAdapter.FavoriteCharacterViewHolder> {
    private Cursor cursor;

    public FavoriteCharacterAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public FavoriteCharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.favorite_character_item, parent, false);
        return new FavoriteCharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteCharacterViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        String characterName = cursor.getString(cursor.getColumnIndexOrThrow("name"));

        // Asigna el nombre del personaje al TextView
        holder.characterNameTextView.setText(characterName);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class FavoriteCharacterViewHolder extends RecyclerView.ViewHolder {
        public TextView characterNameTextView;

        public FavoriteCharacterViewHolder(View itemView) {
            super(itemView);
            characterNameTextView = itemView.findViewById(R.id.characterNameTextView);
        }
    }
}

