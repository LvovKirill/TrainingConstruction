package com.example.trainingconstructor.DataBase.Exercise;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.trainingconstructor.DataBase.Exercise.Exercise;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    void insertAll(Exercise... exercises);

    @Insert
    void insertExercise(Exercise exercise);

    @Delete
    void delete(Exercise exercise);

    @Query("DELETE FROM exercise_table")
    void deleteAll();

    @Query("DELETE FROM exercise_table WHERE id = :id")
    void deleteByID(int id);

    @Query("SELECT*FROM exercise_table")
    List<Exercise> getAllExercise();

    @Query("SELECT*FROM exercise_table WHERE id = :id")
    Exercise getExerciseByID(Integer id);

    @Query("SELECT name FROM exercise_table WHERE id = :id")
    String getNameByID(Integer id);

    @Query("SELECT * FROM exercise_table")
    LiveData<List<Exercise>> getAllExerciseLiveData();



}


