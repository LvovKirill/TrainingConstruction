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
import androidx.appcompat.app.AppCompatActivity;
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

import com.example.trainingconstructor.DataBase.Program.Program;
import com.example.trainingconstructor.DataBase.Training.Training;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentCreateTrainingBinding;
import com.example.trainingconstructor.ui.ConstructionScreen.GalleryScreen.GalleryFragment;
import com.example.trainingconstructor.ui.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

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
import static android.os.Environment.getExternalStorageDirectory;
import static android.provider.Telephony.Mms.Part.FILENAME;
import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

public class CreateTrainingFragment extends BottomSheetDialogFragment {

    private FragmentListener listener;
    protected static int imgId = R.drawable.sport_men;
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
                    Training training = new Training(name, press_type, hands_type, foot_type, back_type, breast_type, sholders_type, saveImage(), imgId);
//                    onLoadImage();


                    if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        saveImage();

                        listener.onInputTrainingSent(training);

                        getActivity().onBackPressed();
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

        binding.defaultGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                GalleryFragment myFragment = GalleryFragment.newInstance("training");
                fragmentManager.beginTransaction().add(R.id.frameLayout, myFragment, "trainingFrag").setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack("myStack")
                        .commit();
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
        File filepath = getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath()+"/Training_Constructor/");
        File file = new File(dir, System.currentTimeMillis()+".jpg");

        try{
            outputStream = new FileOutputStream(file);
        }catch (Exception e){
            e.printStackTrace();
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

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

        return file.getAbsolutePath();
    }


    public static void setImgId(int imgId) {
        CreateTrainingFragment.imgId = imgId;
    }

    public static int getImgId() {
        return imgId;
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.imageCreateTraining.setImageResource(imgId);
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

    private String saveImage() {


        BitmapDrawable drawable = (BitmapDrawable) binding.imageCreateTraining.getDrawable();
        Bitmap bitmap = drawable.getBitmap();



        File f = new File(getActivity().getCacheDir(), System.currentTimeMillis()+".jpg");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100/*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();


        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bitmapdata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getExternalStorageDirectory().getAbsolutePath() + f.getPath();
    }
}