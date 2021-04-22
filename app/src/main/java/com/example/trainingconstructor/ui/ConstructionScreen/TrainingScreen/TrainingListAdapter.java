package com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen;

import android.annotation.SuppressLint;
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

import com.example.trainingconstructor.DataBase.Training.Training;
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


class TrainingViewHolder extends RecyclerView.ViewHolder{

    private final TextView nameTraining;
    private final ImageView icPress;
    private final ImageView icArm;
    private final ImageView icBack;
    private final ImageView icChest;
    private final ImageView icLeg;
    private final ImageView icSholders;

    private TrainingViewHolder(View itemView) {
        super(itemView);
        nameTraining = itemView.findViewById(R.id.name_training);
        icPress = itemView.findViewById(R.id.ic_press);
        icArm = itemView.findViewById(R.id.ic_arm);
        icBack = itemView.findViewById(R.id.ic_back);
        icChest = itemView.findViewById(R.id.ic_chest);
        icLeg = itemView.findViewById(R.id.ic_leg);
        icSholders = itemView.findViewById(R.id.ic_sholders);
    }

    public void bind(String text, boolean isPress, boolean isArm, boolean isBack, boolean isChest, boolean isLeg, boolean isSholders, Context context) {
        nameTraining.setText(text);

        if(isPress){ icPress.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(isArm){ icArm.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(isBack){ icBack.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(isChest){ icChest.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(isLeg){ icLeg.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(isSholders){ icSholders.setColorFilter(Color.argb(255, 255, 255, 255));}
    }
    @SuppressLint("ResourceAsColor")


    static TrainingViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_item, parent, false);
        return new TrainingViewHolder(view);
    }


}
