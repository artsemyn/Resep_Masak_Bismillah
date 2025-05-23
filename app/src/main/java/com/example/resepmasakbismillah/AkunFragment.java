package com.example.resepmasakbismillah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.resepmasakbismillah.databinding.FragmentAkunBinding;

public class AkunFragment extends Fragment {
    private FragmentAkunBinding binding;
    private AkunViewModel viewModel;
    private FirebaseAuth mAuth;
    private MainActivity mainActivity;

    public AkunFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        viewModel = new ViewModelProvider(this).get(AkunViewModel.class);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAkunBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setVariable(BR.viewModel, viewModel);
        
        View view = binding.getRoot();
        
        // Setup click listeners
        binding.btnLogout.setOnClickListener(v -> {
            // Clear Firebase authentication
            mAuth.signOut();
            
            // Clear login state
            if (mainActivity != null) {
                mainActivity.clearLoginState();
            }
            
            // Navigate to LoginActivity
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        });

        binding.btnSaved.setOnClickListener(v -> {
            // Navigate to saved recipes
            FragmentManager fragmentManager = getParentFragmentManager();
            if (fragmentManager != null) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentContainer, new SavedFragment());
                transaction.commit();
            }
        });

        binding.btnMyRecipes.setOnClickListener(v -> {
            // Navigate to my recipes
            FragmentManager fragmentManager = getParentFragmentManager();
            if (fragmentManager != null) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentContainer, new ResepkuFragment());
                transaction.commit();
            }
        });

        mAuth.addAuthStateListener(auth -> {
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                // Get display name or fall back to email if no display name is set
                String displayName = user.getDisplayName();
                if (displayName == null || displayName.isEmpty()) {
                    String email = user.getEmail();
                    if (email != null) {
                        displayName = email.substring(0, email.indexOf('@'));
                    } else {
                        displayName = "User";
                    }
                }
                binding.tvUsername.setText(displayName);
                binding.tvEmail.setText(user.getEmail() != null ? user.getEmail() : "No email");
            } else {
                binding.tvUsername.setText("Not logged in");
                binding.tvEmail.setText("Please log in");
            }
        });

        FragmentManager fragmentManager = getParentFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainer, new SavedFragment());
            transaction.commit();
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}