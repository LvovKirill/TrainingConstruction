package com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.Exercise.ExerciseViewModel;
import com.example.trainingconstructor.DataBase.Training.Training;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentAddPointExersiseBinding;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.CreateTrainingFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.TrainingFragment;
import com.example.trainingconstructor.ui.MainActivity;
import com.example.trainingconstructor.ui.TabataTimerScreen.TabataTimerFragment;

import java.util.List;


public class AddPointExersiseFragment extends Fragment implements View.OnClickListener{

    FragmentAddPointExersiseBinding binding;
    public static ExerciseViewModel exerciseViewModel;
    int currentId=-1;



    public static AddPointExersiseFragment newInstance(int id) {
        AddPointExersiseFragment trainingFragment = new AddPointExersiseFragment();
        Bundle args = new Bundle();
        args.putInt("ID", id);
        trainingFragment.setArguments(args);
        return trainingFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddPointExersiseBinding.inflate(inflater, container, false);

        final AddPointTrainingListAdapter adapter = new AddPointTrainingListAdapter(new AddPointTrainingListAdapter.ExerciseDiff(), getActivity(), AddPointExersiseFragment.this);
        binding.myAddPointExersiseRecyclerview.setAdapter(adapter);
        binding.myAddPointExersiseRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);

        exerciseViewModel.getAllExercises().observe(getViewLifecycleOwner(), trainings -> {
            adapter.submitList(trainings);
        });

        binding.createButton.setOnClickListener(this);

        return binding.getRoot();
    }


    public void setData(String name, int currentId){
        binding.editText.setText(name);
        this.currentId = currentId;
    }

    @Override
    public void onClick(View v) {

        if(currentId==-1) {
            Toast.makeText(getActivity(), "Выберете упражнение", Toast.LENGTH_LONG).show();
        }else {
            int repeat = Integer.parseInt(binding.counterRepeat.getNumber().toString());
            int weight = Integer.parseInt(binding.counterWeight.getNumber().toString());
            int time = Integer.parseInt(binding.counterTime.getNumber().toString());
            int exerciseID = currentId;
            int trainingID = getArguments().getInt("ID");
            int numberInTraining = DataBase.getDatabase(getActivity()).trainingFromExerciseDao()
                    .getTrainingFromExerciseFromTrainingId(trainingID).size() + 1;
            TrainingFromExercise trainingFromExercise = new TrainingFromExercise(exerciseID, trainingID, repeat, weight, time, 2, 1, numberInTraining);
            DataBase.getDatabase(getActivity()).trainingFromExerciseDao().insertTrainingFromExercise(trainingFromExercise);
            getActivity().getSupportFragmentManager().findFragmentByTag("trainingFrag").onStart();
            getActivity().onBackPressed();

        }
    }

}

