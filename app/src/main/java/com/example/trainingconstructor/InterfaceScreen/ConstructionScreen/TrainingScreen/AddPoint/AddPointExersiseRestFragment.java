package com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.TrainingScreen.AddPoint;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingconstructor.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPointExersiseRestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPointExersiseRestFragment extends Fragment {


    public static AddPointExersiseRestFragment newInstance(String param1, String param2) {
        AddPointExersiseRestFragment fragment = new AddPointExersiseRestFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_point_exersise_rest, container, false);
    }
}