package com.example.trainingconstructor.DataBase.TrainingFromExercise;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.DataBase.Training.Training;



    @Entity(tableName = "training_from_exercise_table"
//            ,
//            primaryKeys = { "exerciseId", "trainingId"},
//            foreignKeys = {
//                    @ForeignKey(entity = Exercise.class, parentColumns = "id", childColumns = "exerciseId"),
//                    @ForeignKey(entity = Training.class, parentColumns = "id", childColumns = "trainingId")
//            }
            )
    public class TrainingFromExercise  {

        @PrimaryKey (autoGenerate = true)
        private int id;
    private int exerciseId;
    private int trainingId;
    private int repeat;
    private int weight;
    private int item;
    private int time;
    private int type;
    private int numberInTraining;


        public TrainingFromExercise(int id, int exerciseId, int trainingId, int repeat, int weight, int time, int item, int type, int numberInTraining) {
            this.id = id;
            this.exerciseId = exerciseId;
            this.trainingId = trainingId;
            this.repeat = repeat;
            this.weight = weight;
            this.item = item;
            this.time = time;
            this.type = type;
            this.numberInTraining = numberInTraining;
        }

        @Ignore
    public TrainingFromExercise(int exerciseId, int trainingId, int repeat, int weight, int time, int item, int type, int numberInTraining) {
        this.exerciseId = exerciseId;
        this.trainingId = trainingId;
        this.repeat = repeat;
        this.weight = weight;
        this.item = item;
        this.time = time;
        this.type = type;
        this.numberInTraining = numberInTraining;
    }

        public int getExerciseId() {
            return exerciseId;
        }

        public void setExerciseId(int exerciseId) {
            this.exerciseId = exerciseId;
        }

        public int getTrainingId() {
            return trainingId;
        }

        public void setTrainingId(int trainingId) {
            this.trainingId = trainingId;
        }

        public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getNumberInTraining() {
            return numberInTraining;
        }

        public void setNumberInTraining(int numberInTraining) {
            this.numberInTraining = numberInTraining;
        }
    }