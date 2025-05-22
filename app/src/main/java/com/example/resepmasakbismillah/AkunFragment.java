package com.example.resepmasakbismillah;

import static com.example.resepmasakbismillah.HistoryFragment.ARG_PARAM1;
import static com.example.resepmasakbismillah.HistoryFragment.ARG_PARAM2;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resepmasakbismillah.databinding.FragmentAkunBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class AkunFragment extends Fragment {

    private FragmentAkunBinding binding;
    private FirebaseAuth mAuth;
    private String mParam1;
    private String mParam2;

    public AkunFragment() {
        // Required empty public constructor
    }

    public static AkunFragment newInstance(String param1, String param2) {
        AkunFragment fragment = new AkunFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAkunBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

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


        binding.btnSaved.setOnClickListener(v -> {
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentTransaction transaction = childFragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainer, new SavedFragment());
            transaction.commit();
        });

        // Handle logout
        binding.btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        });

        binding.btnSaved.setOnClickListener(v -> {
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentTransaction transaction = childFragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainer, new SavedFragment());
            transaction.commit();
        });

        binding.btnMyRecipes.setOnClickListener(v -> {
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentTransaction transaction = childFragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainer, new ResepkuFragment());
            transaction.commit();
        });

        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = childFragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, new SavedFragment());
        transaction.commit();

        return view;
    }

    private void updateProfileInfo(FirebaseUser user) {
        if (user != null) {
            binding.tvUsername.setText(user.getDisplayName() != null ? user.getDisplayName() : "User");
            binding.tvEmail.setText(user.getEmail() != null ? user.getEmail() : "No email");
        } else {
            binding.tvUsername.setText("Not logged in");
            binding.tvEmail.setText("Please log in");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}