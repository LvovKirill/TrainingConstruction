package com.example.trainingconstructor.ui.ConstructionScreen.ProgramScreen;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingconstructor.DataBase.CalendarEvent.CalendarEvent;
import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.DataBase.Program.Program;
import com.example.trainingconstructor.DataBase.ProgramFromTraining.ProgramFromTraining;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentProgramBinding;
import com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen.MyExerciseFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.TrainingFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.slider.LabelFormatter;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class ProgramFragment extends Fragment {

    FragmentProgramBinding binding;
    Calendar cursorDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProgramBinding.inflate(inflater, container, false);

        update();

        binding.planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNewCalendarPoint();
            }
        });

        return binding.getRoot();
    }


    public static ProgramFragment newInstance(int id) {
        ProgramFragment programFragment = new ProgramFragment();
        Bundle args = new Bundle();
        args.putInt("ID", id);
        programFragment.setArguments(args);
        return programFragment;
    }

    class scheduleDrawer{
        final int cycle;
        List<ProgramFromTraining> weekList = new ArrayList<>();

        scheduleDrawer(int cycle){
            this.cycle = cycle;
            addWeekList(cycle);
            comparingWeekList();
        }

        void addWeekList(int cycle){
            int programId = getArguments().getInt("ID");
            for (int i=0; i<cycle; i++) {
                        weekList.add(new ProgramFromTraining(0,programId, 1, i+1));
                        weekList.add(new ProgramFromTraining(0,programId, 2, i+1));
                        weekList.add(new ProgramFromTraining(0,programId, 3, i+1));
                        weekList.add(new ProgramFromTraining(0,programId, 4, i+1));
                        weekList.add(new ProgramFromTraining(0,programId, 5, i+1));
                        weekList.add(new ProgramFromTraining(0,programId, 6, i+1));
                        weekList.add(new ProgramFromTraining(0,programId, 7, i+1));

            }
        }

        void comparingWeekList(){
            List<ProgramFromTraining> weekListDB = DataBase.getDatabase(getActivity())
                    .programFromTraining().getProgramFromTrainingByProgramId(getArguments().getInt("ID"));

            for(ProgramFromTraining programFromTraining: weekListDB){
                int position = ((programFromTraining.getNumberCycle()-1)*7 + programFromTraining.getTypeDay())-1;
                weekList.remove(position);
                weekList.add(programFromTraining);
                Collections.sort(weekList, new ProgramFromTrainingComparator());
            }
        }

        public class ProgramFromTrainingComparator implements Comparator<ProgramFromTraining> {
            @Override
            public int compare(ProgramFromTraining o1, ProgramFromTraining o2) {
                int positon1 = (o1.getNumberCycle()-1)*7 + o1.getTypeDay();
                int positon2 = (o2.getNumberCycle()-1)*7 + o2.getTypeDay();
                if(positon1<positon2) return -1;
                else if (positon1>positon2) return 1;
                else return 0;
            }
        }
    }


    void createPie(DataCounter dataCounter){

        //BarChart

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(4f, dataCounter.day1));
        barEntries.add(new BarEntry(8f, dataCounter.day2));
        barEntries.add(new BarEntry(12f, dataCounter.day3));
        barEntries.add(new BarEntry(16f, dataCounter.day4));
        barEntries.add(new BarEntry(20f, dataCounter.day5));
        barEntries.add(new BarEntry(24f, dataCounter.day6));
        barEntries.add(new BarEntry(28f, dataCounter.day7));


        if(dataCounter.day1==0 && dataCounter.day2==0
                && dataCounter.day3==0 && dataCounter.day4==0 && dataCounter.day5==0 && dataCounter.day6==0){
//            binding.emptyPie.setText(getString(R.string.emptyPie));
        }

        BarDataSet barDataSetChart = new BarDataSet(barEntries, "pieArray");


        int colorSetForPie[] = {Color.rgb(253,151,39), Color.rgb(103,63,180), Color.rgb(204,217,72), Color.rgb(44,152,240)};
        barDataSetChart.setColors(ColorTemplate.createColors(colorSetForPie));
        barDataSetChart.setValueTextColor(Color.BLACK);
//        barDataSetChart.setDrawValues(true);
//
//        barDataSetChart.setDrawValues(true);
        BarData barDataPie = new BarData(barDataSetChart);


        barDataPie.setValueTextSize(10f);
        barDataPie.setValueTextColor(Color.BLACK);
//        barDataPie.setDrawValues(false);
        barDataPie.setHighlightEnabled(true);
        binding.barChart.setData(barDataPie);
        binding.barChart.setScaleXEnabled(false);
        binding.barChart.setScaleYEnabled(false);
        binding.barChart.getXAxis().setEnabled(false);
        binding.barChart.getAxisLeft().setEnabled(false);
        binding.barChart.getAxisRight().setEnabled(false);
        binding.barChart.getAxisRight().setEnabled(false);
        binding.barChart.getDescription().setEnabled(false);
        binding.barChart.getLegend().setEnabled(false);
        binding.barChart.animate();
        binding.barChart.setDrawingCacheEnabled(false);
        binding.barChart.setDragDecelerationEnabled(true);
        binding.barChart.invalidate();
    }



    class DataCounter {
        float day1=0;
        float day2=0;
        float day3=0;
        float day4=0;
        float day5=0;
        float day6=0;
        float day7=0;

        float countTraining=0;

        List<ProgramFromTraining> list;

        DataCounter(){update();}

        void update(){

                list = DataBase.getDatabase(getActivity()).programFromTraining().getProgramFromTrainingByProgramId(getArguments().getInt("ID"));
                for(ProgramFromTraining programFromTraining:  list){
                    if(programFromTraining.getTrainingId()!=0 && programFromTraining.getTypeDay()==1){day1++;}
                    if(programFromTraining.getTrainingId()!=0 && programFromTraining.getTypeDay()==2){day2++;}
                    if(programFromTraining.getTrainingId()!=0 && programFromTraining.getTypeDay()==3){day3++;}
                    if(programFromTraining.getTrainingId()!=0 && programFromTraining.getTypeDay()==4){day4++;}
                    if(programFromTraining.getTrainingId()!=0 && programFromTraining.getTypeDay()==5){day5++;}
                    if(programFromTraining.getTrainingId()!=0 && programFromTraining.getTypeDay()==6){day6++;}
                    if(programFromTraining.getTrainingId()!=0 && programFromTraining.getTypeDay()==7){day7++;}
                }
                countTraining=day1+day2+day3+day4+day5+day6+day7;
        }
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

        List<ProgramFromTraining> list = DataBase.getDatabase(getActivity()).programFromTraining()
                .getProgramFromTrainingByProgramId(getArguments().getInt("ID"));

        for(ProgramFromTraining programFromTraining: list){
            int position = (calendar.get(calendar.DAY_OF_MONTH)+(programFromTraining.getNumberCycle()-1)*7 + programFromTraining.getTypeDay())-1;
            dateHashMap.put(position, "present");
        }

        Program program = DataBase.getDatabase(getActivity()).programDao().getProgramById(getArguments().getInt("ID"));

        for(int date=calendar.get(calendar.DAY_OF_MONTH); date<program.getCycle()*7+calendar.get(calendar.DAY_OF_MONTH); date++){
            boolean type=false;
            for(ProgramFromTraining programFromTraining: list){
                if(calendar.get(calendar.DAY_OF_MONTH)+((programFromTraining.getNumberCycle()-1)*7 + programFromTraining.getTypeDay())-1==date) {
                    type = true;
                    break;
                }
            }
            if(type){dateHashMap.put(date, "present");}
            else dateHashMap.put(date, "absent");
        }

        dateHashMap.put(calendar.get(calendar.DAY_OF_MONTH), "current");
        customCalendar.setDate(calendar, dateHashMap);
        customCalendar.getMonthYearTextView().setTextColor(Color.BLACK);


        customCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {
                List<ProgramFromTraining> list = DataBase.getDatabase(getActivity()).programFromTraining()
                        .getProgramFromTrainingByProgramId(getArguments().getInt("ID"));

                cursorDate=selectedDate;

                Program program = DataBase.getDatabase(getActivity()).programDao().getProgramById(getArguments().getInt("ID"));

                dateHashMap.clear();

                for(int date=selectedDate.get(Calendar.DAY_OF_MONTH); date<program.getCycle()*7+selectedDate.get(Calendar.DAY_OF_MONTH); date++){
                    boolean type=false;
                    for(ProgramFromTraining programFromTraining: list){
                        if(selectedDate.get(Calendar.DAY_OF_MONTH)+((programFromTraining.getNumberCycle()-1)*7 + programFromTraining.getTypeDay())-1==date) {
                            type = true;
                            break;
                        }
                    }
                    if(type){dateHashMap.put(date, "present");}
                    else dateHashMap.put(date, "absent");
                }

                dateHashMap.put(selectedDate.get(Calendar.DAY_OF_MONTH), "current");
                customCalendar.setDate(calendar, dateHashMap);
                customCalendar.getMonthYearTextView().setTextColor(Color.BLACK);
            }
        });
    }

    void insertNewCalendarPoint(){
        List<ProgramFromTraining> list = DataBase.getDatabase(getActivity()).programFromTraining()
                .getProgramFromTrainingByProgramId(getArguments().getInt("ID"));

        int programID=getArguments().getInt("ID");
        Program program = DataBase.getDatabase(getActivity()).programDao().getProgramById(programID);

        List<CalendarEvent> calendarEventList = new ArrayList<>();

        for(int date=cursorDate.get(Calendar.DAY_OF_MONTH); date<program.getCycle()*7+cursorDate.get(Calendar.DAY_OF_MONTH); date++){
            boolean type = false;
            for(ProgramFromTraining programFromTraining: list){
                if(cursorDate.get(Calendar.DAY_OF_MONTH)+((programFromTraining.getNumberCycle()-1)*7 + programFromTraining.getTypeDay())-1==date) {
                    calendarEventList.add(new CalendarEvent(1, date, cursorDate.get(Calendar.MONTH)+1, cursorDate.get(Calendar.YEAR), programID, programFromTraining.getTrainingId()));
                    type=true;
                    break;
                }
            }
            if(!type)
            calendarEventList.add(new CalendarEvent(2, date, cursorDate.get(Calendar.MONTH)+1, cursorDate.get(Calendar.YEAR), programID, 0));
        }

        for(CalendarEvent calendarEvent: calendarEventList){
            DataBase.getDatabase(getActivity()).calendarEventDao().insertCalendarEvent(calendarEvent);
        }
    }

    public void update(){
        Program program = DataBase.getDatabase(getActivity()).programDao().getProgramById(getArguments().getInt("ID"));
        int cycle = program.getCycle();
        binding.programImage.setImageResource(program.getImg_id());

        scheduleDrawer scheduleDrawer = new scheduleDrawer(cycle);
        DataCounter dataCounter = new DataCounter();
        createPie(dataCounter);

        createCalendar(binding.calendar);

        binding.recyclerView.isScrollbarFadingEnabled();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ProgramFromTrainingAdapter recyclerAdapter = new ProgramFromTrainingAdapter(scheduleDrawer.weekList, getActivity());
        binding.recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        update();
    }
}