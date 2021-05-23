package com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.DataBase.Exercise.ExerciseViewModel;
import com.example.trainingconstructor.R;

public class MyExerciseFragment extends Fragment {

    com.example.trainingconstructor.databinding.FragmentMyExerciseBinding binding;
    public static ExerciseViewModel exerciseViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = com.example.trainingconstructor.databinding.FragmentMyExerciseBinding.inflate(inflater, container, false);

        final ExerciseListAdapter adapter = new ExerciseListAdapter(new ExerciseListAdapter.ExerciseDiff(), getActivity());
        binding.myExerciseRecyclerview.setAdapter(adapter);
        binding.myExerciseRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);

        exerciseViewModel.getAllExercises().observe(getViewLifecycleOwner(), trainings -> {
            adapter.submitList(trainings);
        });

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateExerciseFragment youFragment = new CreateExerciseFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().add(R.id.frameLayout, youFragment, "createExerciseFrag")
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack("createExerciseFrag")
                        .commit();
            }
        });

        return binding.getRoot();
    }


    public static void addExercise(Exercise training) {
        exerciseViewModel.insert(training);
    }
}