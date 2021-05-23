package com.example.trainingconstructor.ui.ConstructionScreen.ProgramScreen;

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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingconstructor.DataBase.Program.Program;
import com.example.trainingconstructor.R;

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
        holder.bind(current.getName(), current.getComplexity(), current.getCycle(), current.getImg_id());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
                ProgramFragment myFragment = ProgramFragment.newInstance(current.getId());
                Log.d("MyTag", String.valueOf(current.getId()));
                fragmentManager.beginTransaction().add(R.id.frameLayout, myFragment, "programFrag").setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
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

class ProgramViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final TextView nameProgram;
    private final TextView cycleTextView;
    private final ImageView oneFlash;
    private final ImageView twoFlash;
    private final ImageView threeFlash;
    private final ImageView imageProgram;

    private ProgramViewHolder(View itemView) {
        super(itemView);
        nameProgram = itemView.findViewById(R.id.name_program);
        oneFlash = itemView.findViewById(R.id.oneFlash);
        twoFlash = itemView.findViewById(R.id.twoFlash);
        threeFlash = itemView.findViewById(R.id.threeFlash);
        cycleTextView = itemView.findViewById(R.id.cycleTextView);
        imageProgram = itemView.findViewById(R.id.imageProgramItem);

        itemView.setOnClickListener(this);
    }

    public void bind(String text, Float complexity, int cycle, int imgID) {
        nameProgram.setText(text);
        cycleTextView.setText(String.valueOf(cycle) + " недели");
        imageProgram.setImageResource(imgID);


        if(complexity==1.0){oneFlash.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(complexity==2.0){
            oneFlash.setColorFilter(Color.argb(255, 255, 255, 255));
            twoFlash.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(complexity==3.0){
            oneFlash.setColorFilter(Color.argb(255, 255, 255, 255));
            twoFlash.setColorFilter(Color.argb(255, 255, 255, 255));
            threeFlash.setColorFilter(Color.argb(255, 255, 255, 255));}
    }

    static ProgramViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.program_item, parent, false);
        return new ProgramViewHolder(view);
    }

    @Override
    public void onClick(View v) {
    }
}
