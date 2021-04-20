package com.example.trainingconstructor.DataBase.Program;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.trainingconstructor.DataBase.Program.Program;
import com.example.trainingconstructor.DataBase.Program.ProgramRepository;

import java.util.List;

public class ProgramViewModel extends AndroidViewModel {

    private ProgramRepository mRepository;

    private final LiveData<List<Program>> mAllPrograms;

    public ProgramViewModel (Application application) {
        super(application);
        mRepository = new ProgramRepository(application);
        mAllPrograms = mRepository.getAllPrograms();
    }

    public LiveData<List<Program>> getAllPrograms() { return mAllPrograms; }

    public void insert(Program program) { mRepository.insert(program); }

    public void deleteById(int id) { mRepository.deleteById(id); }
}
