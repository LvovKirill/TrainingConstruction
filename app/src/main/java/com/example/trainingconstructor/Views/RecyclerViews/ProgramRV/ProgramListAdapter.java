package com.example.trainingconstructor.Views.RecyclerViews.ProgramRV;

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
import com.example.trainingconstructor.DataBase.Program.Program;
import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.ExerciseScreen.MyExerciseFragment;
import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.ProgramScreen.MyProgramFragment;
import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.ProgramScreen.ProgramFragment;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.Views.RecyclerViews.ProgramRV.ProgramViewHolder;

public class ProgramListAdapter extends ListAdapter<Program, ProgramViewHolder> {

    private Context context;

    public ProgramListAdapter(@NonNull DiffUtil.ItemCallback<Program> diffCallback, Context context) {
        super(diffCallback);
        this.context = context;
    }

    @Override
    public ProgramViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ProgramViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(ProgramViewHolder holder, int position) {
        Program current = getItem(position);
        holder.bind(current.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
                ProgramFragment myFragment = new ProgramFragment();
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


    public static class ProgramDiff extends DiffUtil.ItemCallback<Program> {

        @Override
        public boolean areItemsTheSame(@NonNull Program oldItem, @NonNull Program newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Program oldItem, @NonNull Program newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }


    private void showAlertDilog(Program current){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.no_yes_alert_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button button_yes = dialog.findViewById(R.id.button_yes);
        Button button_no = dialog.findViewById(R.id.button_no);
        TextView textView = dialog.findViewById(R.id.text);

        textView.setText(context.getString(R.string.delete_question) + " " + context.getString(R.string.program_inclination_1) + " \"" + current.getName()+"\" ?");

        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MyProgramFragment.programViewModel.deleteById(current.getId());
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
