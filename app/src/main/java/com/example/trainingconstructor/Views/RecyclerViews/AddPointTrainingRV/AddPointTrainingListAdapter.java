package com.example.trainingconstructor.Views.RecyclerViews.AddPointTrainingRV;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.TrainingScreen.AddPoint.AddPointExersiseFragment;

import java.util.List;


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