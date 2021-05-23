package com.example.trainingconstructor.DataBase.CalendarEvent;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "calendar_event_table")
public class CalendarEvent {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int type;
    private int date_day;
    private int date_month;
    private int date_year;
    private int program_id;
    private int training_id;

    public CalendarEvent(int id, int type, int date_day, int date_month, int date_year, int program_id, int training_id) {
        this.id = id;
        this.type = type;
        this.date_day = date_day;
        this.date_month = date_month;
        this.date_year = date_year;
        this.program_id = program_id;
        this.training_id = training_id;
    }

    @Ignore
    public CalendarEvent(int type, int date_day, int date_month, int date_year, int program_id, int training_id) {
        this.id = id;
        this.type = type;
        this.date_day = date_day;
        this.date_month = date_month;
        this.date_year = date_year;
        this.program_id = program_id;
        this.training_id = training_id;
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

    public int getDate_day() {
        return date_day;
    }

    public void setDate_day(int date_day) {
        this.date_day = date_day;
    }

    public int getDate_month() {
        return date_month;
    }

    public void setDate_month(int date_month) {
        this.date_month = date_month;
    }

    public int getDate_year() {
        return date_year;
    }

    public void setDate_year(int date_year) {
        this.date_year = date_year;
    }

    public int getProgram_id() {
        return program_id;
    }

    public void setProgram_id(int program_id) {
        this.program_id = program_id;
    }

    public int getTraining_id() {
        return training_id;
    }

    public void setTraining_id(int training_id) {
        this.training_id = training_id;
    }
}
