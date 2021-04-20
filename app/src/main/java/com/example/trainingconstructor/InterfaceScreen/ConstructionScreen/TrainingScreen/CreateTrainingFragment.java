package com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.TrainingScreen;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.trainingconstructor.DataBase.Training.Training;
import com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.ProgramScreen.ProgramFragment;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentCreateTrainingBinding;

public class CreateTrainingFragment extends Fragment {

    private FragmentListener listener;

    FragmentCreateTrainingBinding binding;

    public interface FragmentListener{
        void onInputTrainingSent(Training training);
    }

    public static CreateTrainingFragment newInstance() {
        CreateTrainingFragment createTrainingFragment = new CreateTrainingFragment();
        Bundle args = new Bundle();
        return createTrainingFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateTrainingBinding.inflate(inflater, container, false);

        binding.createTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(binding.editTrainingName.getText());
                boolean press_type = binding.pressCheckBox.isChecked();
                boolean hands_type = binding.armCheckBox.isChecked();
                boolean foot_type = binding.legCheckBox.isChecked();
                boolean back_type = binding.backCheckBox.isChecked();
                boolean breast_type = binding.chestCheckBox.isChecked();
                boolean sholders_type = binding.shoulderCheckBox.isChecked();

                if(name.equals("")){
                    Toast.makeText(getActivity(), R.string.input_name, Toast.LENGTH_LONG).show();
                }else if (!press_type && !hands_type && !foot_type && !back_type && !breast_type && !sholders_type){
                    Toast.makeText(getActivity(), R.string.choose_muscle_group, Toast.LENGTH_LONG).show();
                }else {
                    Training training = new Training(name, press_type, hands_type, foot_type, back_type, breast_type, sholders_type);

                    listener.onInputTrainingSent(training);

                    FragmentManager fragmentManager = getFragmentManager();

                    TrainingFragment myFragment = TrainingFragment.newInstance(training.getId(), training.getName());
                    fragmentManager.beginTransaction().add(R.id.frameLayout, myFragment).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                            .addToBackStack("myStack")
                            .commit();

                }

            }
        });


        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentListener){
            listener=(FragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }
}