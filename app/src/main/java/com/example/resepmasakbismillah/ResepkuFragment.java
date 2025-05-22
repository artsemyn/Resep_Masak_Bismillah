package com.example.resepmasakbismillah;

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

public class ResepkuFragment extends Fragment {
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resepku, container, false);
        recyclerView = view.findViewById(R.id.rv_resepku);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new RecipeAdapter(
                getMyRecipes(),
                recipe -> {
                    // Handle recipe click
                }
        ));

        return view;
    }

    private List<Recipe> getMyRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        // Add your my recipes here
        return recipes;
    }
}