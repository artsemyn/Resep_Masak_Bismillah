package com.example.resepmasakbismillah;

import com.google.firebase.firestore.DocumentId;

public class Recipe {
    @DocumentId
    private String id;
    private String nama;  // Changed from title
    private String jenis; // Changed from category
    private String deskripsi; // Changed from description
    private String waktu; // Changed from cookingTime
    private String bahan; // Changed from ingredients
    private String cara; // Changed from instructions
    private String userId;
    private long timestamp;

    // Empty constructor required for Firestore
    public Recipe() {
    }

    public Recipe(String nama, String jenis, String deskripsi,
                  String waktu, String bahan,
                  String cara, String userId, long timestamp) {
        this.nama = nama;
        this.jenis = jenis;
        this.deskripsi = deskripsi;
        this.waktu = waktu;
        this.bahan = bahan;
        this.cara = cara;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    public String getWaktu() { return waktu; }
    public void setWaktu(String waktu) { this.waktu = waktu; }

    public String getBahan() { return bahan; }
    public void setBahan(String bahan) { this.bahan = bahan; }

    public String getCara() { return cara; }
    public void setCara(String cara) { this.cara = cara; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}