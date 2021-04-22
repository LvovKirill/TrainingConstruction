package com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingconstructor.ui.ConstructionScreen.ProgramScreen.MyProgramFragment;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentAddPointExersisePagerBinding;

public class AddPointExersisePagerFragment extends Fragment implements View.OnClickListener {

    FragmentAddPointExersisePagerBinding binding;
    ColorStateList def;
    PagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddPointExersisePagerBinding.inflate(inflater, container, false);

        adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), getArguments().getInt("ID"));

        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setCurrentItem(0);

        def = binding.customTabItem2.getTextColors();
        binding.customTabItem1.setOnClickListener(this);
        binding.customTabItem2.setOnClickListener(this);
        binding.cross.setOnClickListener(this);

        return binding.getRoot();
    }


    public static AddPointExersisePagerFragment newInstance(int id) {
        AddPointExersisePagerFragment addPointExersisePagerFragment = new AddPointExersisePagerFragment();
        Bundle args = new Bundle();
        args.putInt("ID", id);
        addPointExersisePagerFragment.setArguments(args);
        return addPointExersisePagerFragment;
    }


    public static class PagerAdapter extends FragmentStatePagerAdapter {

        int trainingID;

        PagerAdapter(@NonNull FragmentManager fm, int id) {
            super(fm);
            this.trainingID = id;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.setPrimaryItem(container, position, object);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return AddPointExersiseFragment.newInstance(trainingID);
                case 1:
                    return new AddPointExersiseRestFragment();
                default:
                    return new MyProgramFragment();
            }
        }
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.customTabItem1) {
            binding.select.animate().x(0).setDuration(100);
            binding.customTabItem1.setTextColor(Color.WHITE);
            binding.customTabItem2.setTextColor(def);
            binding.viewPager.setCurrentItem(0);
        } else if (v.getId() == R.id.customTabItem2) {
            binding.customTabItem1.setTextColor(def);
            binding.customTabItem2.setTextColor(Color.WHITE);
            int size = binding.customTabItem2.getWidth();
            binding.select.animate().x(size).setDuration(100);
            binding.viewPager.setCurrentItem(1);

        } else if (v.getId() == R.id.cross) {
            getActivity().onBackPressed();
        }
    }
}