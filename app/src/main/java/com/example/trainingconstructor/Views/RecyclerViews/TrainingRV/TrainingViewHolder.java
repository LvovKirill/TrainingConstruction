package com.example.trainingconstructor.Views.RecyclerViews.TrainingRV;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.ProgramScreen.ProgramFragment;
import com.example.trainingconstructor.R;

public class TrainingViewHolder extends RecyclerView.ViewHolder{

    private final TextView nameTraining;
    private final ImageView icPress;
    private final ImageView icArm;
    private final ImageView icBack;
    private final ImageView icChest;
    private final ImageView icLeg;
    private final ImageView icSholders;

    private TrainingViewHolder(View itemView) {
        super(itemView);
        nameTraining = itemView.findViewById(R.id.name_training);
        icPress = itemView.findViewById(R.id.ic_press);
        icArm = itemView.findViewById(R.id.ic_arm);
        icBack = itemView.findViewById(R.id.ic_back);
        icChest = itemView.findViewById(R.id.ic_chest);
        icLeg = itemView.findViewById(R.id.ic_leg);
        icSholders = itemView.findViewById(R.id.ic_sholders);
    }

    public void bind(String text, boolean isPress, boolean isArm, boolean isBack, boolean isChest, boolean isLeg, boolean isSholders, Context context) {
        nameTraining.setText(text);

        if(isPress){ icPress.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(isArm){ icArm.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(isBack){ icBack.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(isChest){ icChest.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(isLeg){ icLeg.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(isSholders){ icSholders.setColorFilter(Color.argb(255, 255, 255, 255));}
    }
    @SuppressLint("ResourceAsColor")


    static TrainingViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_item, parent, false);
        return new TrainingViewHolder(view);
    }


}
