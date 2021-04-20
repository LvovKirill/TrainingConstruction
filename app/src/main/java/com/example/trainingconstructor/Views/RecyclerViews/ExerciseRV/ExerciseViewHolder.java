package com.example.trainingconstructor.Views.RecyclerViews.ExerciseRV;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.R;

public class ExerciseViewHolder extends RecyclerView.ViewHolder   {

    private final TextView nameExercise;

    private final ImageView icPress;
    private final ImageView icArm;
    private final ImageView icBack;
    private final ImageView icChest;
    private final ImageView icLeg;
    private final ImageView icSholders;

    private ExerciseViewHolder(View itemView) {
        super(itemView);
        nameExercise = itemView.findViewById(R.id.name_exercise);

        icPress = itemView.findViewById(R.id.ic_press);
        icArm = itemView.findViewById(R.id.ic_arm);
        icBack = itemView.findViewById(R.id.ic_back);
        icChest = itemView.findViewById(R.id.ic_chest);
        icLeg = itemView.findViewById(R.id.ic_leg);
        icSholders = itemView.findViewById(R.id.ic_sholders);
    }

    public void bind(Exercise exercise) {
        nameExercise.setText(exercise.getName());
        if(exercise.isPress_type()){ icPress.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(exercise.isHands_type()){ icArm.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(exercise.isBack_type()){ icBack.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(exercise.isBreast_type()){ icChest.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(exercise.isFoot_type()){ icLeg.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(exercise.isSholders_type()){ icSholders.setColorFilter(Color.argb(255, 255, 255, 255));}
    }

    static com.example.trainingconstructor.Views.RecyclerViews.ExerciseRV.ExerciseViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item, parent, false);
        return new com.example.trainingconstructor.Views.RecyclerViews.ExerciseRV.ExerciseViewHolder(view);
    }
}