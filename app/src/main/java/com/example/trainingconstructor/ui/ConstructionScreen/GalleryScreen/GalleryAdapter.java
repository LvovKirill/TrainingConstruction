package com.example.trainingconstructor.ui.ConstructionScreen.GalleryScreen;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingconstructor.DataBase.Exercise.Exercise;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.R;
import com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen.CreateExerciseFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.ExerciseScreen.MyExerciseFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.ProgramScreen.CreateProgramFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint.AddPointExersiseFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.CreateTrainingFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.TrainingFromExerciseAdapter;
import com.example.trainingconstructor.ui.MainActivity;

import java.util.List;


    public class GalleryAdapter extends RecyclerView.Adapter {

        private static final String TAG = "RecyclerAdapter";
        List<Integer> currentList;
        String typeCreate;

        public GalleryAdapter(List<Integer> currentList, String typeCreate) {
            this.currentList = currentList;
            this.typeCreate = typeCreate;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view;

                view = layoutInflater.inflate(R.layout.gallery_item, parent, false);
                return new ViewHolderOne(view);

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
                Integer res = currentList.get(position);
                viewHolderOne.imageGallery.setImageResource(res);
                viewHolderOne.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        mainActivity.getSupportFragmentManager().findFragmentByTag("createTrainingFrag").onStart();
//                        mainActivity.onBackPressed();
                        if(typeCreate.equals("training")) {
                            ((AppCompatActivity) viewHolderOne.itemView.getContext()).onBackPressed();
                            CreateTrainingFragment.setImgId(res);
                            ((AppCompatActivity) viewHolderOne.itemView.getContext()).getSupportFragmentManager().findFragmentByTag("createTrainingFrag").onStart();
                        }else if(typeCreate.equals("exercise")){
                            ((AppCompatActivity) viewHolderOne.itemView.getContext()).onBackPressed();
                            CreateExerciseFragment.setImgID(res);
                            ((AppCompatActivity) viewHolderOne.itemView.getContext()).getSupportFragmentManager().findFragmentByTag("createExerciseFrag").onStart();
                        }else if(typeCreate.equals("program")) {
                            ((AppCompatActivity) viewHolderOne.itemView.getContext()).onBackPressed();
                            CreateProgramFragment.setImgId(res);
                            ((AppCompatActivity) viewHolderOne.itemView.getContext()).getSupportFragmentManager().findFragmentByTag("createProgramFrag").onStart();
                        }


                    }
                });

                viewHolderOne.itemView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        Animation scaleUp = AnimationUtils.loadAnimation(((AppCompatActivity) viewHolderOne.itemView.getContext()), R.anim.puls);
                        Animation scaleDown = AnimationUtils.loadAnimation(((AppCompatActivity) viewHolderOne.itemView.getContext()), R.anim.down_button_anim);

                            if(event.getAction()==MotionEvent.ACTION_UP){}
//                binding.addTrainingLayout.startAnimation(scaleUp);
                            else if(event.getAction()==MotionEvent.ACTION_DOWN)
                                viewHolderOne.itemView.startAnimation(scaleUp);

                        return false;
                    }
                });
        }

        @Override
        public int getItemCount() {
            return currentList.size();
        }

        class ViewHolderOne extends RecyclerView.ViewHolder {

            private final ImageView imageGallery;

            public ViewHolderOne(@NonNull View itemView) {
                super(itemView);
                imageGallery = itemView.findViewById(R.id.image_gallery);
            }
        }
    }
