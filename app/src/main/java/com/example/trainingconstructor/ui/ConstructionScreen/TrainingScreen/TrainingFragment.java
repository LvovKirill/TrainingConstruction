package com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.DataBase.ProgramFromTraining.ProgramFromTraining;
import com.example.trainingconstructor.DataBase.Training.Training;
import com.example.trainingconstructor.DataBase.Training.TrainingViewModel;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExerciseViewModel;
import com.example.trainingconstructor.databinding.FragmentTrainingBinding;
import com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen.MyExerciseFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint.AddPointExersiseFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint.AddPointExersisePagerFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint.AddPointExersiseRestFragment;
import com.example.trainingconstructor.ui.TabataTimerScreen.TabataTimerFragment;
import com.example.trainingconstructor.R;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TrainingFragment extends Fragment implements View.OnClickListener, View.OnTouchListener{

    FragmentTrainingBinding binding;
    public static TrainingFromExerciseViewModel trainingFromExerciseViewModel;
    private GetInfoTrainingListener getInfoListener;
    public static TrainingViewModel trainingViewModel;
    private CreateTrainingFragment.FragmentListener listener;

    List<TrainingFromExercise> list = new ArrayList<>();


    public static TrainingFragment newInstance(int id) {
        TrainingFragment trainingFragment = new TrainingFragment();
        Bundle args = new Bundle();
        args.putInt("ID", id);
        trainingFragment.setArguments(args);
        return trainingFragment;
    }

    public interface GetInfoTrainingListener{
        void getInfoListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTrainingBinding.inflate(inflater, container, false);
        update();

        binding.recyclerView.isScrollbarFadingEnabled();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        binding.recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);

        binding.addPointButton.setOnClickListener(this);
        binding.addRestButton.setOnClickListener(this);
        binding.startButton.setOnClickListener(this);

        binding.addPointButton.setOnTouchListener(this);
        binding.addRestButton.setOnTouchListener(this);
        binding.startButton.setOnTouchListener(this);

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
        Drawable ic_press = getResources().getDrawable(R.drawable.ic_press);
        ic_press.setTint(Color.WHITE);
        ic_press.setBounds(0,45,0,40);

        ArrayList<PieEntry> pieArray = new ArrayList<>();
        if(dataCounter.countPress!=0)
            pieArray.add(new PieEntry(dataCounter.countPress, ic_press));
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

        if(dataCounter.countPress==0 && dataCounter.countHand==0
                && dataCounter.countBack==0 && dataCounter.countBreast==0 && dataCounter.countFoot==0 && dataCounter.countSholders==0){
            binding.emptyPie.setText(getString(R.string.emptyPie));
        }else binding.emptyPie.setText("");

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
        binding.pieChart.invalidate();
        binding.pieChart.spin(500,0,-360f, Easing.EasingOption.EaseInOutQuad);


    }

    void loadPage(DataCounter dataCounter){
        String name = DataBase.getDatabase(getActivity()).trainingDao().getTrainingByID(getArguments().getInt("ID")).getName();
        binding.nameTraining.setText(name);
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
                    Collections.sort(list, new TrainingFromExerciseComparator());
                    for(TrainingFromExercise trainingFromExercise :  list){
                        if(trainingFromExercise.getExerciseId()!=0) {
                            int exerciseId = trainingFromExercise.getExerciseId();
                            Exercise exercise = MyExerciseFragment.exerciseViewModel.getExerciseByID(exerciseId);
                            if (exercise.isPress_type()) countPress++;
                            if (exercise.isHands_type()) countHand++;
                            if (exercise.isFoot_type()) countFoot++;
                            if (exercise.isBack_type()) countBack++;
                            if (exercise.isBreast_type()) countBreast++;
                            if (exercise.isSholders_type()) countSholders++;
                            countExercise++;
                        } countTime += trainingFromExercise.getTime();

                    }

                }catch (Exception e){}
        }
    }

    TrainingFromExercise deletedMovie = null;
    List<TrainingFromExercise> archivedTFE = new ArrayList<>();

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback( ItemTouchHelper.UP | ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            TrainingFromExercise trainingFromExerciseFrom = list.get(fromPosition);
            int numberFrom = trainingFromExerciseFrom.getNumberInTraining();
            TrainingFromExercise trainingFromExerciseTo = list.get(toPosition);
            int numberTo = trainingFromExerciseTo.getNumberInTraining();

            Collections.swap(list, fromPosition, toPosition);
            binding.recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            trainingFromExerciseFrom.setNumberInTraining(numberTo);
            trainingFromExerciseTo.setNumberInTraining(numberFrom);

            DataBase.getDatabase(getActivity()).trainingFromExerciseDao().updateTrainingFromExercise(trainingFromExerciseFrom);
            DataBase.getDatabase(getActivity()).trainingFromExerciseDao().updateTrainingFromExercise(trainingFromExerciseTo);
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();

            switch (direction) {
//                case ItemTouchHelper.LEFT:
//                    deletedMovie = list.get(position);
//                    list.remove(position);
//                    binding.recyclerView.getAdapter().notifyItemRemoved(position);
//                    Snackbar.make(binding.recyclerView, String.valueOf(deletedMovie.getTime()), Snackbar.LENGTH_LONG)
//                            .setAction("Undo", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    list.add(position, deletedMovie);
//                                    binding.recyclerView.getAdapter().notifyItemInserted(position);
//                                }
//                            }).show();
//                    break;
                case ItemTouchHelper.RIGHT:
                    final TrainingFromExercise trainingFromExercise = list.get(position);
                    archivedTFE.add(trainingFromExercise);

                    list.remove(position);
                    binding.recyclerView.getAdapter().notifyItemRemoved(position);

                    DataBase.getDatabase(getActivity()).trainingFromExerciseDao().delete(trainingFromExercise);

                    Snackbar.make(binding.recyclerView, "Вы уверены, что хотите удалить этот пункт из тренировки?", Snackbar.LENGTH_LONG)
                            .setAction("Вернуть", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    archivedTFE.remove(archivedTFE.lastIndexOf(trainingFromExercise));
                                    list.add(position, trainingFromExercise);
                                    binding.recyclerView.getAdapter().notifyItemInserted(position);
                                }
                            }).show();
                    getActivity().getSupportFragmentManager().findFragmentByTag("trainingFrag").onStart();

                    break;
            }
        }
    };

    public class TrainingFromExerciseComparator implements Comparator<TrainingFromExercise> {
        @Override
        public int compare(TrainingFromExercise o1, TrainingFromExercise o2) {
            if(o1.getNumberInTraining()<o2.getNumberInTraining()) return -1;
            else if (o1.getNumberInTraining()>o2.getNumberInTraining()) return 1;
            else return 0;
        }
    }

    public void update(){
        trainingFromExerciseViewModel = new ViewModelProvider(this).get(TrainingFromExerciseViewModel.class);

        List<TrainingFromExercise> list1 = trainingFromExerciseViewModel.getTrainingFromExerciseFromTrainingId(getArguments().getInt("ID"));
        list.clear();
        for(TrainingFromExercise trainingFromExercise: list1){
            list.add(trainingFromExercise);
        }
        Collections.sort(list, new TrainingFromExerciseComparator());
        DataCounter dataCounter = new DataCounter();

        trainingViewModel = new ViewModelProvider(this).get(TrainingViewModel.class);
        Training training = trainingViewModel.getTrainingByID(getArguments().getInt("ID"));
        binding.headerTraining.setImageResource(training.getImg_id());


        createPie(dataCounter);
        loadPage(dataCounter);

//        binding.recyclerView.isScrollbarFadingEnabled();
//        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
//        binding.recyclerView.addItemDecoration(dividerItemDecoration);
//
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
//        itemTouchHelper.attachToRecyclerView(binding.recyclerView);

        TrainingFromExerciseAdapter recyclerAdapter = new TrainingFromExerciseAdapter(list);
        binding.recyclerView.setAdapter(recyclerAdapter);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Animation scaleUp = AnimationUtils.loadAnimation(getActivity(), R.anim.puls);
        Animation scaleDown = AnimationUtils.loadAnimation(getActivity(), R.anim.down_button_anim);

        if(binding.addPointButton.equals(v)) {
            if(event.getAction()==MotionEvent.ACTION_UP){}
            else if(event.getAction()==MotionEvent.ACTION_DOWN)
                binding.addTrainingLayout.startAnimation(scaleUp);

        } else if (binding.addRestButton.equals(v)) {
            if(event.getAction()==MotionEvent.ACTION_UP){}
            else if(event.getAction()==MotionEvent.ACTION_DOWN)
                binding.addRestLayout.startAnimation(scaleUp);

        } else if (binding.startButton.equals(v)) {
            if(event.getAction()==MotionEvent.ACTION_UP){}
            else if(event.getAction()==MotionEvent.ACTION_DOWN)
                binding.startButton.startAnimation(scaleUp);

        }

        return false;
    }


    @Override
    public void onClick(View v) {

        if (binding.addPointButton.equals(v)) {
            FragmentManager fragmentManager = getFragmentManager();
            AddPointExersiseFragment myFragment = AddPointExersiseFragment.newInstance(getArguments().getInt("ID"));
            fragmentManager.beginTransaction().add(R.id.frameLayout, myFragment).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .addToBackStack("myStack")
                    .commit();
        } else if (binding.addRestButton.equals(v)) {
            FragmentManager fragmentManagerRest = getFragmentManager();
            AddPointExersiseRestFragment myFragmentRest = AddPointExersiseRestFragment.newInstance(getArguments().getInt("ID"));
            fragmentManagerRest.beginTransaction().add(R.id.frameLayout, myFragmentRest).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .addToBackStack("myStack")
                    .commit();
        } else if (binding.startButton.equals(v)) {
            FragmentManager fragmentManagerStart = getFragmentManager();
            TabataTimerFragment myFragmentStart = TabataTimerFragment.newInstance(getArguments().getInt("ID"));
            fragmentManagerStart.beginTransaction().add(R.id.frameLayout, myFragmentStart).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .addToBackStack("myStack")
                    .commit();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        update();
    }
}