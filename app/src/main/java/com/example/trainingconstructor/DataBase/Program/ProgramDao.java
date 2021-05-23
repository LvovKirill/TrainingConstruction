package com.example.trainingconstructor.DataBase.Program;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.trainingconstructor.DataBase.Program.Program;

import java.util.List;

@Dao
public interface ProgramDao {

    @Insert
    void insertAll(Program... programs);

    @Insert
    void insertProgram(Program program);

    @Delete
    void delete(Program program);

    @Query("DELETE FROM program_table")
    void deleteAll();

    @Query("DELETE FROM program_table WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT*FROM program_table WHERE id = :id")
    Program getProgramById(int id);

    @Query("SELECT*FROM program_table")
    List<Program> getAllProgram();

    @Query("SELECT * FROM program_table")
    LiveData<List<Program>> getAllProgramLiveData();


}
