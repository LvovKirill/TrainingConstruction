package com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.ProgramScreen;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentProgramBinding;

import java.util.ArrayList;


public class ProgramFragment extends Fragment {

    FragmentProgramBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProgramBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }

    class scheduleDrawer{
        final int cycle;
        ArrayList<String> weekList = new ArrayList<>();

        scheduleDrawer(int cycle){
            this.cycle = cycle;
        }

        void addWeekList(){
            weekList.add(getString(R.string.mondey));
            weekList.add(getString(R.string.tuesday));
            weekList.add(getString(R.string.wednesday));
            weekList.add(getString(R.string.thursday));
            weekList.add(getString(R.string.friday));
            weekList.add(getString(R.string.saturday));
            weekList.add(getString(R.string.sunday));
        }
    }
}