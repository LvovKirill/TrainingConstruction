package com.example.trainingconstructor.DataBase.Program;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

    @Entity(tableName = "program_table")
    public class Program {

        @PrimaryKey(autoGenerate = true)
        int id;
        private String name;
        private String about;
        private int cycle;
        private int count_training;
        private float complexity;

        public Program(int id, String name, String about, int cycle, int count_training, float complexity) {
            this.id = id;
            this.name = name;
            this.about = about;
            this.cycle = cycle;
            this.count_training = count_training;
            this.complexity = complexity;
        }


        @Ignore
        public Program(String name, String about, int cycle, int count_training, float complexity) {
            this.name = name;
            this.about = about;
            this.cycle = cycle;
            this.count_training = count_training;
            this.complexity = complexity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public int getCycle() {
            return cycle;
        }

        public void setCycle(int cycle) {
            this.cycle = cycle;
        }

        public int getCount_training() {
            return count_training;
        }

        public void setCount_training(int count_training) {
            this.count_training = count_training;
        }

        public int getId() {
            return id;
        }

        public float getComplexity() {
            return complexity;
        }

        public void setComplexity(float complexity) {
            this.complexity = complexity;
        }
    }
