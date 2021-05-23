package com.example.trainingconstructor.DataBase.ProgramFromTraining;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.trainingconstructor.DataBase.ProgramFromTraining.ProgramFromTraining;

import java.util.List;

@Dao
public interface ProgramFromTrainingDao {

    @Insert
    void insertAll(ProgramFromTraining... programFromTrainings);

    @Insert
    void insertProgramFromTraining(ProgramFromTraining programFromTrainings);

    @Delete
    void delete(ProgramFromTraining programFromTrainings);

    @Query("DELETE FROM program_from_trainings_table")
    void deleteAll();

    @Query("DELETE FROM program_from_trainings_table WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT*FROM program_from_trainings_table")
    List<ProgramFromTraining> getAllProgramFromTraining();

    @Query("SELECT*FROM program_from_trainings_table WHERE programId = :programId")
    List<ProgramFromTraining> getProgramFromTrainingByProgramId(int programId);

    @Query("SELECT*FROM program_from_trainings_table WHERE id = :id")
    ProgramFromTraining getProgramFromTrainingByID(int id);

    @Query("SELECT * FROM program_from_trainings_table")
    LiveData<List<ProgramFromTraining>> getAllProgramFromTrainingLiveData();


}
