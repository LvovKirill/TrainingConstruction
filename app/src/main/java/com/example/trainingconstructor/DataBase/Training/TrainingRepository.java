package com.example.trainingconstructor.DataBase.Training;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.trainingconstructor.DataBase.DataBase;

import java.util.List;

class TrainingRepository {

    private TrainingDao mTrainingDao;
    private LiveData<List<Training>> mAllTrainings;

    TrainingRepository(Application application) {
        DataBase db = DataBase.getDatabase(application);
        mTrainingDao = db.trainingDao();
        mAllTrainings = mTrainingDao.getAllTrainingLiveData();
    }

    LiveData<List<Training>> getAllTrainings() {
        return mAllTrainings;
    }

    void insert(Training training) {
        DataBase.databaseWriteExecutor.execute(() -> {
            mTrainingDao.insertTraining(training);
        });
    }

    Training getTrainingByID(int id) {
           return mTrainingDao.getTrainingByID(id);
    }

    void deleteByID(int id) {
        DataBase.databaseWriteExecutor.execute(() -> {
            mTrainingDao.deleteById(id);
        });
    }


}
