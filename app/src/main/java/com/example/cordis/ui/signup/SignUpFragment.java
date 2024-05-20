package com.example.cordis.ui.signup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cordis.R;
import com.example.cordis.databinding.FragmentSignUpBinding;

public class SignUpFragment extends Fragment {
    FragmentSignUpBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }
}