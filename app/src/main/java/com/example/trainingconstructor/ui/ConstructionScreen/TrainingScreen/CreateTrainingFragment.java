package com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trainingconstructor.DataBase.Training.Training;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentCreateTrainingBinding;
import com.example.trainingconstructor.ui.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Time;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.provider.Telephony.Mms.Part.FILENAME;
import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

public class CreateTrainingFragment extends Fragment {

    private FragmentListener listener;
    static Bitmap photo;
    static final int GALLERY_REQUEST = 1;
    private static int REQUEST_CODE = 100;

    FragmentCreateTrainingBinding binding;

    OutputStream outputStream;

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
                    onLoadImage();


                    if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        saveImage();

                        listener.onInputTrainingSent(training);

                        FragmentManager fragmentManager = getFragmentManager();

                        TrainingFragment myFragment = TrainingFragment.newInstance(training.getId());
                        fragmentManager.beginTransaction().add(R.id.frameLayout, myFragment).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                                .addToBackStack("myStack")
                                .commit();
                    }else {
                        askPermission();
                    }

                }

            }
        });


        binding.gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);

            }
        });


        return binding.getRoot();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.imageCreateTraining.setImageBitmap(bitmap);

                }

        }
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

    public String onLoadImage(){
        BitmapDrawable drawable = (BitmapDrawable)binding.imageCreateTraining.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
//        File filepath = Environment.getExternalStorageDirectory();
//        File dir = new File(filepath.getAbsolutePath()+"/Training_Constructor/");
//        File file = new File(dir, System.currentTimeMillis()+".jpg");
//
//        try{
//            outputStream = new FileOutputStream(file);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//        Toast.makeText(getActivity(), file.getAbsolutePath(), Toast.LENGTH_LONG).show();
//
//        try {
//            outputStream.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            outputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        OutputStream fOut = null;
//        Time time = new Time();
//        time.setToNow();

        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(getActivity().openFileOutput("FILENAME", MODE_PRIVATE)));
//            Toast.makeText(getActivity(), FILN)
            // пишем данные
            bw.write("Содержимое файла");
            // закрываем поток
            bw.close();
            Log.d(LOG_TAG, "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "filename12345";
    }


    private void askPermission() {
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                saveImage();
            }else {
                Toast.makeText(getActivity(),"Please provide the required permissions",Toast.LENGTH_SHORT).show();
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void saveImage() {

        File dir = new File(Environment.getExternalStorageDirectory(),"TrainingConstruction");

        if (!dir.exists()){

            dir.mkdir();

        }

        BitmapDrawable drawable = (BitmapDrawable) binding.imageCreateTraining.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        File file = new File(dir,System.currentTimeMillis()+".jpg");

        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            Toast.makeText(getActivity(),"Successfully Saved",Toast.LENGTH_SHORT).show();

        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}