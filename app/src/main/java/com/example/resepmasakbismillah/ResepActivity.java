package com.example.resepmasakbismillah;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResepActivity extends AppCompatActivity {
    public static final String RECIPE_ID_KEY = "recipeId";
    private TextView tvTitle;
    private TextView tvTime;
    private TextView tvIngredients;
    private TextView tvInstructions;
    private ImageButton btnBack;
    private MaterialButton btnSave;
    private MaterialButton btnRecook;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String recipeId;
    private boolean isSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resep);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        tvTitle = findViewById(R.id.recipeTitle);
        tvTime = findViewById(R.id.recipeTime);
        tvIngredients = findViewById(R.id.ingredientsList);
        tvInstructions = findViewById(R.id.stepsList);
        btnBack = findViewById(R.id.backButton);
        btnSave = findViewById(R.id.saveButton);
        btnRecook = findViewById(R.id.recookButton);

        // Handle back button
        btnBack.setOnClickListener(v -> finish());

        // Get recipe ID from intent
        Intent intent = getIntent();
        if (intent != null) {
            recipeId = intent.getStringExtra(RECIPE_ID_KEY);
            if (recipeId != null) {
                fetchRecipeData(recipeId);
            }
        }

        // Handle system bars
        View decorView = getWindow().getDecorView();
        decorView.setOnApplyWindowInsetsListener((v, insets) -> {
            Insets systemBars = Insets.of(
                insets.getSystemWindowInsetLeft(),
                insets.getSystemWindowInsetTop(),
                insets.getSystemWindowInsetRight(),
                insets.getSystemWindowInsetBottom()
            );
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            );
            return insets;
        });
    }

    private void fetchRecipeData(String recipeId) {
        db.collection("recipes").document(recipeId)
            .get()
            .addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    Recipe recipe = documentSnapshot.toObject(Recipe.class);
                    if (recipe != null) {
                        setupViews(recipe);
                        setupButtons(recipe);
                    }
                } else {
                    Toast.makeText(this, "Resep tidak ditemukan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            })
            .addOnFailureListener(e -> {
                Toast.makeText(this, "Gagal mengambil data resep: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            });
    }

    private void setupViews(Recipe recipe) {
        tvTitle.setText(recipe.getNama());
        tvTime.setText(recipe.getWaktu());
        tvIngredients.setText(recipe.getBahan());
        
        // Format and display steps as a string
        String[] steps = recipe.getCara();
        String stepsString = String.join("\n", steps);
        tvInstructions.setText(stepsString);
    }

    private void setupButtons(Recipe recipe) {
        // Save button click
        btnSave.setOnClickListener(v -> {
            // Update recipe in Firestore
            db.collection("recipes").document(recipe.getId())
                .update(
                    "nama", tvTitle.getText().toString(),
                    "waktu", tvTime.getText().toString(),
                    "bahan", tvIngredients.getText().toString(),
                    "cara", tvInstructions.getText().toString()
                )
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Resep berhasil disimpan", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Gagal menyimpan resep: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
        });

        // Recook button click
        btnRecook.setOnClickListener(v -> {
            // Create a copy of the recipe with new timestamp
            // Split instructions into array of strings
            String[] instructionsArray = tvInstructions.getText().toString().split("\\n");
            
            Recipe newRecipe = new Recipe(
                tvTitle.getText().toString(),  // nama
                "",  // jenis
                "",  // deskripsi
                tvTime.getText().toString(),  // waktu
                0,  // kesulitan
                tvIngredients.getText().toString(),  // bahan
                instructionsArray,  // cara (as String[])
                FirebaseAuth.getInstance().getCurrentUser().getUid(),  // userId
                System.currentTimeMillis()  // timestamp
            );

            // Add new recipe to Firestore
            db.collection("recipes").add(newRecipe)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Resep berhasil direcook", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Gagal merecook resep", Toast.LENGTH_SHORT).show();
                });
        });
    }
}