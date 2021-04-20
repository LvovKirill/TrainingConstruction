package com.example.trainingconstructor.Views.RecyclerViews.TrainingFromExerciseRV;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.ExerciseScreen.MyExerciseFragment;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.Views.RecyclerViews.AddPointTrainingRV.AddPointTrainingViewHolder;

public class TrainingFromExerciseViewHolder extends RecyclerView.ViewHolder   {

    private final TextView nameExercise;
    private final TextView repeatTextView;
    private final TextView timeTextView;
    private final TextView weightTextView;

    private final ImageView icPress;
    private final ImageView icArm;
    private final ImageView icBack;
    private final ImageView icChest;
    private final ImageView icLeg;
    private final ImageView icSholders;

    private TrainingFromExerciseViewHolder(View itemView) {
        super(itemView);
        nameExercise = itemView.findViewById(R.id.name_tfe);
        repeatTextView = itemView.findViewById(R.id.repeat);
        timeTextView = itemView.findViewById(R.id.timeText);
        weightTextView = itemView.findViewById(R.id.weight);

        icPress = itemView.findViewById(R.id.ic_press);
        icArm = itemView.findViewById(R.id.ic_arm);
        icBack = itemView.findViewById(R.id.ic_back);
        icChest = itemView.findViewById(R.id.ic_chest);
        icLeg = itemView.findViewById(R.id.ic_leg);
        icSholders = itemView.findViewById(R.id.ic_sholders);
    }

    public void bind(int repeat, int time, int weight, TrainingFromExercise trainingFromExercise) {
        Exercise exercise = MyExerciseFragment.exerciseViewModel.getExerciseByID(trainingFromExercise.getExerciseId());
        nameExercise.setText(exercise.getName());
        repeatTextView.setText(Integer.toString(repeat));
        timeTextView.setText(Integer.toString(time));
        weightTextView.setText(Integer.toString(weight));

        if(exercise.isPress_type()){ icPress.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(exercise.isHands_type()){ icArm.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(exercise.isBack_type()){ icBack.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(exercise.isBreast_type()){ icChest.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(exercise.isFoot_type()){ icLeg.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(exercise.isSholders_type()){ icSholders.setColorFilter(Color.argb(255, 255, 255, 255));}
    }

    static TrainingFromExerciseViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.training_from_exercise_item, parent, false);
        return new TrainingFromExerciseViewHolder(view);
    }
}
