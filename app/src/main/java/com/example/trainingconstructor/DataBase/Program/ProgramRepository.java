package com.example.trainingconstructor.DataBase.Program;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.Program.Program;
import com.example.trainingconstructor.DataBase.Program.ProgramDao;

import java.util.List;

class ProgramRepository {

    private ProgramDao mProgramDao;
    private LiveData<List<Program>> mAllPrograms;

    ProgramRepository(Application application) {
        DataBase db = DataBase.getDatabase(application);
        mProgramDao = db.programDao();
        mAllPrograms = mProgramDao.getAllProgramLiveData();
    }

    LiveData<List<Program>> getAllLiveDataPrograms() {
        return mAllPrograms;
    }

    void insert(Program program) {
        DataBase.databaseWriteExecutor.execute(() -> {
            mProgramDao.insertProgram(program);
        });
    }

    void deleteById(int id) {
        DataBase.databaseWriteExecutor.execute(() -> {
            mProgramDao.deleteById(id);
        });
    }

    List<Program> getAllPrograms(){
        return mProgramDao.getAllProgram();
    }
}
