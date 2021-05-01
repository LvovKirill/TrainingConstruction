package com.example.trainingconstructor.ui.TabataTimerScreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.Exercise.ExerciseViewModel;
import com.example.trainingconstructor.DataBase.Training.TrainingViewModel;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExerciseViewModel;
import com.example.trainingconstructor.R;


import com.example.trainingconstructor.databinding.FragmentTabataTimerBinding;
import com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen.MyExerciseFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.MyTrainingFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.TrainingFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.TrainingFromExerciseAdapter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import static com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.TrainingFragment.trainingFromExerciseViewModel;


public class TabataTimerFragment extends Fragment{


    FragmentTabataTimerBinding binding;
    protected ExerciseViewModel exerciseViewModel;
    protected TrainingViewModel trainingViewModel;
    TrainingFromExerciseViewModel trainingFromExerciseViewModel;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private int currentPosition=0;
    List<TrainingFromExercise> list;

    private TextToSpeech tts;

    private static final long START_TIME_IN_MILLIS = 10000;
    private long mTimeLeftInMillis;


    public static TabataTimerFragment newInstance(int id) {
        TabataTimerFragment fragment = new TabataTimerFragment();
        Bundle args = new Bundle();
        args.putInt("ID", id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTabataTimerBinding.inflate(inflater, container, false);

        final TrainingFromExerciseAdapter adapter = new TrainingFromExerciseAdapter(new TrainingFromExerciseAdapter.TrainingFromExerciseDiff(), getContext());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.isScrollbarFadingEnabled();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        trainingFromExerciseViewModel = new ViewModelProvider(this).get(TrainingFromExerciseViewModel.class);
        list = DataBase.getDatabase(getActivity()).trainingFromExerciseDao().getTrainingFromExerciseFromTrainingId(getArguments().getInt("ID"));

        DataBase.getDatabase(getActivity()).trainingFromExerciseDao().getTrainingFromExerciseFromTrainingNumber(getArguments().getInt("ID")).observe(getViewLifecycleOwner(), exercises -> {
            adapter.submitList(exercises);
        });

//        mTimeLeftInMillis =

        startTimer();

        String currentName = DataBase.getDatabase(getActivity()).exerciseDao().getNameByID(list.get(currentPosition).getExerciseId());
        binding.nameCurrent.setText(currentName);


        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS){
                    Locale localeDef = Locale.getDefault();
                    Locale locale = new Locale("ru");
                    int lang = tts.setLanguage(locale);
                }

            }
        });

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

        binding.finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountDownTimer.onFinish();
            }
        });


        return binding.getRoot();
    }


    private void startTimer() {
        String currentName = DataBase.getDatabase(getActivity()).exerciseDao().getNameByID(list.get(currentPosition).getExerciseId());
        int currentRepeat = list.get(currentPosition).getRepeat();
        int currentWeight = list.get(currentPosition).getWeight();
        int currentTime = list.get(currentPosition).getTime();
        binding.nameCurrent.setText(currentName);
        binding.counterRepeat.setText(Integer.toString(currentRepeat)+" x");
        binding.counterWeight.setText(Integer.toString(currentWeight)+" кг");

        if(currentPosition!=0) {
            int speech = tts.speak(currentName + currentTime + "минут" + currentRepeat + "раз", TextToSpeech.QUEUE_FLUSH, null);
        }

        mCountDownTimer = new CountDownTimer(getInfo().getTime()*60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                if ((int) (mTimeLeftInMillis / 1000) % 60 == 3 ||(int) (mTimeLeftInMillis / 1000) % 60 % 60 == 2 ||(int) (mTimeLeftInMillis / 1000) % 60==1 ) {
                    int speech = tts.speak("пип", TextToSpeech.QUEUE_FLUSH, null);
                }
                if ((int) (mTimeLeftInMillis / 1000) % 60 == 0) {
                    int speech = tts.speak("всё", TextToSpeech.QUEUE_FLUSH, null);
                }
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                currentPosition++;
                if(currentPosition+1<=list.size()){startTimer();
                    }
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

    private TrainingFromExercise getInfo(){
        return list.get(currentPosition);
    }
}