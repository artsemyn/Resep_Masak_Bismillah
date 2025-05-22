package com.example.resepmasakbismillah;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AkunViewModel extends ViewModel {
    private final FirebaseAuth mAuth;
    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();

    public AkunViewModel() {
        mAuth = FirebaseAuth.getInstance();
        updateUserData();
    }

    private void updateUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String displayName = user.getDisplayName();
            if (displayName == null || displayName.isEmpty()) {
                String email = user.getEmail();
                if (email != null) {
                    displayName = email.substring(0, email.indexOf('@'));
                }
            }
            username.setValue(displayName);
            email.setValue(user.getEmail());
        }
    }

    public LiveData<String> getUsername() {
        return username;
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public void onLogout() {
        mAuth.signOut();
    }
}