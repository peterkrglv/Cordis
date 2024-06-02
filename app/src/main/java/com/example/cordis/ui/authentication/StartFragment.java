package com.example.cordis.ui.authentication;

import static androidx.navigation.Navigation.findNavController;
import static com.example.cordis.Methods.isNetworkAvailable;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cordis.R;
import com.example.cordis.databinding.FragmentStartBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartFragment extends Fragment {
    FragmentStartBinding binding;
    NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartBinding.inflate(inflater, container, false);
        binding.imageGuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(getContext())) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        user.reload().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                findNavController(binding.getRoot()).navigate(R.id.action_startFragment_to_playlistsFragment);
                            } else {
                                findNavController(binding.getRoot()).navigate(R.id.action_startFragment_to_logInFragment);
                            }
                        });
                    } else {
                        findNavController(binding.getRoot()).navigate(R.id.action_startFragment_to_logInFragment);
                    }
                } else {
                    Snackbar.make(binding.getRoot(), "No internet connection detected", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        return binding.getRoot();
    }
}