//package com.example.trainingconstructor.ui.CalendarScreen;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentManager;
//import androidx.recyclerview.widget.DiffUtil;
//import androidx.recyclerview.widget.ListAdapter;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.trainingconstructor.DataBase.CalendarEvent.CalendarEvent;
//import com.example.trainingconstructor.DataBase.CalendarEvent.CalendarEvent;
//import com.example.trainingconstructor.R;
//import com.example.trainingconstructor.ui.ConstructionScreen.CalendarEventScreen.MyCalendarEventFragment;
//import com.example.trainingconstructor.ui.ConstructionScreen.CalendarEventScreen.CalendarEventFragment;
//
//
//public class CurrentEventsAdapter extends ListAdapter<CalendarEvent> {
//
//    private Context context;
//
//    public CalendarEventListAdapter(@NonNull DiffUtil.ItemCallback<CalendarEvent> diffCallback, Context context) {
//        super(diffCallback);
//        this.context = context;
//    }
//
//    @Override
//    public CalendarEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return CalendarEventViewHolder.create(parent);
//    }
//
//    @Override
//    public void onBindViewHolder(CalendarEventViewHolder holder, int position) {
//        CalendarEvent current = getItem(position);
//        holder.bind(current.getName(), current.getComplexity(), current.getCycle());
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                FragmentManager fragmentManager = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
//                CalendarEventFragment myFragment = CalendarEventFragment.newInstance(current.getId());
//                Log.d("MyTag", String.valueOf(current.getId()));
//                fragmentManager.beginTransaction().add(R.id.frameLayout, myFragment).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
//                        .addToBackStack("myStack")
//                        .commit();
//
//            }
//        });
//
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                showAlertDilog(current);
//                return false;
//            }
//        });
//    }
//
//
//    public static class CalendarEventDiff extends DiffUtil.ItemCallback<CalendarEvent> {
//
//        @Override
//        public boolean areItemsTheSame(@NonNull CalendarEvent oldItem, @NonNull CalendarEvent newItem) {
//            return oldItem == newItem;
//        }
//
//        @Override
//        public boolean areContentsTheSame(@NonNull CalendarEvent oldItem, @NonNull CalendarEvent newItem) {
//            return oldItem.getName().equals(newItem.getName());
//        }
//    }
//
//
//    private void showAlertDilog(CalendarEvent current){
//        Dialog dialog = new Dialog(context);
//        dialog.setContentView(R.layout.no_yes_alert_dialog);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//        Button button_yes = dialog.findViewById(R.id.button_yes);
//        Button button_no = dialog.findViewById(R.id.button_no);
//        TextView textView = dialog.findViewById(R.id.text);
//
//        textView.setText(context.getString(R.string.delete_question) + " " + context.getString(R.string.program_inclination_1) + " \"" + current.getName()+"\" ?");
//
//        button_yes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    MyCalendarEventFragment.programViewModel.deleteById(current.getId());
//                    dialog.cancel();
//                }catch (Exception e){
//                    Toast.makeText(context, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//        button_no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.cancel();
//            }
//        });
//
//        dialog.show();
//
//    }
//}
//
//class CalendarEventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//
//    private final TextView nameCalendarEvent;
//    private final TextView cycleTextView;
//    private final ImageView oneFlash;
//    private final ImageView twoFlash;
//    private final ImageView threeFlash;
//
//    private CalendarEventViewHolder(View itemView) {
//        super(itemView);
//        nameCalendarEvent = itemView.findViewById(R.id.name_program);
//        oneFlash = itemView.findViewById(R.id.oneFlash);
//        twoFlash = itemView.findViewById(R.id.twoFlash);
//        threeFlash = itemView.findViewById(R.id.threeFlash);
//        cycleTextView = itemView.findViewById(R.id.cycleTextView);
//
//        itemView.setOnClickListener(this);
//    }
//
//    public void bind(String text, Float complexity, int cycle) {
//        nameCalendarEvent.setText(text);
//        cycleTextView.setText(String.valueOf(cycle) + " недели");
//
//        if(complexity==1.0){oneFlash.setColorFilter(Color.argb(255, 255, 255, 255));}
//        if(complexity==2.0){
//            oneFlash.setColorFilter(Color.argb(255, 255, 255, 255));
//            twoFlash.setColorFilter(Color.argb(255, 255, 255, 255));}
//        if(complexity==3.0){
//            oneFlash.setColorFilter(Color.argb(255, 255, 255, 255));
//            twoFlash.setColorFilter(Color.argb(255, 255, 255, 255));
//            threeFlash.setColorFilter(Color.argb(255, 255, 255, 255));}
//    }
//
//    static com.example.trainingconstructor.ui.ConstructionScreen.CalendarEventScreen.CalendarEventViewHolder create(ViewGroup parent) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.program_item, parent, false);
//        return new com.example.trainingconstructor.ui.ConstructionScreen.CalendarEventScreen.CalendarEventViewHolder(view);
//    }
//
//    @Override
//    public void onClick(View v) {
//    }
//}