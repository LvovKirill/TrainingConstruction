package com.example.trainingconstructor.InterfaceScreen.StatisticsScreen;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trainingconstructor.R;
import com.example.trainingconstructor.databinding.FragmentStatisticsBinding;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {

    FragmentStatisticsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false);

        ArrayList<BarEntry> visiotors = new ArrayList<>();
        visiotors.add(new BarEntry(1, 80));
        visiotors.add(new BarEntry(2, 90));
        visiotors.add(new BarEntry(3, 105));
        visiotors.add(new BarEntry(4, 95));
        visiotors.add(new BarEntry(5, 110));
        visiotors.add(new BarEntry(6, 105));
        visiotors.add(new BarEntry(7, 108));
        visiotors.add(new BarEntry(8, 115));
        visiotors.add(new BarEntry(9, 120));
        visiotors.add(new BarEntry(10, 125));

        BarDataSet barDataSet = new BarDataSet(visiotors, "Visiotors");

        int colorSet[] = {Color.rgb(3, 155, 229)};
        barDataSet.setColors(ColorTemplate.createColors(colorSet));


        BarData barData = new BarData(barDataSet);

        binding.barChart.setFitBars(true);
        binding.barChart.setData(barData);
        binding.barChart.setFilterTouchesWhenObscured(false);
        binding.barChart.setTouchEnabled(false);

        binding.barChart.getAxisRight().setDrawLabels(false);
//        binding.barChart.getAxisLeft().setDrawLabels(false);
        binding.barChart.getLegend().setEnabled(false);
        binding.barChart.getXAxis().setDrawGridLines(false);
        binding.barChart.getAxisRight().setEnabled(false);
        binding.barChart.getDescription().setEnabled(false);

        YAxis leftAxis = binding.barChart.getAxisLeft();
        leftAxis.setEnabled(false);




//        binding.barChart.setDrawGridBackground(true);
        binding.barChart.getAxisRight().setDrawLabels(false);
        binding.barChart.getAxisLeft().setDrawLabels(false);


        binding.barChart.getXAxis().setEnabled(true);
        binding.barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        binding.barChart.getXAxis().setDrawGridLines(false);
        binding.barChart.invalidate();

        binding.barChart.animateY(1200);



        //PieChart


        ArrayList<PieEntry> pieArray = new ArrayList<>();
        pieArray.add(new PieEntry(15, R.string.press));
        pieArray.add(new PieEntry(30, R.string.arm));
        pieArray.add(new PieEntry(10, R.string.back));
        pieArray.add(new PieEntry(32, R.string.chest));
        pieArray.add(new PieEntry(5, R.string.leg));
        pieArray.add(new PieEntry(8, R.string.shoulder));


        PieDataSet barDataSetForPieChart = new PieDataSet(pieArray, "pieArray");

        int colorSetForPie[] = {Color.rgb(0, 30, 255), Color.rgb(20, 45, 255), Color.rgb(40, 60, 255), Color.rgb(60, 75, 255), Color.rgb(80, 90, 255), Color.rgb(100, 100, 255)};
        barDataSetForPieChart.setColors(ColorTemplate.createColors(colorSetForPie));
        barDataSetForPieChart.setValueTextColor(Color.WHITE);


        PieData barDataPie = new PieData(barDataSetForPieChart);

        binding.pieChart.setData(barDataPie);
        binding.pieChart.setDrawSliceText(true);
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.getLegend().setEnabled(true);
        binding.pieChart.setCenterText("Группы мышц");
        binding.pieChart.animate();
        binding.barChart.animateY(1200);

        return binding.getRoot();
    }
}