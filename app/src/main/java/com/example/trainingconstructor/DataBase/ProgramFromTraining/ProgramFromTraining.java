package com.example.trainingconstructor.DataBase.ProgramFromTraining;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "program_from_trainings_table")
public class ProgramFromTraining{

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int trainingId;
    private int programId;
    private int typeDay;
    private int numberCycle;

    public ProgramFromTraining(int id, int trainingId, int programId, int typeDay, int numberCycle) {
        this.id = id;
        this.trainingId = trainingId;
        this.programId = programId;
        this.typeDay = typeDay;
        this.numberCycle = numberCycle;
    }

    @Ignore
    public ProgramFromTraining(int trainingId, int programId, int typeDay, int numberCycle) {
        this.id = id;
        this.trainingId = trainingId;
        this.programId = programId;
        this.typeDay = typeDay;
        this.numberCycle = numberCycle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(int trainingId) {
        this.trainingId = trainingId;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getTypeDay() {
        return typeDay;
    }

    public void setTypeDay(int typeDay) {
        this.typeDay = typeDay;
    }

    public int getNumberCycle() {
        return numberCycle;
    }

    public void setNumberCycle(int numberCycle) {
        this.numberCycle = numberCycle;
    }
}
