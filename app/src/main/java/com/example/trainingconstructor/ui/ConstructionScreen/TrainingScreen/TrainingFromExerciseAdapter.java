package com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.DataBase.Training.Training;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen.MyExerciseFragment;
import com.example.trainingconstructor.R;


public class TrainingFromExerciseAdapter extends ListAdapter<TrainingFromExercise, TrainingFromExerciseViewHolder> {

    private Context context;

    public TrainingFromExerciseAdapter(@NonNull DiffUtil.ItemCallback<TrainingFromExercise> diffCallback, Context context) {
        super(diffCallback);
        this.context = context;
    }

    @Override
    public TrainingFromExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           return TrainingFromExerciseViewHolder.create(parent);

    }

    @Override
    public void onBindViewHolder(TrainingFromExerciseViewHolder holder, int position) {
        TrainingFromExercise current = getItem(position);

        Thread myThready = new Thread(new Runnable() {
            @Override
            public void run() {
                String name = DataBase.getDatabase(context).exerciseDao().getNameByID(current.getExerciseId());
                holder.bind(current.getRepeat(), current.getTime(), current.getWeight(), current);
            }
        });


                try {

//                    AsyncTask.execute(new Runnable() {
//                        @Override
//                        public void run() {
                            String name = DataBase.getDatabase(context).exerciseDao().getNameByID(current.getExerciseId());
                            holder.bind(current.getRepeat(), current.getTime(), current.getWeight(), current);
//                        }
//                    });
                }catch (Exception e) {
                    Log.d("MyLog", Integer.toString(current.getExerciseId()));
                    holder.bind(current.getRepeat(), current.getTime(), current.getWeight(), current);
                }


    }

    public static class TrainingFromExerciseDiff extends DiffUtil.ItemCallback<TrainingFromExercise> {

        @Override
        public boolean areItemsTheSame(@NonNull TrainingFromExercise oldItem, @NonNull TrainingFromExercise newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull TrainingFromExercise oldItem, @NonNull TrainingFromExercise newItem) {
            return oldItem.getExerciseId()==newItem.getExerciseId();
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



class TrainingFromExerciseRestViewHolder extends RecyclerView.ViewHolder   {

    private final TextView timeRest;

    private TrainingFromExerciseRestViewHolder(View itemView) {
        super(itemView);
        timeRest = itemView.findViewById(R.id.timeRest);
    }

    public void bind(String time) {
        timeRest.setText(time);
    }

    static TrainingFromExerciseRestViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_from_exercise_rest_item, parent, false);
        return new TrainingFromExerciseRestViewHolder(view);
    }
}



class TrainingFromExerciseViewHolder extends RecyclerView.ViewHolder   {

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
