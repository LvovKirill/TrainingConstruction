package com.example.trainingconstructor.Views.RecyclerViews.TrainingFromExerciseRV;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.DataBase.Exercise.ExerciseViewModel;
import com.example.trainingconstructor.DataBase.Training.Training;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.ExerciseScreen.MyExerciseFragment;
import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.TrainingScreen.MyTrainingFragment;
import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.TrainingScreen.TrainingFragment;
import com.example.trainingconstructor.InterfaceScreen.MainActivity;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.Views.RecyclerViews.TrainingRV.TrainingViewHolder;

import java.util.List;


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
