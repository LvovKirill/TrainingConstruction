package com.example.trainingconstructor.ui.ConstructionScreen.ProgramScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingconstructor.DataBase.Program.Program;
import com.example.trainingconstructor.DataBase.Program.ProgramViewModel;
import com.example.trainingconstructor.R;


public class MyProgramFragment extends Fragment {

    com.example.trainingconstructor.databinding.FragmentMyProgramBinding binding;
    public static ProgramViewModel programViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    boolean emptyFlag;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = com.example.trainingconstructor.databinding.FragmentMyProgramBinding.inflate(inflater, container, false);

        final ProgramListAdapter adapter = new ProgramListAdapter(new ProgramListAdapter.ProgramDiff(), getActivity());
        binding.myProgramrecyclerview.setAdapter(adapter);
        binding.myProgramrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        programViewModel = new ViewModelProvider(this).get(ProgramViewModel.class);

        programViewModel.getAllLiveDataPrograms().observe(getViewLifecycleOwner(), programs -> {
            adapter.submitList(programs);
        });

        if(programViewModel.getAllPrograms().size()==0){emptyList();}

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateProgramFragment youFragment = new CreateProgramFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().add(R.id.frameLayout, youFragment, "createProgramFrag")
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack("myStack")
                        .commit();
            }
        });

        return binding.getRoot();
    }

    public void emptyList(){
//        binding.emptyListProgramImg.setImageResource(R.drawable.for_empty_list_img);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(emptyFlag){emptyList();}
    }

    public static void addProgram(Program program) {
        programViewModel.insert(program);
    }
}