package com.example.resepmasakbismillah;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TambahResepActivity extends AppCompatActivity {
    private EditText etNamaResep, etWaktu, etBahan, etLangkah;
    private Spinner spnJenis;
    private Button btnSimpan;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_resep);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize views
        etNamaResep = findViewById(R.id.etNamaResep);
        etWaktu = findViewById(R.id.etWaktu);
        spnJenis = findViewById(R.id.spnJenis);
        etBahan = findViewById(R.id.etBahan);
        etLangkah = findViewById(R.id.etLangkah);
        btnSimpan = findViewById(R.id.btnSave);

        // Set up click listeners
        btnSimpan.setOnClickListener(v -> saveRecipe());
    }

    private void saveRecipe() {
        // Get values from form
        String nama = etNamaResep.getText().toString().trim();
        String waktu = etWaktu.getText().toString().trim();
        String jenis = spnJenis.getSelectedItem().toString();
        String bahan = etBahan.getText().toString().trim();
        String cara = etLangkah.getText().toString().trim();

        // Validate form
        if (TextUtils.isEmpty(nama)) {
            etNamaResep.setError("Nama resep tidak boleh kosong");
            return;
        }
        if (TextUtils.isEmpty(waktu)) {
            etWaktu.setError("Waktu pembuatan tidak boleh kosong");
            return;
        }
        if (TextUtils.isEmpty(bahan)) {
            etBahan.setError("Bahan tidak boleh kosong");
            return;
        }
        if (TextUtils.isEmpty(cara)) {
            etLangkah.setError("Langkah pembuatan tidak boleh kosong");
            return;
        }

        // Format ingredients and steps with bullet points
        String formattedBahan = formatWithBulletPoints(bahan);
        String formattedCara = formatWithBulletPoints(cara);

        // Create recipe object
        Map<String, Object> recipe = new HashMap<>();
        recipe.put("nama", nama);
        recipe.put("waktu", waktu);
        recipe.put("jenis", jenis);
        recipe.put("bahan", formattedBahan);
        recipe.put("cara", formattedCara);
        recipe.put("timestamp", System.currentTimeMillis());
        recipe.put("userId", mAuth.getCurrentUser().getUid());

        // Save to Firestore
        db.collection("recipes")
            .add(recipe)
            .addOnSuccessListener(documentReference -> {
                Toast.makeText(TambahResepActivity.this, "Resep berhasil disimpan", Toast.LENGTH_SHORT).show();
                finish();
            })
            .addOnFailureListener(e -> {
                Toast.makeText(TambahResepActivity.this, "Gagal menyimpan resep: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
    }

    private String formatWithBulletPoints(String text) {
        // Split by new lines and add bullet points
        String[] lines = text.split("\\n");
        StringBuilder formatted = new StringBuilder();
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                formatted.append("• ").append(line.trim()).append("\\n");
            }
        }
        return formatted.toString().trim();
    }
}