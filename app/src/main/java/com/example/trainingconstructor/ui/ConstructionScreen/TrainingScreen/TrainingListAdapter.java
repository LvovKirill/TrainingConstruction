package com.example.trainingconstructor.ui.ConstructionScreen.TrainingScreen;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingconstructor.DataBase.DataBase;
import com.example.trainingconstructor.DataBase.Training.Training;
import com.example.trainingconstructor.DataBase.TrainingFromExercise.TrainingFromExercise;
import com.example.trainingconstructor.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class TrainingListAdapter extends ListAdapter<Training, TrainingViewHolder> {

    private Context context;

    public TrainingListAdapter(@NonNull DiffUtil.ItemCallback<Training> diffCallback, Context context) {
        super(diffCallback);
        this.context = context;
    }


    @Override
    public TrainingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return TrainingViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(TrainingViewHolder holder, int position) {
        Training current = getItem(position);

        holder.bind(current, holder.itemView.getContext());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
                TrainingFragment myFragment = TrainingFragment.newInstance(current.getId());
                fragmentManager.beginTransaction().add(R.id.frameLayout, myFragment, "trainingFrag").setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack("trainingFrag")
                        .commit();

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showAlertDilog(current);
                return false;
            }
        });
    }

    public static class TrainingDiff extends DiffUtil.ItemCallback<Training> {

        @Override
        public boolean areItemsTheSame(@NonNull Training oldItem, @NonNull Training newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Training oldItem, @NonNull Training newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }

    private void showAlertDilog(Training current){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.no_yes_alert_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button button_yes = dialog.findViewById(R.id.button_yes);
        Button button_no = dialog.findViewById(R.id.button_no);
        TextView textView = dialog.findViewById(R.id.text);

        textView.setText(context.getString(R.string.delete_question) + " " + context.getString(R.string.training_inclination_1) + " \"" + current.getName()+"\"");

        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    TrainingFragment.trainingFromExerciseViewModel.deleteById(current.getId());
                    MyTrainingFragment.trainingViewModel.deleteById(current.getId());
                    dialog.cancel();
                }catch (Exception e){
                    Toast.makeText(context, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
                }
            }
        });

        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();

    }
}


class TrainingViewHolder extends RecyclerView.ViewHolder{

    private final TextView nameTraining;
    private final TextView timeTraining;
    private final ImageView image;
    private final ImageView icPress;
    private final ImageView icArm;
    private final ImageView icBack;
    private final ImageView icChest;
    private final ImageView icLeg;
    private final ImageView icSholders;

    private TrainingViewHolder(View itemView) {
        super(itemView);
        nameTraining = itemView.findViewById(R.id.name_training);
        timeTraining = itemView.findViewById(R.id.time_of_training);
        image = itemView.findViewById(R.id.image_training_item);
        icPress = itemView.findViewById(R.id.ic_press);
        icArm = itemView.findViewById(R.id.ic_arm);
        icBack = itemView.findViewById(R.id.ic_back);
        icChest = itemView.findViewById(R.id.ic_chest);
        icLeg = itemView.findViewById(R.id.ic_leg);
        icSholders = itemView.findViewById(R.id.ic_sholders);
    }

    public void bind(Training training, Context context) {
        nameTraining.setText(training.getName());
        image.setImageResource(training.getImg_id());

//        image.setImageBitmap(getImageBitmap(training.getImage_path()));
        int time=0;
        List<TrainingFromExercise> list = DataBase.getDatabase(context).trainingFromExerciseDao().getTrainingFromExerciseFromTrainingId(training.getId());
        for(TrainingFromExercise trainingFromExercise: list){
            time+=trainingFromExercise.getTime();
        }
        timeTraining.setText(String.valueOf(time)+" "+context.getResources().getString(R.string.min));

        if(training.isPress_type()){ icPress.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(training.isHands_type()){ icArm.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(training.isBack_type()){ icBack.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(training.isBreast_type()){ icChest.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(training.isFoot_type()){ icLeg.setColorFilter(Color.argb(255, 255, 255, 255));}
        if(training.isSholders_type()){ icSholders.setColorFilter(Color.argb(255, 255, 255, 255));}
    }
    @SuppressLint("ResourceAsColor")


    static TrainingViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_item, parent, false);
        return new TrainingViewHolder(view);
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("Error", "Error getting bitmap", e);
        }
        return bm;
    }


}
