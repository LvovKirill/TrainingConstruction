package com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExerciseViewModel;
import com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen.MyExerciseFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint.AddPointExersiseFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint.AddPointExersisePagerFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint.AddPointExersiseRestFragment;
import com.example.trainingconstructor.ui.TabataTimerScreen.TabataTimerFragment;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentTrainingBinding;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainingFragment extends Fragment {

    FragmentTrainingBinding binding;
    public static TrainingFromExerciseViewModel trainingFromExerciseViewModel;

    public static TrainingFragment newInstance(int id) {
        TrainingFragment trainingFragment = new TrainingFragment();
        Bundle args = new Bundle();
        args.putInt("ID", id);
        trainingFragment.setArguments(args);
        return trainingFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTrainingBinding.inflate(inflater, container, false);

        DataCounter dataCounter = new DataCounter();

        createPie(dataCounter);
        loadPage(dataCounter);

        final TrainingFromExerciseAdapter adapter = new TrainingFromExerciseAdapter(new TrainingFromExerciseAdapter.TrainingFromExerciseDiff(), getContext());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.isScrollbarFadingEnabled();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        trainingFromExerciseViewModel = new ViewModelProvider(this).get(TrainingFromExerciseViewModel.class);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);

        trainingFromExerciseViewModel.getTrainingFromExercisesFromTrainingNumber(getArguments().getInt("ID")).observe(getViewLifecycleOwner(), exercises -> {
            adapter.submitList(exercises);
        });



        //Button
        binding.addPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                AddPointExersiseFragment myFragment = AddPointExersiseFragment.newInstance(getArguments().getInt("ID"));
                fragmentManager.beginTransaction().add(R.id.frameLayout, myFragment).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack("myStack")
                        .commit();
            }
        });

        binding.addRestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                AddPointExersiseRestFragment myFragment = new AddPointExersiseRestFragment();
                fragmentManager.beginTransaction().add(R.id.frameLayout, myFragment).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack("myStack")
                        .commit();
            }
        });

        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                TabataTimerFragment myFragment = TabataTimerFragment.newInstance(getArguments().getInt("ID"));
                fragmentManager.beginTransaction().add(R.id.frameLayout, myFragment).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack("myStack")
                        .commit();
            }

        });

        return binding.getRoot();
    }


    void createPie(DataCounter dataCounter){

        //PieChart

        Drawable ic_chest = getResources().getDrawable(R.drawable.ic_chest);
        ic_chest.setTint(Color.WHITE);
        ic_chest.setBounds(0,45,0,0);
        Drawable ic_arm = getResources().getDrawable(R.drawable.ic_arm);
        ic_arm.setTint(Color.WHITE);
        ic_arm.setBounds(0,45,0,0);
        Drawable ic_back = getResources().getDrawable(R.drawable.ic_back);
        ic_back.setTint(Color.WHITE);
        ic_back.setBounds(0,45,0,0);
        Drawable ic_leg = getResources().getDrawable(R.drawable.ic_leg);
        ic_leg.setTint(Color.WHITE);
        ic_leg.setBounds(0,45,0,0);
        Drawable ic_shoulder = getResources().getDrawable(R.drawable.ic_shoulder);
        ic_shoulder.setTint(Color.WHITE);
        ic_shoulder.setBounds(0,45,0,40);

        ArrayList<PieEntry> pieArray = new ArrayList<>();
        if(dataCounter.countPress!=0)
            pieArray.add(new PieEntry(dataCounter.countPress, ic_chest));
        if(dataCounter.countHand!=0)
            pieArray.add(new PieEntry(dataCounter.countHand, ic_arm));
        if(dataCounter.countBack!=0)
            pieArray.add(new PieEntry(dataCounter.countBack, ic_back));
        if(dataCounter.countBreast!=0)
            pieArray.add(new PieEntry(dataCounter.countBreast, ic_chest));
        if(dataCounter.countFoot!=0)
            pieArray.add(new PieEntry(dataCounter.countFoot, ic_leg));
        if(dataCounter.countSholders!=0)
            pieArray.add(new PieEntry(dataCounter.countSholders, ic_shoulder));

        PieDataSet barDataSetForPieChart = new PieDataSet(pieArray, "pieArray");


        int colorSetForPie[] = {Color.rgb(253,151,39), Color.rgb(103,63,180), Color.rgb(204,217,72), Color.rgb(44,152,240)};
        barDataSetForPieChart.setColors(ColorTemplate.createColors(colorSetForPie));
        barDataSetForPieChart.setValueTextColor(Color.WHITE);
        barDataSetForPieChart.setDrawValues(false);

        barDataSetForPieChart.setDrawValues(true);
        PieData barDataPie = new PieData(barDataSetForPieChart);

        barDataPie.setValueFormatter(new DecimalRemover(new DecimalFormat("###,###,###")));

        barDataPie.setValueTextSize(15f);
        barDataPie.setValueTextColor(Color.WHITE);
        binding.pieChart.setData(barDataPie);
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.getLegend().setEnabled(false);
        binding.pieChart.animate();
        binding.pieChart.setHoleColor(ContextCompat.getColor(getContext(), R.color.transparent));
        binding.pieChart.setHoleRadius(30);
        binding.pieChart.setTransparentCircleRadius(33);
        binding.pieChart.setUsePercentValues(true);
        binding.pieChart.setDrawingCacheEnabled(false);
        binding.pieChart.setDragDecelerationEnabled(true);


    }

    void loadPage(DataCounter dataCounter){
        binding.nameTraining.setText(getArguments().getString("NAME"));
        binding.timeTraining.setText(Integer.toString(dataCounter.countTime));
        binding.countExercise.setText(Integer.toString(dataCounter.countExercise));
    }

    public static void addTrainingFromExercise(TrainingFromExercise trainingFromExercise){
        trainingFromExerciseViewModel.insert(trainingFromExercise);
    }

    class DecimalRemover extends PercentFormatter {
        protected DecimalFormat mFormat;
        public DecimalRemover(DecimalFormat format) {
            this.mFormat = format; }
        @Override public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler){
            if(value < 10) return "";
            return mFormat.format(value) + " %"; }
    }

    class DataCounter {
        int countPress=0;
        int countHand=0;
        int countFoot=0;
        int countBack=0;
        int countBreast=0;
        int countSholders=0;

        int countTime=0;
        int countExercise=0;

        List<TrainingFromExercise> list;

        DataCounter(){update();}

        void update(){

                try {
                    list = DataBase.getDatabase(getActivity()).trainingFromExerciseDao().getTrainingFromExerciseFromTrainingId(getArguments().getInt("ID"));
                    for(TrainingFromExercise trainingFromExercise :  list){
                        int exerciseId = trainingFromExercise.getExerciseId();
                        Exercise exercise = MyExerciseFragment.exerciseViewModel.getExerciseByID(exerciseId);
                        if(exercise.isPress_type())countPress++;
                        if(exercise.isHands_type())countHand++;
                        if(exercise.isFoot_type())countFoot++;
                        if(exercise.isBack_type())countBack++;
                        if(exercise.isBreast_type())countBreast++;
                        if(exercise.isSholders_type())countSholders++;
                        countTime += trainingFromExercise.getTime();
                        countExercise++;

                    }

                }catch (Exception e){}
        }
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

//            Collections.swap(Collections.singletonList(trainingFromExerciseViewModel.getTrainingFromExercisesFromTrainingNumber(getArguments().getInt("ID")), fromPosition, toPosition);
            return false;
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };



}