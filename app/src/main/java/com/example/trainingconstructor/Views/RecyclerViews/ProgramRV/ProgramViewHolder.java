package com.example.trainingconstructor.Views.RecyclerViews.ProgramRV;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.ProgramScreen.ProgramFragment;
import com.example.trainingconstructor.R;

public class ProgramViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final TextView nameProgram;

    private ProgramViewHolder(View itemView) {
        super(itemView);
        nameProgram = itemView.findViewById(R.id.name_program);
        itemView.setOnClickListener(this);
    }

    public void bind(String text) {
        nameProgram.setText(text);
    }

    static com.example.trainingconstructor.Views.RecyclerViews.ProgramRV.ProgramViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.program_item, parent, false);
        return new com.example.trainingconstructor.Views.RecyclerViews.ProgramRV.ProgramViewHolder(view);
    }

    @Override
    public void onClick(View v) {
    }
}
