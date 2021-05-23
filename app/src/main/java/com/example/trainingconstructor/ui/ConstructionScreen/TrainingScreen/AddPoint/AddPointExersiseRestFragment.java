package com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExerciseViewModel;
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

    public static AddPointExersiseRestFragment newInstance(int id) {
        AddPointExersiseRestFragment addPointExersiseRestFragment = new AddPointExersiseRestFragment();
        Bundle args = new Bundle();
        args.putInt("ID", id);
        addPointExersiseRestFragment.setArguments(args);
        return addPointExersiseRestFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddPointExersiseRestBinding.inflate(inflater, container, false);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time = Integer.parseInt(binding.counter.getNumber());
                int trainingId = getArguments().getInt("ID");
                int numberInTraining = DataBase.getDatabase(getActivity()).trainingFromExerciseDao()
                        .getTrainingFromExerciseFromTrainingId(trainingId).size()+1;
                TrainingFromExercise trainingFromExercise = new TrainingFromExercise(0,trainingId,0,0,time,0, 0, numberInTraining);
                DataBase.getDatabase(getActivity()).trainingFromExerciseDao().insertTrainingFromExercise(trainingFromExercise);
                getActivity().getSupportFragmentManager().findFragmentByTag("trainingFrag").onStart();
                getActivity().onBackPressed();
            }
        });

        return binding.getRoot();
    }
}