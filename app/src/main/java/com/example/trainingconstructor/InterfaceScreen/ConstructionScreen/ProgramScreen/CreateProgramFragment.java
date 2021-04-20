package com.example.trainingconstructor.InterfaceScreen.ConstructionScreen.ProgramScreen;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.trainingconstructor.DataBase.Program.Program;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentCreateProgramBinding;


public class CreateProgramFragment extends Fragment {

    private CreateProgramFragment.FragmentListener listener;

    FragmentCreateProgramBinding binding;
    public static final String EXTRA_REPLY = "com.example.android.traininglistsql.REPLY";

    public interface FragmentListener{
        void onInputProgramSent(Program training);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateProgramBinding.inflate(inflater, container, false);

        binding.createProgramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = String.valueOf(binding.editProgramName.getText());
                String about = String.valueOf(binding.editProgramAbout.getText());
                boolean press_type = binding.pressCheckBox.isChecked();
                boolean hands_type = binding.armCheckBox.isChecked();
                boolean foot_type = binding.legCheckBox.isChecked();
                boolean back_type = binding.backCheckBox.isChecked();
                boolean breast_type = binding.chestCheckBox.isChecked();

                if(name.equals("")){
                    Toast.makeText(getActivity(), R.string.input_name, Toast.LENGTH_LONG).show();
                }else if (about.equals("")) {
                    Toast.makeText(getActivity(), R.string.input_about, Toast.LENGTH_LONG).show();
                }else if (!(press_type == hands_type == foot_type == back_type == breast_type)){
                    Toast.makeText(getActivity(), R.string.choose_muscle_group, Toast.LENGTH_LONG).show();
                }else {
                    Program program = new Program(name, about, 3,5);
                    Toast.makeText(getActivity(), "всё чики-поки", Toast.LENGTH_LONG).show();

                    listener.onInputProgramSent(program);


                    FragmentManager fragmentManager = getFragmentManager();
                    ProgramFragment myFragment = new ProgramFragment();
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
        if(context instanceof CreateProgramFragment.FragmentListener){
            listener=(CreateProgramFragment.FragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }
}