package com.example.trainingconstructor.ui.ConstructionScreen.ProgramScreen.AddPoint;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.Exercise.ExerciseViewModel;
import com.example.trainingconstructor.DataBase.Program.Program;
import com.example.trainingconstructor.DataBase.ProgramFromTraining.ProgramFromTraining;
import com.example.trainingconstructor.DataBase.Training.Training;
import com.example.trainingconstructor.DataBase.Training.TrainingViewModel;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentAddPointProgramBinding;
import com.example.trainingconstructor.databinding.FragmentTrainingBinding;
import com.example.trainingconstructor.ui.ConstructionScreen.ProgramScreen.ProgramFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint.AddPointExersiseFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint.AddPointTrainingListAdapter;

import java.util.List;


public class AddPointProgramFragment extends Fragment {

    FragmentAddPointProgramBinding binding;
    List<Training> trainingList;
    public static TrainingViewModel trainingViewModel;
    static int currentTrainingId;

    public static AddPointProgramFragment newInstance(int typeDay, int numberCycle, int programId) {
        AddPointProgramFragment addPointProgramFragment = new AddPointProgramFragment();
        Bundle args = new Bundle();
        args.putInt("typeDay", typeDay);
        args.putInt("numberCycle", numberCycle);
        args.putInt("programId", programId);
        addPointProgramFragment.setArguments(args);
        return addPointProgramFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddPointProgramBinding.inflate(inflater, container, false);

        trainingList = DataBase.getDatabase(getActivity()).trainingDao().getAllTraining();

        final AddPointProgramListAdapter adapter = new AddPointProgramListAdapter(new AddPointProgramListAdapter.TrainingDiff(), getActivity(),
                AddPointProgramFragment.this, getArguments().getInt("typeDay"), getArguments().getInt("numberCycle"), getArguments().getInt("programId"));
        binding.myAddPointProgramRecyclerview.setAdapter(adapter);
        binding.myAddPointProgramRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        trainingViewModel = new ViewModelProvider(this).get(TrainingViewModel.class);

        trainingViewModel.getAllTrainings().observe(getViewLifecycleOwner(), trainings -> {
            adapter.submitList(trainings);
        });

        binding.addPointPFTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBase.getDatabase(getActivity()).programFromTraining().insertProgramFromTraining(new ProgramFromTraining(currentTrainingId,
                        getArguments().getInt("programId"), getArguments().getInt("typeDay"), getArguments().getInt("numberCycle")));
                getActivity().getSupportFragmentManager().findFragmentByTag("programFrag").onStart();
                getActivity().onBackPressed();
            }
        });


        return binding.getRoot();
    }

}