package com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentCreateExerciseBinding;
import com.example.trainingconstructor.ui.ConstructionScreen.GalleryScreen.GalleryFragment;


public class CreateExerciseFragment extends Fragment {

    private com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen.CreateExerciseFragment.FragmentListener listener;

    FragmentCreateExerciseBinding binding;
    static int imgID=R.drawable.sport_men;
    public static final String EXTRA_REPLY = "com.example.android.traininglistsql.REPLY";

    public interface FragmentListener{
        void onInputExerciseSent(Exercise exercise);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateExerciseBinding.inflate(inflater, container, false);

        binding.createExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = String.valueOf(binding.editExerciseName.getText());
                String about = String.valueOf(binding.editExerciseAbout.getText());
                boolean press_type=false;
                boolean hands_type=false;
                boolean foot_type=false;
                boolean back_type=false;
                boolean breast_type=false;
                boolean shoulder_type=false;
                if(binding.pressCheckBox.isChecked())press_type=true;
                if(binding.armCheckBox.isChecked())hands_type=true;
                if(binding.legCheckBox.isChecked())foot_type=true;
                if(binding.backCheckBox.isChecked())back_type=true;
                if(binding.chestCheckBox.isChecked())breast_type=true;
                if(binding.shoulderCheckBox.isChecked())shoulder_type=true;

                if(name.equals("")){
                    Toast.makeText(getActivity(), R.string.input_name, Toast.LENGTH_LONG).show();
                }else if (about.equals("")) {
                    Toast.makeText(getActivity(), R.string.input_about, Toast.LENGTH_LONG).show();
                }else if (!press_type && !hands_type && !foot_type && !back_type && !breast_type && !shoulder_type){
                    Toast.makeText(getActivity(), R.string.choose_muscle_group, Toast.LENGTH_LONG).show();
                }else {
                    Exercise exercise = new Exercise(name, press_type, hands_type, foot_type, back_type, breast_type, shoulder_type, imgID);

                    listener.onInputExerciseSent(exercise);

                    getActivity().onBackPressed();


//                    FragmentManager fragmentManager = getFragmentManager();
//                    ExerciseFragment myFragment = new ExerciseFragment();
//                    fragmentManager.beginTransaction().add(R.id.frameLayout, myFragment).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
//                            .addToBackStack("myStack")
//                            .commit();

                }

            }
        });

        binding.defaultGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                GalleryFragment myFragment = GalleryFragment.newInstance("exercise");
                fragmentManager.beginTransaction().add(R.id.frameLayout, myFragment, "exerciseFrag").setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack("myStack")
                        .commit();
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen.CreateExerciseFragment.FragmentListener){
            listener=(com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen.CreateExerciseFragment.FragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

    public static void setImgID(int imgID) {
        CreateExerciseFragment.imgID = imgID;
    }

    public static int getImgID() {
        return imgID;
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.imageCreateExercise.setImageResource(imgID);
    }
}