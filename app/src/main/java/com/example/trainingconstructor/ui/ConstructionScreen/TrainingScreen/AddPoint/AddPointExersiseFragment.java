package com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingconstructor.DataBase.Exercise.ExerciseViewModel;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.databinding.FragmentAddPointExersiseBinding;


public class AddPointExersiseFragment extends Fragment implements View.OnClickListener{

    FragmentAddPointExersiseBinding binding;
    public static ExerciseViewModel exerciseViewModel;
    private FragmentListener listener;


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

    public void setText(int name){
        binding.editText.setText(String.valueOf(name));
    }

    @Override
    public void onClick(View v) {

        int repeat = Integer.parseInt(binding.counterRepeat.getNumber().toString());
        int weight = Integer.parseInt(binding.counterWeight.getNumber().toString());
        int time = Integer.parseInt(binding.counterTime.getNumber().toString());
        int exerciseID = Integer.parseInt(binding.editText.getText().toString());
        int trainingID = getArguments().getInt("ID");

        TrainingFromExercise trainingFromExercise = new TrainingFromExercise(exerciseID,trainingID,repeat,weight,time,2);
        listener.onInputTrainingFromExerciseSent(trainingFromExercise);

    }

    public interface FragmentListener{
        void onInputTrainingFromExerciseSent(TrainingFromExercise trainingFromExercise);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentListener){
            listener=(FragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }
}

