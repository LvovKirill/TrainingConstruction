package com.example.trainingconstructor.ui.ConstructionScreen.ProgramScreen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.ProgramFromTraining.ProgramFromTraining;
import com.example.trainingconstructor.DataBase.Training.Training;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.ui.ConstructionScreen.ProgramScreen.AddPoint.AddPointProgramFragment;

import java.util.List;

public class ProgramFromTrainingAdapter extends RecyclerView.Adapter {

    private static final String TAG = "RecyclerAdapter";
    List<ProgramFromTraining> PTList;
    private Context context;

    public ProgramFromTrainingAdapter(List<ProgramFromTraining> PTList, Context context) {
        this.PTList = PTList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(PTList.get(position).getTrainingId()==0)
        return 0;
        else return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == 1) {
            view = layoutInflater.inflate(R.layout.add_point_program_item, parent, false);
            return new ViewHolderOne(view);
        }else if(viewType == 0){
            view = layoutInflater.inflate(R.layout.add_point_empty_program_item, parent, false);
            return new ViewHolderTwo(view);
        } return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position)==1) {
            ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
            Training training = DataBase.getDatabase(context).trainingDao().getTrainingByID(PTList.get(position).getTrainingId());
            viewHolderOne.nameTrainingTextView.setText(training.getName());
            viewHolderOne.typeDayTextView.setText(getTypeDayToString(PTList.get(position).getTypeDay()));
            viewHolderOne.countCycle.setText(String.valueOf(PTList.get(position).getNumberCycle()));
            viewHolderOne.imageView.setImageResource(training.getImg_id());

        }else if (getItemViewType(position)==0){
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            viewHolderTwo.typeDayTextView.setText(getTypeDayToString(PTList.get(position).getTypeDay()));
            viewHolderTwo.countCycle.setText(String.valueOf(PTList.get(position).getNumberCycle()));
            viewHolderTwo.addPPTButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
                    AddPointProgramFragment myFragment = AddPointProgramFragment.newInstance(PTList.get(position).getTypeDay(), PTList.get(position).getNumberCycle(),
                            PTList.get(position).getProgramId());
                    Log.d("MyTag", String.valueOf(PTList.get(position).getProgramId()));
                    fragmentManager.beginTransaction().add(R.id.frameLayout, myFragment).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                            .addToBackStack("myStack")
                            .commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return PTList.size();
    }

    public String getTypeDayToString(int typeDay){
        switch (typeDay){
            case 1:
                return context.getString(R.string.mondey);
            case 2:
                return context.getString(R.string.tuesday);
            case 3:
                return context.getString(R.string.wednesday);
            case 4:
                return context.getString(R.string.thursday);
            case 5:
                return context.getString(R.string.friday);
            case 6:
                return context.getString(R.string.saturday);
            case 7:
                return context.getString(R.string.sunday);
            default: return null;
        }
    }


    class ViewHolderOne extends RecyclerView.ViewHolder {
        TextView typeDayTextView;
        TextView nameTrainingTextView;
        TextView countCycle;
        ImageView imageView;
        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            typeDayTextView = itemView.findViewById(R.id.typeDayTextView);
            nameTrainingTextView = itemView.findViewById(R.id.nameTrainingInProgramFromTraining);
            imageView = itemView.findViewById(R.id.imageProgramFromTraining);
            countCycle = itemView.findViewById(R.id.countWeekTextView);
        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {
        TextView typeDayTextView;
        TextView countCycle;
        Button addPPTButton;
        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            typeDayTextView = itemView.findViewById(R.id.typeDayTextView);
            addPPTButton = itemView.findViewById(R.id.addPointProgramFromTrainingsButton);
            countCycle = itemView.findViewById(R.id.countWeekTextView);
        }
    }
}
