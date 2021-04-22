package com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.R;

public class ExerciseListAdapter extends ListAdapter<Exercise, ExerciseViewHolder> {

    private Context context;

    public ExerciseListAdapter(@NonNull DiffUtil.ItemCallback<Exercise> diffCallback, Context context) {
        super(diffCallback);
        this.context = context;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ExerciseViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {
        Exercise current = getItem(position);
        holder.bind(current);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
                ExerciseFragment myFragment = new ExerciseFragment();
                fragmentManager.beginTransaction().add(R.id.frameLayout, myFragment).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack("myStack")
                        .commit();

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showAlertDilog(current);
                return false;
            }
        });
    }

    public static class ExerciseDiff extends DiffUtil.ItemCallback<Exercise> {

        @Override
        public boolean areItemsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }



    private void showAlertDilog(Exercise current){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.no_yes_alert_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button button_yes = dialog.findViewById(R.id.button_yes);
        Button button_no = dialog.findViewById(R.id.button_no);
        TextView textView = dialog.findViewById(R.id.text);

        textView.setText(context.getString(R.string.delete_question) + " " + context.getString(R.string.exersise) + " \"" + current.getName()+"\" ?");

        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MyExerciseFragment.exerciseViewModel.deleteByID(current.getId());
                    dialog.cancel();
                }catch (Exception e){
                    Toast.makeText(context, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
                }
            }
        });

        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();

    }
}


class ExerciseViewHolder extends RecyclerView.ViewHolder   {

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

    static ExerciseViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item, parent, false);
        return new ExerciseViewHolder(view);
    }
}
