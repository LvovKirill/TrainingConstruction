package com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen.MyExerciseFragment;

import java.util.List;

public class TrainingFromExerciseAdapter extends RecyclerView.Adapter {

    private static final String TAG = "RecyclerAdapter";
    List<TrainingFromExercise> moviesList;

    public TrainingFromExerciseAdapter(List<TrainingFromExercise> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public int getItemViewType(int position) {
        if(moviesList.get(position).getType()==1)
        return 1;
        else if(moviesList.get(position).getType()==0)
        return 0;
        else return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == 1) {
            view = layoutInflater.inflate(R.layout.training_from_exercise_item, parent, false);
            return new ViewHolderOne(view);
        }else if(viewType == 0){
            view = layoutInflater.inflate(R.layout.training_from_exercise_rest_item, parent, false);
            return new ViewHolderTwo(view);
        } return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position)==1) {
            ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
            TrainingFromExercise trainingFromExercise = moviesList.get(position);
            Exercise exercise = MyExerciseFragment.exerciseViewModel.getExerciseByID(trainingFromExercise.getExerciseId());
            viewHolderOne.nameExercise.setText(exercise.getName());
            viewHolderOne.repeatTextView.setText(Integer.toString(trainingFromExercise.getRepeat()));
            viewHolderOne.timeTextView.setText(Integer.toString(trainingFromExercise.getTime()));
            viewHolderOne.weightTextView.setText(Integer.toString(trainingFromExercise.getWeight()));

            viewHolderOne.imageTFE.setImageResource(exercise.getImg_id());

            if(exercise.isPress_type()){ viewHolderOne.icPress.setColorFilter(Color.argb(255, 255, 255, 255));}
            if(exercise.isHands_type()){ viewHolderOne.icArm.setColorFilter(Color.argb(255, 255, 255, 255));}
            if(exercise.isBack_type()){ viewHolderOne.icBack.setColorFilter(Color.argb(255, 255, 255, 255));}
            if(exercise.isBreast_type()){ viewHolderOne.icChest.setColorFilter(Color.argb(255, 255, 255, 255));}
            if(exercise.isFoot_type()){ viewHolderOne.icLeg.setColorFilter(Color.argb(255, 255, 255, 255));}
            if(exercise.isSholders_type()){ viewHolderOne.icSholders.setColorFilter(Color.argb(255, 255, 255, 255));}
        }else if (getItemViewType(position)==0){
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            viewHolderTwo.textView.setText(Integer.toString(moviesList.get(position).getTime()));
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    class ViewHolderOne extends RecyclerView.ViewHolder {

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

        private final ImageView imageTFE;

        public ViewHolderOne(@NonNull View itemView) {
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

            imageTFE = itemView.findViewById(R.id.imageTFEItem);

        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {

        TextView textView;
        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.timeRest);
        }
    }
}

