package com.example.resepmasakbismillah;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment {
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
        recyclerView = view.findViewById(R.id.ry_saved);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new RecipeAdapter(
                getSavedRecipes(),
                (recipe, context) -> {
                    Intent intent = new Intent(context, ResepActivity.class);
                    intent.putExtra(ResepActivity.RECIPE_ID_KEY, recipe.getId());
                    context.startActivity(intent);
                }
        ));

        return view;
    }

    private List<Recipe> getSavedRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        // Add your saved recipes here
        return recipes;
    }
}