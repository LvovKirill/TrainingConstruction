package com.example.trainingconstructor.ui.TabataTimerScreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingconstructor.DataBase.Exercise.ExerciseViewModel;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExerciseViewModel;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentTabataTimerBinding;
import com.example.trainingconstructor.databinding.FragmentTrainingBinding;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class TabataTimerFragment extends Fragment {


    FragmentTabataTimerBinding binding;
    protected TrainingFromExerciseViewModel trainingFromExerciseViewModel;
    protected ExerciseViewModel exerciseViewModel;


    public static TabataTimerFragment newInstance(int id) {
        TabataTimerFragment fragment = new TabataTimerFragment();
        Bundle args = new Bundle();
        args.getInt("ID", id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTabataTimerBinding.inflate(inflater, container, false);

        List<TrainingFromExercise> list = trainingFromExerciseViewModel.getTrainingFromExerciseFromTrainingId(getArguments().getInt("ID"));

        binding.nameCurrent.setText(exerciseViewModel.getExerciseByID(list.get(1).getExerciseId()).getName());

        return binding.getRoot();
    }
}