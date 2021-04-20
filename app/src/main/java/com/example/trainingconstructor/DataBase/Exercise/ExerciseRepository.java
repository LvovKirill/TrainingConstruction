package com.example.trainingconstructor.DataBase.Exercise;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.DataBase.Exercise.ExerciseDao;

import java.util.List;

class ExerciseRepository {

    private ExerciseDao mExerciseDao;
    private LiveData<List<Exercise>> mAllExercises;

    ExerciseRepository(Application application) {
        DataBase db = DataBase.getDatabase(application);
        mExerciseDao = db.exerciseDao();
        mAllExercises = mExerciseDao.getAllExerciseLiveData();
    }

    LiveData<List<Exercise>> getAllExercises() {
        return mAllExercises;
    }

    Exercise getExerciseByID(Integer id) {
        return mExerciseDao.getExerciseByID(id);
    }

    String getNameByID(Integer id) {
        return mExerciseDao.getNameByID(id);
    }

    void insert(Exercise exercise) {
        DataBase.databaseWriteExecutor.execute(() -> {
            mExerciseDao.insertExercise(exercise);
        });
    }

    void deleteByID(int id) {
        DataBase.databaseWriteExecutor.execute(() -> {
            mExerciseDao.deleteByID(id);
        });
    }
}
