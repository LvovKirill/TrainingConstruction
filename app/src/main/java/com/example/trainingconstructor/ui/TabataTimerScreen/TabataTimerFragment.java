package com.example.trainingconstructor.ui.TabataTimerScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingconstructor.R;


public class TabataTimerFragment extends Fragment {




    public static TabataTimerFragment newInstance(String param1, String param2) {
        TabataTimerFragment fragment = new TabataTimerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tabata_timer, container, false);
    }
}