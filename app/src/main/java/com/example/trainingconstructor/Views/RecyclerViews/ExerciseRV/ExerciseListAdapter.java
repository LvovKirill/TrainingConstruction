package com.example.trainingconstructor.Views.RecyclerViews.ExerciseRV;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.DataBase.Training.Training;
import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.ExerciseScreen.ExerciseFragment;
import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.ExerciseScreen.MyExerciseFragment;
import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.ProgramScreen.ProgramFragment;
import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.TrainingScreen.TrainingFragment;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.Views.RecyclerViews.ExerciseRV.ExerciseViewHolder;

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
