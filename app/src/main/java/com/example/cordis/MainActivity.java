package com.example.cordis;

import static com.example.cordis.Methods.isNetworkAvailable;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cordis.databinding.ActivityMainBinding;
import com.example.cordis.domain.user.UserModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        setContentView(binding.getRoot());

        if (isNetworkAvailable(this)) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                user.reload().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (navController.getCurrentDestination().getId() != R.id.startFragment) {
                            navController.navigate(navController.getCurrentDestination().getId());
                        } else {
                            navController.navigate(R.id.playlistsFragment);
                        }
                    } else {
                        navController.navigate(R.id.logInFragment);
                    }
                });
            } else {
                navController.navigate(R.id.logInFragment);
            }
        } else {
            Snackbar.make(binding.getRoot(), "No internet connection detected", Snackbar.LENGTH_LONG).show();
        }
    }
}