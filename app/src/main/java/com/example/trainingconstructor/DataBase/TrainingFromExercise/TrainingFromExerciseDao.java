package com.example.trainingconstructor.DataBase.TrainingFromExercise;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;

import java.util.List;


@Dao
public interface TrainingFromExerciseDao {

    @Insert
    void insertAll(TrainingFromExercise... trainingFromExercises);

    @Insert
    void insertTrainingFromExercise(TrainingFromExercise trainingFromExercise);

    @Delete
    void delete(TrainingFromExercise trainingFromExercise);

    @Update
    int updateTrainingFromExercise(TrainingFromExercise trainingFromExercise);

    @Query("DELETE FROM training_from_exercise_table")
    void deleteAll();

    @Query("DELETE FROM training_from_exercise_table WHERE trainingId = :trainingId")
    void deleteById(int trainingId);

    @Query("SELECT*FROM training_from_exercise_table WHERE trainingId = :trainingId AND trainingId = :exerciseId" )
    LiveData<TrainingFromExercise> getOneTrainingFromExercises(int trainingId, int exerciseId);

    @Query("SELECT*FROM training_from_exercise_table WHERE trainingId = :trainingId")
    LiveData<List<TrainingFromExercise>> getTrainingFromExerciseFromTrainingNumber(int trainingId);

    @Query("SELECT * FROM training_from_exercise_table")
    LiveData<List<TrainingFromExercise>> getAllTrainingFromExerciseLiveData();

    @Query("SELECT * FROM training_from_exercise_table WHERE trainingId = :trainingId")
    List<TrainingFromExercise> getTrainingFromExerciseFromTrainingId(int trainingId);

    @Query("SELECT * FROM training_from_exercise_table WHERE trainingId = :id")
    TrainingFromExercise getTrainingFromExerciseFromId(int id);


}
