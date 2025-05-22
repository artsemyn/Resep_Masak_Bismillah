package com.example.resepmasakbismillah;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentAkunBinding extends ViewDataBinding {
    @NonNull
    private final View rootView;
    @NonNull
    public final View btnSaved;
    @NonNull
    public final View btnMyRecipes;
    @NonNull
    public final TextView tvUsername;
    @NonNull
    public final TextView tvEmail;
    public View btnLogout;
    public RecyclerView recyclerView;
    public RecyclerView recyclerSavedRecipes;
    public RecyclerView recyclerMyRecipes;

    public FragmentAkunBinding(@NonNull View rootView, @NonNull View btnSaved,
                               @NonNull View btnMyRecipes, @NonNull TextView tvUsername,
                               @NonNull TextView tvEmail) {
        super(rootView.getContext(), rootView, 0);
        this.recyclerSavedRecipes = recyclerSavedRecipes;
        this.recyclerMyRecipes = recyclerMyRecipes;
        this.rootView = rootView;
        this.btnSaved = btnSaved;
        this.btnMyRecipes = btnMyRecipes;
        this.tvUsername = tvUsername;
        this.tvEmail = tvEmail;
    }

    @NonNull
    public static FragmentAkunBinding bind(@NonNull View rootView) {
        View btnHistory = rootView.findViewById(R.id.btnSave);
        View btnMyRecipes = rootView.findViewById(R.id.btnMyRecipes);
        View btnLogout = rootView.findViewById(R.id.btnLogout);
        TextView tvUsername = rootView.findViewById(R.id.tvUsername);
        TextView tvEmail = rootView.findViewById(R.id.tvEmail);

        return new FragmentAkunBinding(rootView, btnHistory, btnMyRecipes, tvUsername, tvEmail);
    }

    @NonNull
    public static FragmentAkunBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.fragment_akun, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object value) {
        return false;
    }

    @Override
    protected void executeBindings() {
        // Add any data binding logic here
    }

    @Override
    public void invalidateAll() {
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        return false;
    }

    @NonNull
    public View getRoot() {
        return rootView;
    }
}