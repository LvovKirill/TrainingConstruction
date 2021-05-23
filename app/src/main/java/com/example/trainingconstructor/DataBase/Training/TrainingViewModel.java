package com.example.trainingconstructor.DataBase.Training;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TrainingViewModel extends AndroidViewModel {

    private TrainingRepository mRepository;

    private final LiveData<List<Training>> mAllTrainings;

    public TrainingViewModel (Application application) {
        super(application);
        mRepository = new TrainingRepository(application);
        mAllTrainings = mRepository.getAllTrainings();
    }

    public LiveData<List<Training>> getAllTrainings() { return mAllTrainings; }

    public Training getTrainingByID(int id) { return mRepository.getTrainingByID(id); }

    public void insert(Training training) { mRepository.insert(training); }

    public void deleteById(int id) { mRepository.deleteByID(id); }
}
