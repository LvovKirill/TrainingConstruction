package com.example.calendarEventconstructor.DataBase.CalendarEvent;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.trainingconstructor.DataBase.CalendarEvent.CalendarEvent;

import java.util.List;

@Dao
public interface CalendarEventDao {

    @Insert
    void insertAll(CalendarEvent... calendarEvent);

    @Insert
    void insertCalendarEvent(CalendarEvent calendarEvent);

    @Delete
    void delete(CalendarEvent calendarEvent);

    @Query("DELETE FROM calendar_event_table")
    void deleteAll();

    @Query("DELETE FROM calendar_event_table WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT*FROM calendar_event_table WHERE date_month = :month")
    List<CalendarEvent> getByDateMonth(int month);

    @Query("SELECT*FROM calendar_event_table WHERE date_month = :month AND date_day = :day")
    List<CalendarEvent> getByDayAndMonth(int month, int day);

    @Query("SELECT*FROM calendar_event_table")
    List<CalendarEvent> getAllCalendarEvent();

    @Query("SELECT*FROM calendar_event_table WHERE id = :id")
    CalendarEvent getCalendarEventByID(int id);

    @Query("SELECT * FROM calendar_event_table")
    LiveData<List<CalendarEvent>> getAllCalendarEventLiveData();
    
}

