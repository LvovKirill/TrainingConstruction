package com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.TrainingScreen.AddPoint;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.TrainingScreen.CreateTrainingFragment;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentChoiceTypeEventTrainingBinding;


public class ChoiceTypeEventTrainingFragment extends Fragment {

    FragmentChoiceTypeEventTrainingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChoiceTypeEventTrainingBinding.inflate(inflater, container, false);

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.exersiseRadioButton.isChecked()) {
                    AddPointExersiseFragment youFragment = new AddPointExersiseFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().add(R.id.frameLayout, youFragment)
                            .setCustomAnimations(R.anim.alpha_anim, R.anim.alpha_anim)
                            .addToBackStack("myStack")
                            .commit();
                }else if(binding.restRadioButton.isChecked()){}


            }
        });

        return binding.getRoot();
    }
}