package com.example.resepmasakbismillah;

import java.util.List;

public class Recipe {
    private String title; // Nama resep
    private int imageResource; // Gambar makanan
    private String cookingTime; // Waktu memasak
    private String category; // Jenis makanan
    private List<String> ingredients; // Bahan
    private List<String> steps; // Langkah-langkah
    private String author; // Penulis resep
    private String dateAdded; // Tanggal ditambahkan

    public Recipe(String title, int imageResource, String cookingTime,
                  String category, List<String> ingredients, List<String> steps) {
        this.title = title;
        this.imageResource = imageResource;
        this.cookingTime = cookingTime;
        this.category = category;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public String getAuthor() {
        return author;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}