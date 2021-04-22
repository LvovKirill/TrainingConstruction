package com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentAddPointExersiseBinding;
import com.example.trainingconstructor.databinding.FragmentAddPointExersiseRestBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPointExersiseRestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPointExersiseRestFragment extends Fragment {

    FragmentAddPointExersiseRestBinding binding;


    public static AddPointExersiseRestFragment newInstance(String param1, String param2) {
        AddPointExersiseRestFragment fragment = new AddPointExersiseRestFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddPointExersiseRestBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}