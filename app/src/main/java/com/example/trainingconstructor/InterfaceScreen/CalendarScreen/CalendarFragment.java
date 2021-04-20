package com.example.trainingconstructor.InterfaceScreen.CalendarScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.trainingconstructor.R;

public class CalendarFragment extends Fragment {

    CalendarView calendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kalendar, container, false);

        calendarView = rootView.findViewById(R.id.kalendar);

        calendarView.setWeekNumberColor(1);

        return rootView;
    }
}