package com.example.resepmasakbismillah;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resepmasakbismillah.RecipeAdapter;
import com.example.resepmasakbismillah.databinding.FragmentHistoryBinding;
import com.example.resepmasakbismillah.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    private FragmentHistoryBinding binding;
    private RecipeAdapter adapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize RecyclerView
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvHistory.setHasFixedSize(true);
        adapter = new RecipeAdapter(new ArrayList<>(), (recipe, context) -> {
            Intent intent = new Intent(context, ResepActivity.class);
            intent.putExtra(ResepActivity.RECIPE_ID_KEY, recipe.getId());
            context.startActivity(intent);
        });
        binding.rvHistory.setAdapter(adapter);

        loadHistoryRecipes();

        return view;
    }

    private void loadHistoryRecipes() {
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(getContext(), "Silakan login terlebih dahulu", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = mAuth.getCurrentUser().getUid();
        db.collection("recipes")
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Recipe> recipes = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Recipe recipe = document.toObject(Recipe.class);
                        recipe.setId(document.getId());
                        recipes.add(recipe);
                    }
                    adapter.updateRecipes(recipes);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Gagal memuat riwayat resep: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
