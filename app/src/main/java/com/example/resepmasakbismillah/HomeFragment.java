package com.example.resepmasakbismillah;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resepmasakbismillah.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Map<String, RecipeAdapter> categoryAdapters;
    private Map<String, List<Recipe>> categoryRecipes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        categoryAdapters = new HashMap<>();
        categoryRecipes = new HashMap<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Set up add recipe button
        binding.btnAddRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TambahResepActivity.class);
            startActivity(intent);
        });

        setupRecyclerViews();
        loadRecipes();
        setupCategoryButtons();
        updateUserName();
        return view;
    }

    private void setupRecyclerViews() {
        // Setup RecyclerViews for each category
        setupCategoryRecyclerView("appetizers", binding.ryAppetizers);
        setupCategoryRecyclerView("maincourse", binding.ryMaincourse);
        setupCategoryRecyclerView("soup", binding.rySoup);
        setupCategoryRecyclerView("desserts", binding.ryDesserts);
        setupCategoryRecyclerView("drinks", binding.ryDrinks);
    }

    private void setupCategoryRecyclerView(String category, RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        RecipeAdapter adapter = new RecipeAdapter(new ArrayList<>(), (recipe, context) -> {
            Intent intent = new Intent(context, ResepActivity.class);
            intent.putExtra("recipeId", recipe.getId());
            intent.putExtra("title", recipe.getNama());
            intent.putExtra("cookingTime", recipe.getWaktu());
            intent.putExtra("category", recipe.getJenis());
            intent.putExtra("ingredients", recipe.getBahan());
            intent.putExtra("instructions", recipe.getCara());
            context.startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        categoryAdapters.put(category, adapter);
        categoryRecipes.put(category, new ArrayList<>());
    }

    private void loadRecipes() {
        // Query all recipes and group by category
        db.collection("recipes")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                for (com.google.firebase.firestore.QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Recipe recipe = document.toObject(Recipe.class);
                    recipe.setId(document.getId());
                    String category = recipe.getJenis();
                    if (category != null) {
                        // Convert category to lowercase for case-insensitive comparison
                        String normalizedCategory = category.toLowerCase();
                        // Map common variations to standard categories
                        if (normalizedCategory.contains("main")) {
                            normalizedCategory = "maincourse";
                        } else if (normalizedCategory.contains("soup")) {
                            normalizedCategory = "soup";
                        } else if (normalizedCategory.contains("dessert")) {
                            normalizedCategory = "desserts";
                        } else if (normalizedCategory.contains("drink")) {
                            normalizedCategory = "drinks";
                        } else {
                            normalizedCategory = "appetizers"; // Default to appetizers
                        }
                        
                        if (categoryAdapters.containsKey(normalizedCategory)) {
                            categoryRecipes.get(normalizedCategory).add(recipe);
                            categoryAdapters.get(normalizedCategory).updateRecipes(categoryRecipes.get(normalizedCategory));
                        }
                    }
                }
            });
    }

    private void updateUserName() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String displayName = user.getDisplayName();
            if (displayName == null || displayName.isEmpty()) {
                String email = user.getEmail();
                if (email != null) {
                    displayName = email.substring(0, email.indexOf('@'));
                } else {
                    displayName = "User";
                }
            }
            binding.userName.setText(displayName);
        } else {
            binding.userName.setText("Guest");
        }
    }

    private void setupCategoryButtons() {
        binding.btnAppetizers.setOnClickListener(v -> {
            scrollSmoothToView(binding.tvTitleAppetizers);
        });

        binding.btnMaincourse.setOnClickListener(v -> {
            scrollSmoothToView(binding.tvTitleMaincourse);
        });

        binding.btnSoup.setOnClickListener(v -> {
            scrollSmoothToView(binding.tvTitleSoup);
        });

        binding.btnDesserts.setOnClickListener(v -> {
            scrollSmoothToView(binding.tvTitleDesserts);
        });

        binding.btnDrinks.setOnClickListener(v -> {
            scrollSmoothToView(binding.tvTitleDrinks);
        });
    }

    private void scrollSmoothToView(final View targetView) {
        targetView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                targetView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                binding.svHome.smoothScrollTo(0, targetView.getTop());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
