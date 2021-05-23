package com.example.trainingconstructor.DataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.calendarEventconstructor.DataBase.CalendarEvent.CalendarEventDao;
import com.example.trainingconstructor.DataBase.CalendarEvent.CalendarEvent;
import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.DataBase.Exercise.ExerciseDao;
import com.example.trainingconstructor.DataBase.Program.Program;
import com.example.trainingconstructor.DataBase.Program.ProgramDao;
import com.example.trainingconstructor.DataBase.ProgramFromTraining.ProgramFromTraining;
import com.example.trainingconstructor.DataBase.ProgramFromTraining.ProgramFromTrainingDao;
import com.example.trainingconstructor.DataBase.Training.Training;
import com.example.trainingconstructor.DataBase.Training.TrainingDao;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExerciseDao;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen.CreateExerciseFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.CreateTrainingFragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Training.class, Program.class, Exercise.class, TrainingFromExercise.class, ProgramFromTraining.class, CalendarEvent.class},
        version = 1, exportSchema = true)
public abstract class DataBase extends RoomDatabase {

    public abstract TrainingDao trainingDao();
    public abstract ProgramDao programDao();
    public abstract ExerciseDao exerciseDao();

    public abstract TrainingFromExerciseDao trainingFromExerciseDao();
    public abstract ProgramFromTrainingDao programFromTraining();

    public abstract CalendarEventDao calendarEventDao();

    private static volatile DataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DataBase.class, "room_database")
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {

                ProgramDao dao = INSTANCE.programDao();
                dao.deleteAll();

                Program program = new Program("Воин спарты", "Описание", 1, 22, 2, R.drawable.sport_men);
                dao.insertProgram(program);
                program = new Program("Новичок 1.0", "Описание", 3, 14, 1, R.drawable.sport_men);
                dao.insertProgram(program);
                program = new Program("Новичок 1.0", "Описание", 3, 14, 3, R.drawable.sport_men);
                dao.insertProgram(program);
            });

            databaseWriteExecutor.execute(() -> {

                TrainingDao dao = INSTANCE.trainingDao();
                dao.deleteAll();

                Training training = new Training("Силовая тренировка на пресс", true, false, false, false, false, false, "path", CreateTrainingFragment.getImgId());
                dao.insertTraining(training);
            });

            databaseWriteExecutor.execute(() -> {

                ExerciseDao dao = INSTANCE.exerciseDao();
                dao.deleteAll();

                Exercise exercise = new Exercise("Планка", true, false, false, false, false, false, R.drawable.image_dg_4);
                dao.insertExercise(exercise);
                exercise = new Exercise("Отжимания", true, false, false, false, false, false, R.drawable.sport_men);
                dao.insertExercise(exercise);
            });

            databaseWriteExecutor.execute(() -> {
                TrainingFromExerciseDao dao = INSTANCE.trainingFromExerciseDao();
                dao.deleteAll();

//                TrainingFromExercise trainingFromExercise = new TrainingFromExercise(1,1,1,1,1,1);
//                dao.insertTrainingFromExercise(trainingFromExercise);
//
//                trainingFromExercise = new TrainingFromExercise(2,2,2,2,2,2);
//                dao.insertTrainingFromExercise(trainingFromExercise);
            });

            databaseWriteExecutor.execute(() -> {
                ProgramFromTrainingDao dao = (ProgramFromTrainingDao) INSTANCE.programFromTraining();
                dao.deleteAll();

                ProgramFromTraining programFromTraining = new ProgramFromTraining(1, 1, 2, 1);
                dao.insertProgramFromTraining(programFromTraining);

                ProgramFromTraining programFromTraining1 = new ProgramFromTraining(1, 1, 4, 1);
                dao.insertProgramFromTraining(programFromTraining1);
            });



        }
    };

}
