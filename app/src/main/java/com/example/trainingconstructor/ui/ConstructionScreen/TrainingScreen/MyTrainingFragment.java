package com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.example.trainingconstructor.DataBase.Training.Training;
import com.example.trainingconstructor.DataBase.Training.TrainingViewModel;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentMyTrainingBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class MyTrainingFragment extends Fragment {

    FragmentMyTrainingBinding binding;
    public static TrainingViewModel trainingViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMyTrainingBinding.inflate(inflater, container, false);

        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(binding.myTrainingrecyclerview.getContext(), R.anim.layout_animation_fall_down);
        binding.myTrainingrecyclerview.setLayoutAnimation(layoutAnimationController);

        final TrainingListAdapter adapter = new TrainingListAdapter(new TrainingListAdapter.TrainingDiff(), getActivity());
        binding.myTrainingrecyclerview.setAdapter(adapter);


        binding.myTrainingrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        trainingViewModel = new ViewModelProvider(this).get(TrainingViewModel.class);

        trainingViewModel.getAllTrainings().observe(getViewLifecycleOwner(), trainings -> {
            adapter.submitList(trainings);
        });

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateTrainingFragment youFragment = CreateTrainingFragment.newInstance();
                getActivity().getSupportFragmentManager().popBackStack();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.frameLayout, youFragment, "createTrainingFrag")
                        .setCustomAnimations(R.anim.layout_animation_fall_down, R.anim.layout_animation_fall_down)
                        .addToBackStack("createTrainingFrag")
                        .commit();
            }
        });

        return binding.getRoot();
    }


    private void runLayoutAnimation(RecyclerView recyclerView){

        Context context = recyclerView.getContext();

        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();

    }


    public static void addTraining(Training training){
        trainingViewModel.insert(training);
    }

}