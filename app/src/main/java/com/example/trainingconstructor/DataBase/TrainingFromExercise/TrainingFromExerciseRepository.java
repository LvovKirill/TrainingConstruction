package com.example.trainingconstructor.DataBase.TrainingFromExercise;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;

import java.util.List;


class TrainingFromExerciseRepository {
    
    private TrainingFromExerciseDao mTrainingFromExerciseDao;
    private LiveData<List<TrainingFromExercise>> mAllTrainingFromExercises;


    TrainingFromExerciseRepository(Application application) {
        DataBase db = DataBase.getDatabase(application);
        mTrainingFromExerciseDao = db.trainingFromExerciseDao();
        mAllTrainingFromExercises = mTrainingFromExerciseDao.getAllTrainingFromExerciseLiveData();
    }

    LiveData<List<TrainingFromExercise>> getAllTrainingFromExercises() {
        return mAllTrainingFromExercises;
    }


    LiveData<TrainingFromExercise> getOneTrainingFromExercises(int trainingID, int exerciseID) {
        return mTrainingFromExerciseDao.getOneTrainingFromExercises(trainingID, exerciseID);
    }

    LiveData<List<TrainingFromExercise>> getTrainingFromExerciseFromTrainingNumber(int trainingNumber) {
        return mTrainingFromExerciseDao.getTrainingFromExerciseFromTrainingNumber(trainingNumber);
    }

    List<TrainingFromExercise> getTrainingFromExerciseFromTrainingId(int trainingId) {
        return mTrainingFromExerciseDao.getTrainingFromExerciseFromTrainingId(trainingId);
    }

    void insert(TrainingFromExercise trainingFromExercise) {
        DataBase.databaseWriteExecutor.execute(() -> {
            mTrainingFromExerciseDao.insertTrainingFromExercise(trainingFromExercise);
        });
    }

    void deleteByID(int id) {
        DataBase.databaseWriteExecutor.execute(() -> {
            mTrainingFromExerciseDao.deleteById(id);
        });
    }

}