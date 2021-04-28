package com.example.trainingconstructor.ui.TabataTimerScreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.Exercise.ExerciseViewModel;
import com.example.trainingconstructor.DataBase.Training.TrainingViewModel;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExerciseViewModel;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentTabataTimerBinding;
import com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen.MyExerciseFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.MyTrainingFragment;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;


public class TabataTimerFragment extends Fragment {


    FragmentTabataTimerBinding binding;
    protected TrainingFromExerciseViewModel trainingFromExerciseViewModel;
    protected ExerciseViewModel exerciseViewModel;
    protected TrainingViewModel trainingViewModel;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;

    private static final long START_TIME_IN_MILLIS = 600000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


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

        String name;
        try {
            name = DataBase.getDatabase(getActivity()).trainingDao().getTrainingByID(getArguments().getInt("ID")).getName();
        }catch (Exception e){
            name="ytn";
        }

        binding.nameCurrent.setText(name);

        binding.mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        binding.mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    resetTimer();
            }
        });

        return binding.getRoot();
    }


    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                binding.mButtonStartPause.setText("старт");
                binding.mButtonStartPause.setVisibility(View.INVISIBLE);
                binding.mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();
        mTimerRunning = true;
        binding.mButtonStartPause.setText("пауза");
        binding.mButtonReset.setVisibility(View.INVISIBLE);
    }
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        binding.mButtonStartPause.setText("старт");
        binding.mButtonReset.setVisibility(View.VISIBLE);
    }
    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        binding.mButtonReset.setVisibility(View.INVISIBLE);
        binding.mButtonStartPause.setVisibility(View.VISIBLE);
    }
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        binding.counter.setText(timeLeftFormatted);
    }



}