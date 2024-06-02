package com.example.cordis.ui.authentication.signup;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cordis.Methods;
import com.example.cordis.R;
import com.example.cordis.databinding.FragmentSignUpBinding;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpFragment extends Fragment {
    FragmentSignUpBinding binding;
    SignUpViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        viewModel.signUpStatus.observe(getViewLifecycleOwner(), signUpState -> {
            switch (signUpState) {
                case LOADING:
                    binding.blockingView.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    findNavController(binding.getRoot()).navigate(R.id.action_signUpFragment_to_playlistsFragment);
                    break;
                case ERROR:
                    binding.blockingView.setVisibility(View.GONE);
                    binding.progressBar.setVisibility(View.GONE);
                    binding.passwordLayout.setError("Invalid email or password");
                    break;
            }
        });
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(binding.getRoot()).navigate(R.id.action_signUpFragment_to_logInFragment);
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(binding.getRoot()).navigate(R.id.action_signUpFragment_to_logInFragment);
            }
        });

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.userSignUp(binding.inputName.getText().toString(), binding.inputEmail.getText().toString(), binding.inputPassword.getText().toString());
            }
        });

        TextInputLayout passwordLayout = binding.passwordLayout;
        Methods.setPasswordIcon(passwordLayout, binding.getRoot());
        TextInputLayout confirmPasswordLayout = binding.confirmPasswordLayout;
        Methods.setPasswordIcon(confirmPasswordLayout, binding.getRoot());

        return binding.getRoot();
    }
}