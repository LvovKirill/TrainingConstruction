package com.example.trainingconstructor.ui.ConstructionScreen.GalleryScreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentGalleryBinding;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint.AddPointExersiseFragment;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.AddPoint.AddPointTrainingListAdapter;
import com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen.TrainingFromExerciseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GalleryFragment extends Fragment {

    FragmentGalleryBinding binding;
    List<Integer> currentList = new ArrayList<>();

    public static GalleryFragment newInstance(String flag) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString("flag", flag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);

        createCurrentList(getArguments().getString("flag"));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        binding.galleryRv.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.galleryRv);

        GalleryAdapter recyclerAdapter = new GalleryAdapter(currentList, getArguments().getString("flag"));
        binding.galleryRv.setAdapter(recyclerAdapter);
        binding.galleryRv.setLayoutManager(new LinearLayoutManager(getActivity()));


        return binding.getRoot();
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(currentList, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };



    void createCurrentList(String flag){
        switch (flag){
            case "program":
                currentList.add(R.drawable.sport_men);
                currentList.add(R.drawable.image_dg_1);
                currentList.add(R.drawable.image_dg_2);
                currentList.add(R.drawable.image_dg_3);
                currentList.add(R.drawable.image_dg_4);
                currentList.add(R.drawable.image_dg_5);
                currentList.add(R.drawable.image_dg_6);
                break;
            case "training":
                currentList.add(R.drawable.sport_men);
//                currentList.add(R.drawable.image_dg_1);
//                currentList.add(R.drawable.image_dg_2);
                currentList.add(R.drawable.image_dg_3);
                currentList.add(R.drawable.image_dg_4);
                currentList.add(R.drawable.image_dg_5);
                currentList.add(R.drawable.image_dg_6);
                currentList.add(R.drawable.image_dg_7);
                currentList.add(R.drawable.image_dg_8);
                currentList.add(R.drawable.image_dg_9);
                currentList.add(R.drawable.image_dg_10);
                currentList.add(R.drawable.image_dg_11);
                currentList.add(R.drawable.image_dg_12);
                currentList.add(R.drawable.image_dg_13);
                currentList.add(R.drawable.image_dg_14);
                break;
            case "exercise":
                currentList.add(R.drawable.image_dg_ex_1);
                currentList.add(R.drawable.image_dg_ex_2);
                currentList.add(R.drawable.image_dg_ex_3);
                currentList.add(R.drawable.image_dg_ex_4);
                currentList.add(R.drawable.image_dg_ex_5);
//                currentList.add(R.drawable.image_dg_ex_6);
//                currentList.add(R.drawable.image_dg_ex_7);
                currentList.add(R.drawable.image_dg_ex_8);
                currentList.add(R.drawable.image_dg_ex_9);
                currentList.add(R.drawable.image_dg_ex_10);
                currentList.add(R.drawable.image_dg_ex_11);
                currentList.add(R.drawable.image_dg_ex_12);
                break;
        }

    }
}