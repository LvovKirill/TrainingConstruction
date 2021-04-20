package com.example.trainingconstructor.DataBase.Exercise;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.DataBase.Exercise.ExerciseRepository;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {

    private ExerciseRepository mRepository;

    private final LiveData<List<Exercise>> mAllExercises;

    public ExerciseViewModel (Application application) {
        super(application);
        mRepository = new ExerciseRepository(application);
        mAllExercises = mRepository.getAllExercises();
    }

    public LiveData<List<Exercise>> getAllExercises() { return mAllExercises; }

    public Exercise getExerciseByID(Integer id) { return mRepository.getExerciseByID(id); }

    public String getNameByID(Integer id) { return mRepository.getNameByID(id); }

    public void insert(Exercise exercise) { mRepository.insert(exercise); }

    public void deleteByID(int id) { mRepository.deleteByID(id); }
}
