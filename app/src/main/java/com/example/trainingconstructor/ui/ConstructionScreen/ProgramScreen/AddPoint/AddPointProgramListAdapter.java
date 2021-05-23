package com.example.trainingconstructor.ui.ConstructionScreen.ProgramScreen.AddPoint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.ProgramFromTraining.ProgramFromTraining;
import com.example.trainingconstructor.DataBase.Training.Training;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint.AddPointExersiseFragment;


public class AddPointProgramListAdapter extends ListAdapter<Training, AddPointTrainingViewHolder> {

    private Context context;
    private Fragment fragment;
    private int typeDay;
    private int numberCycle;
    private int programId;



    public AddPointProgramListAdapter(@NonNull DiffUtil.ItemCallback<Training> diffCallback, Context context, Fragment fragment, int typeDay, int numberCycle, int programId) {
        super(diffCallback);
        this.context = context;
        this.fragment = fragment;
        this.typeDay = typeDay;
        this.numberCycle = numberCycle;
        this.programId = programId;
    }



    @Override
    public AddPointTrainingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AddPointTrainingViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(AddPointTrainingViewHolder holder, int position) {
        Training current = getItem(position);
        holder.bind(current.getName(), current.getImg_id());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DataBase.getDatabase(context).programFromTraining().insertProgramFromTraining(new ProgramFromTraining(current.getId(), programId, typeDay, numberCycle));
                AddPointProgramFragment.currentTrainingId=current.getId();
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
}


class AddPointTrainingViewHolder extends RecyclerView.ViewHolder   {

    private final TextView nameTraining;
    private final ImageView imageView;

    private AddPointTrainingViewHolder(View itemView) {
        super(itemView);
        nameTraining = itemView.findViewById(R.id.name_exercise);
        imageView = itemView.findViewById(R.id.imageItem);
    }

    public void bind(String text, int imgId) {
        nameTraining.setText(text);
        imageView.setImageResource(imgId);
    }

    static AddPointTrainingViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_point_training_item, parent, false);
        return new AddPointTrainingViewHolder(view);
    }
}
