package com.example.trainingconstructor.DataBase.Training;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TrainingDao {

    @Insert
    void insertAll(Training... trainings);

    @Insert
    void insertTraining(Training training);

    @Delete
    void delete(Training training);

    @Query("DELETE FROM training_table")
    void deleteAll();

    @Query("DELETE FROM training_table WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT*FROM training_table")
    List<Training> getAllTraining();

    @Query("SELECT*FROM training_table WHERE id = :id")
    Training getTrainingByID(int id);

    @Query("SELECT * FROM training_table")
    LiveData<List<Training>> getAllTrainingLiveData();


}
