package com.example.trainingconstructor.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.ImageView;

import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.DataBase.Program.Program;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen.CreateExerciseFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen.MyExerciseFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.ProgramScreen.CreateProgramFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.ProgramScreen.MyProgramFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint.AddPointExersiseFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.CreateTrainingFragment;
import com.example.trainingconstructor.DataBase.Training.Training;
import com.example.trainingconstructor.DataBase.Training.TrainingViewModel;
import com.example.trainingconstructor.ui.ConstructionScreen.ConstructionFragment;
import com.example.trainingconstructor.ui.CalendarScreen.CalendarFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.MyTrainingFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.TrainingFragment;

import com.example.trainingconstructor.ui.SettingsScreen.SettingsFragment;
import com.example.trainingconstructor.ui.StatisticsScreen.StatisticsFragment;
import com.example.trainingconstructor.R;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        CreateTrainingFragment.FragmentListener, CreateProgramFragment.FragmentListener,
        CreateExerciseFragment.FragmentListener, AddPointExersiseFragment.FragmentListener{

    SNavigationDrawer sNavigationDrawer;
    Class fragmentClass;
    public static Fragment fragment;
    public TrainingViewModel trainingViewModel;

    public TrainingViewModel getTrainingViewModel() {
        return trainingViewModel;
    }

    static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sNavigationDrawer = findViewById(R.id.navigationDrawer);
        sNavigationDrawer.setAppbarTitleTV(getString(R.string.constraction));

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        List<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(new MenuItem(getString(R.string.constraction),R.drawable.sport_men));
        menuItems.add(new MenuItem(getString(R.string.kalendar),R.drawable.sport_men));
        menuItems.add(new MenuItem(getString(R.string.statistics),R.drawable.sport_men));
        menuItems.add(new MenuItem(getString(R.string.settings),R.drawable.sport_men));

        sNavigationDrawer.setMenuItemList(menuItems);
        fragmentClass =  ConstructionFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }


        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                System.out.println("Position "+position);

                switch (position){
                    case 0:{
                        fragmentClass = ConstructionFragment.class;
                        break;
                    }
                    case 1:{
                        fragmentClass = CalendarFragment.class;
                        break;
                    }
                    case 2:{
                        fragmentClass = StatisticsFragment.class;
                        break;
                    }
                    case 3:{
                        fragmentClass = SettingsFragment.class;
                        break;
                    }

                }

                sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                    @Override
                    public void onDrawerOpened() {

                    }

                    @Override
                    public void onDrawerOpening(){

                    }

                    @Override
                    public void onDrawerClosing(){
                        System.out.println("Drawer closed");

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                        }
                    }

                    @Override
                    public void onDrawerClosed() {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        System.out.println("State "+newState);
                    }
                });

            }
        });
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//
//        Bitmap bitmap = null;
//        ImageView imageView = (ImageView) findViewById(R.id.image_create_training);
//
//
//        switch (requestCode) {
//            case GALLERY_REQUEST:
//                if (resultCode == RESULT_OK) {
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    try {
//                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    imageView.setImageBitmap(bitmap);
//
//                }
//
//        }
//    }



//    public void saveAsBitmap(View view, String filename) {
//        view.setDrawingCacheEnabled(true);
//        Bitmap bitmap = view.getDrawingCache();
//        try {
//            FileOutputStream out = openFileOutput(filename, MODE_PRIVATE);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//            out.close();
//        } catch (Exception ignored) {
//        }
//        bitmap.recycle();
//    }




    //связь

    public int myCurrentItemID;

    @Override
    public void onInputTrainingSent(Training training) {
        MyTrainingFragment.addTraining(training);
    }

    @Override
    public void onInputProgramSent(Program program) {
        MyProgramFragment.addProgram(program);
    }

    @Override
    public void onInputExerciseSent(Exercise exercise) {
        MyExerciseFragment.addExercise(exercise);
    }

    @Override
    public void onInputTrainingFromExerciseSent(TrainingFromExercise trainingFromExercise) {
        TrainingFragment.addTrainingFromExercise(trainingFromExercise);
    }

//    @Override
//    public void saveImage(String filename) {
//        try {
//            FileOutputStream out = openFileOutput(filename, MODE_PRIVATE);
//
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//            out.close();
//        } catch (Exception ignored) {
//        }
//        bitmap.recycle();
//    }
}