package com.example.resepmasakbismillah;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipes;
    private OnRecipeClickListener listener;

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public RecipeAdapter(List<Recipe> recipes, OnRecipeClickListener listener) {
        this.recipes = recipes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe_home, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView;
        TextView cookingTimeView;
        TextView categoryView;
        TextView ingredientsView;
        TextView stepsView;

        RecipeViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivRecipe);
            titleView = itemView.findViewById(R.id.tvRecipeTitle);
            cookingTimeView = itemView.findViewById(R.id.tvCookingTime);
            categoryView = itemView.findViewById(R.id.tvCategory);
            ingredientsView = itemView.findViewById(R.id.tvIngredients);
            stepsView = itemView.findViewById(R.id.tvSteps);
        }

        void bind(Recipe recipe) {
            imageView.setImageResource(recipe.getImageResource());
            titleView.setText(recipe.getTitle());
            cookingTimeView.setText(recipe.getCookingTime());
            categoryView.setText(recipe.getCategory());

            // Format ingredients
            StringBuilder ingredientsText = new StringBuilder();
            for (String ingredient : recipe.getIngredients()) {
                ingredientsText.append("• ").append(ingredient).append("\n");
            }
            ingredientsView.setText(ingredientsText.toString());

            // Format steps
            StringBuilder stepsText = new StringBuilder();
            for (int i = 0; i < recipe.getSteps().size(); i++) {
                stepsText.append(i + 1).append(". ").append(recipe.getSteps().get(i)).append("\n");
            }
            stepsView.setText(stepsText.toString());

            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    ((RecipeAdapter) itemView.getTag()).listener.onRecipeClick(recipe);
                }
            });
        }
    }
}