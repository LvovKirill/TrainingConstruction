package com.example.trainingconstructor.DataBase.TrainingFromExercise;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.trainingconstructor.DataBase.Training.Training;

import java.util.List;


public class TrainingFromExerciseViewModel extends AndroidViewModel {

    private TrainingFromExerciseRepository mRepository;

    private final LiveData<List<TrainingFromExercise>> mAllTrainingFromExercises;

    public TrainingFromExerciseViewModel (Application application) {
        super(application);
        mRepository = new TrainingFromExerciseRepository(application);
        mAllTrainingFromExercises = mRepository.getAllTrainingFromExercises();
    }

    public LiveData<List<TrainingFromExercise>> getAllTrainingFromExercises() { return mAllTrainingFromExercises; }

    public LiveData<List<TrainingFromExercise>> getTrainingFromExercisesFromTrainingNumber(int trainingNumber) {
        return mRepository.getTrainingFromExerciseFromTrainingNumber(trainingNumber); }

    public List<TrainingFromExercise> getTrainingFromExerciseFromTrainingId(int trainingId) {
        return mRepository.getTrainingFromExerciseFromTrainingId(trainingId); }

    public LiveData<TrainingFromExercise> getOneTrainingFromExercises(int trainingID, int exerciseID) {
        return mRepository.getOneTrainingFromExercises(trainingID, exerciseID); }

    public TrainingFromExercise getTrainingFromExerciseFromId(int id) {
        return mRepository.getTrainingFromExerciseFromId(id); }

    public void insert(TrainingFromExercise trainingFromExercise) { mRepository.insert(trainingFromExercise); }

    public void deleteById(int id) { mRepository.deleteByID(id); }
}