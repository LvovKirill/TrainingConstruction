package com.example.trainingconstructor.ui.ConstructionScreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingconstructor.ui.ConstructionScreen.ProgramScreen.MyProgramFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen.MyExerciseFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.MyTrainingFragment;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentConstructionBinding;


public class ConstructionFragment extends Fragment implements View.OnClickListener {

    FragmentConstructionBinding binding;
    ColorStateList def;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConstructionBinding.inflate(inflater, container, false);

        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager());

        Log.d("ErrorRV", "onCreateView");
        binding.viewPagerInConstructionFragment.setAdapter(adapter);
        binding.viewPagerInConstructionFragment.setCurrentItem(0);

        def = binding.customTabItem2.getTextColors();
        binding.customTabItem1.setOnClickListener(this);
        binding.customTabItem2.setOnClickListener(this);
        binding.customTabItem3.setOnClickListener(this);

        return binding.getRoot();
    }


    public static class PagerAdapter extends FragmentStatePagerAdapter {

        PagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.setPrimaryItem(container, position, object);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            MyTrainingFragment myTrainingFragment = new MyTrainingFragment();
            MyProgramFragment myProgramFragment = new MyProgramFragment();
            MyExerciseFragment myExerciseFragment = new MyExerciseFragment();
            switch (position) {
                case 0:
                    Log.d("ErrorRV", "MyProgramFragmentRV");
                    return myProgramFragment;
                case 1:
                    Log.d("ErrorRV", "MyTrainingFragmentRV");
                    return myTrainingFragment;
                case 2:
                    Log.d("ErrorRV", "MyExerciseFragmentRV");
                    return myExerciseFragment;

                default:
                    return new MyProgramFragment();
            }
        }
    }

    

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.customTabItem1){
            binding.select.animate().x(0).setDuration(100);
            binding.customTabItem1.setTextColor(Color.WHITE);
            binding.customTabItem2.setTextColor(def);
            binding.customTabItem3.setTextColor(def);
            binding.viewPagerInConstructionFragment.setCurrentItem(0);
        } else if (v.getId() == R.id.customTabItem2){
            binding.customTabItem1.setTextColor(def);
            binding.customTabItem2.setTextColor(Color.WHITE);
            binding.customTabItem3.setTextColor(def);
            int size = binding.customTabItem2.getWidth();
            binding.select.animate().x(size).setDuration(100);
            binding.viewPagerInConstructionFragment.setCurrentItem(1);
        } else if (v.getId() == R.id.customTabItem3){
            binding.customTabItem1.setTextColor(def);
            binding.customTabItem3.setTextColor(Color.WHITE);
            binding.customTabItem2.setTextColor(def);
            int size = binding.customTabItem2.getWidth() * 2;
            binding.select.animate().x(size).setDuration(100);
            binding.viewPagerInConstructionFragment.setCurrentItem(2);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("ErrorRV", "Stop");
        onDetach();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("ErrorRV", "onAttach");
    }
}