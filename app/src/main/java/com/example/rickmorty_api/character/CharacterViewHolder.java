package com.example.rickmorty_api.character;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rickmorty_api.R;

public class CharacterViewHolder extends RecyclerView.ViewHolder {
    public TextView characterName;
    public TextView characterStatus;
    public ImageView characterImage;

    public CharacterViewHolder(View itemView) {
        super(itemView);
        characterName = itemView.findViewById(R.id.characterName);
        characterStatus = itemView.findViewById(R.id.characterStatus);
        characterImage = itemView.findViewById(R.id.characterImage);
    }
}
