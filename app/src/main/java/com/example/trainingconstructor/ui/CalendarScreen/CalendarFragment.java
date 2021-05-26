package com.example.trainingconstructor.ui.CalendarScreen;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.trainingconstructor.DataBase.CalendarEvent.CalendarEvent;
import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.Program.Program;
import com.example.trainingconstructor.DataBase.ProgramFromTraining.ProgramFromTraining;
import com.example.trainingconstructor.DataBase.Training.Training;
import com.example.trainingconstructor.DataBase.Training.TrainingViewModel;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExerciseViewModel;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentKalendarBinding;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.TrainingFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.TrainingFromExerciseAdapter;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.TrainingListAdapter;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.OnNavigationButtonClickedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarFragment extends Fragment {

    FragmentKalendarBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentKalendarBinding.inflate(inflater, container, false);

        final TrainingListAdapter adapter = new TrainingListAdapter(new TrainingListAdapter.TrainingDiff(), getActivity());
        binding.calendarRecyclerView.setAdapter(adapter);


        binding.calendarRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        List<Training> trainings =
//
//        trainingViewModel = new ViewModelProvider(this).get(TrainingViewModel.class);
//
//        trainingViewModel.getAllTrainings().observe(getViewLifecycleOwner(), trainings -> {
//            adapter.submitList(trainings);
//        });

        createCalendar(binding.calendar);

        Calendar calendar = Calendar.getInstance();

        List<CalendarEvent> calendarEventList = DataBase.getDatabase(getActivity()).calendarEventDao()
                .getByDayAndMonth(calendar.get(calendar.MONTH)+1, calendar.get(calendar.DAY_OF_MONTH));

        List<Training> trainingList = new ArrayList<>();
        for(CalendarEvent calendarEvent: calendarEventList){
            Training training = DataBase.getDatabase(getActivity()).trainingDao().getTrainingByID(calendarEvent.getTraining_id());
            trainingList.add(training);
        }

//        TrainingListAdapter recyclerAdapter = new TrainingListAdapter(trainingList, getActivity());
//        binding.recyclerView.setAdapter(recyclerAdapter);



        return binding.getRoot();
    }

    public void createCalendar(CustomCalendar customCalendar){
        HashMap<Object, Property> descHashMap = new HashMap<>();

        Property defaultProperty = new Property();
        defaultProperty.layoutResource = R.layout.default_view;
        defaultProperty.dateTextViewResource = R.id.textView;
        descHashMap.put("default", defaultProperty);

        Property currentProperty = new Property();
        currentProperty.layoutResource = R.layout.current_view;
        currentProperty.dateTextViewResource = R.id.textView;
        descHashMap.put("current", currentProperty);

        Property presentProperty = new Property();
        presentProperty.layoutResource = R.layout.present_view;
        presentProperty.dateTextViewResource = R.id.textView;
        descHashMap.put("present", presentProperty);

        Property absentProperty = new Property();
        absentProperty.layoutResource = R.layout.absent_view;
        absentProperty.dateTextViewResource = R.id.textView;
        descHashMap.put("absent", absentProperty);

        customCalendar.setMapDescToProp(descHashMap);
        Calendar calendar = Calendar.getInstance();
        HashMap<Integer, Object> dateHashMap = new HashMap<>();

        dateHashMap.put(calendar.get(calendar.DAY_OF_MONTH), "current");

        List<CalendarEvent> list = DataBase.getDatabase(getActivity()).calendarEventDao().getByDateMonth(calendar.get(calendar.MONTH)+1);

        for(CalendarEvent calendarEvent: list){
            if(calendarEvent.getType()==1) {
                dateHashMap.put(calendarEvent.getDate_day(), "present");
            }else dateHashMap.put(calendarEvent.getDate_day(), "absent");
        }


        dateHashMap.put(calendar.get(calendar.DAY_OF_MONTH), "current");
        customCalendar.setDate(calendar, dateHashMap);
        customCalendar.getMonthYearTextView().setTextColor(Color.BLACK);

        customCalendar.setOnNavigationButtonClickedListener(1, new OnNavigationButtonClickedListener() {
            @Override
            public Map<Integer, Object>[] onNavigationButtonClicked(int whichButton, Calendar newMonth) {
                return new Map[0];
            }
        });
    }
}