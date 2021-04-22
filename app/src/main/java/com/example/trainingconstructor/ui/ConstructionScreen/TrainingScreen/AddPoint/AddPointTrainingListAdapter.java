package com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.R;


public class AddPointTrainingListAdapter extends ListAdapter<Exercise, AddPointTrainingViewHolder> {

    private Context context;
    private Fragment fragment;



        public AddPointTrainingListAdapter(@NonNull DiffUtil.ItemCallback<Exercise> diffCallback, Context context, Fragment fragment) {
            super(diffCallback);
            this.context = context;
            this.fragment = fragment;
        }



        @Override
        public AddPointTrainingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return AddPointTrainingViewHolder.create(parent);
        }

        @Override
        public void onBindViewHolder(AddPointTrainingViewHolder holder, int position) {
            Exercise current = getItem(position);
            holder.bind(current.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((AddPointExersiseFragment) fragment).setText(current.getId());

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
    }


class AddPointTrainingViewHolder extends RecyclerView.ViewHolder   {

    private final TextView nameExercise;

    private AddPointTrainingViewHolder(View itemView) {
        super(itemView);
        nameExercise = itemView.findViewById(R.id.name_exercise);
    }

    public void bind(String text) {
        nameExercise.setText(text);
    }

    static AddPointTrainingViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_point_training_item, parent, false);
        return new AddPointTrainingViewHolder(view);
    }
}