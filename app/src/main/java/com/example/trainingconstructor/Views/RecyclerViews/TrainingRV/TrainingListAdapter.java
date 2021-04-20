package com.example.trainingconstructor.Views.RecyclerViews.TrainingRV;

import android.app.Activity;
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

import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.Training.Training;
import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.TrainingScreen.AddPoint.AddPointExersiseFragment;
import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.TrainingScreen.MyTrainingFragment;
import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.TrainingScreen.TrainingFragment;
import com.example.trainingconstructor.InterfaceScreen.MainActivity;
import com.example.trainingconstructor.R;

public class TrainingListAdapter extends ListAdapter<Training, TrainingViewHolder> {

    private Context context;

    public TrainingListAdapter(@NonNull DiffUtil.ItemCallback<Training> diffCallback, Context context) {
        super(diffCallback);
        this.context = context;
    }

    @Override
    public TrainingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return TrainingViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(TrainingViewHolder holder, int position) {
        Training current = getItem(position);

        holder.bind(current.getName(), current.isPress_type(), current.isHands_type(), current.isBack_type(),
                current.isBreast_type(), current.isFoot_type(), current.isBreast_sholders(), holder.itemView.getContext());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
                TrainingFragment myFragment = TrainingFragment.newInstance(current.getId(), current.getName());
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

    public static class TrainingDiff extends DiffUtil.ItemCallback<Training> {

        @Override
        public boolean areItemsTheSame(@NonNull Training oldItem, @NonNull Training newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Training oldItem, @NonNull Training newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }

    private void showAlertDilog(Training current){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.no_yes_alert_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button button_yes = dialog.findViewById(R.id.button_yes);
        Button button_no = dialog.findViewById(R.id.button_no);
        TextView textView = dialog.findViewById(R.id.text);

        textView.setText(context.getString(R.string.delete_question) + " " + context.getString(R.string.training_inclination_1) + " \"" + current.getName()+"\"");

        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    TrainingFragment.trainingFromExerciseViewModel.deleteById(current.getId());
                    MyTrainingFragment.trainingViewModel.deleteById(current.getId());
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
