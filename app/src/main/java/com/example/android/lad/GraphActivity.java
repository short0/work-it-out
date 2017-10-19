package com.example.android.lad;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class GraphActivity extends AppCompatActivity {

    DatabaseHandler database;
    ArrayList<ExerciseRecord> exerciseRecords;
    int i=0;
    private Button lineBtn;
    private Button barBtn;
    private Button pointBtn;
    final ArrayList<Double> weights = new ArrayList<Double>();
    final ArrayList<Double> bodyFatPercentages = new ArrayList<Double>();
    final ArrayList<String> dates = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        lineBtn = (Button) findViewById(R.id.lineBtn);
        barBtn = (Button) findViewById(R.id.barBtn);
        pointBtn = (Button) findViewById(R.id.pointBtn);
        database = new DatabaseHandler(getApplicationContext());
        exerciseRecords = database.getAllRecords();

        for(ExerciseRecord e: exerciseRecords)
        {
            weights.add(Double.parseDouble(e.getmWeight()));
            bodyFatPercentages.add(Double.parseDouble(e.getmBodyFatPercentage()));
            dates.add(e.getmDate());
        }
        final GraphView graph = (GraphView) findViewById(R.id.graph);
        final GraphView graph2 = (GraphView) findViewById(R.id.graph2);
        final LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        final LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>();
        final BarGraphSeries<DataPoint> series3 = new BarGraphSeries<DataPoint>();
        final BarGraphSeries<DataPoint> series4 = new BarGraphSeries<DataPoint>();
        final PointsGraphSeries<DataPoint> series5 = new PointsGraphSeries<DataPoint>();
        final PointsGraphSeries<DataPoint> series6 = new PointsGraphSeries<DataPoint>();

        Collections.sort(exerciseRecords,new WeightComparator()); //even doing anything?
        int count = 0;
        for (int q = exerciseRecords.size()-1;q>=0;q--)
        {
            int x;
            double y;
            x = count;
            y = weights.get(q);
            series.appendData(new DataPoint(x,y),true,exerciseRecords.size());
            series3.appendData(new DataPoint(x,y),true,exerciseRecords.size());
            series5.appendData(new DataPoint(x,y),true,exerciseRecords.size());
            i++;
            count++;
        }
        ArrayList<String> tempDates = new ArrayList<String>();
        for (ExerciseRecord e : exerciseRecords)
        {
            tempDates.add(e.getmDate());
        }
        Collections.sort(tempDates);
        final String dateLabels[] = new String[i];
        for (int q =0; q<i; q++)
        {
            dateLabels[q] = tempDates.get(q).substring(0, 5);
        }
        final StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);

        for (int q=0; q<exerciseRecords.size();q++)
        {
            int x;
            double y;
            x = q;
            y = bodyFatPercentages.get(q);
            series2.appendData(new DataPoint(x,y),true,exerciseRecords.size());
            series4.appendData(new DataPoint(x,y),true,exerciseRecords.size());
            series6.appendData(new DataPoint(x,y),true,exerciseRecords.size());
        }

        final StaticLabelsFormatter staticLabelsFormatter2 = new StaticLabelsFormatter(graph2);



        lineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (exerciseRecords.size() == 0)
                {
                    Toast.makeText(getApplicationContext(),"Please add some exercise records before trying to add line graph",Toast.LENGTH_SHORT).show();
                }
                else {
                    // Clear previous graph
                    graph.removeAllSeries();
                    graph2.removeAllSeries();

                    Toast.makeText(getApplicationContext(), "Adding line graph", Toast.LENGTH_SHORT).show();
                    series.setColor(Color.GREEN);
                    series.setTitle("Line Graph");
                    graph.addSeries(series);
                    staticLabelsFormatter.setHorizontalLabels(dateLabels);
                    graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                    graph.getLegendRenderer().setVisible(true);
                    graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

                    series2.setColor(Color.GREEN);
                    series2.setTitle("Line Graph");
                    graph2.addSeries(series2);
                    staticLabelsFormatter2.setHorizontalLabels(dateLabels);
                    graph2.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter2);
                    graph2.getLegendRenderer().setVisible(true);
                    graph2.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

//                lineBtn.setClickable(false);
                }

            }

        });

        barBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (exerciseRecords.size() == 0)
                {
                    Toast.makeText(getApplicationContext(),"Please add some exercise records before trying to add bar graph",Toast.LENGTH_SHORT).show();
                }
                else {
                    // Clear previous graph
                    graph.removeAllSeries();
                    graph2.removeAllSeries();

                    Toast.makeText(getApplicationContext(), "Adding bar graph", Toast.LENGTH_SHORT).show();
                    //series3.setColor(Color.MAGENTA);
                    series3.setTitle("Bar Graph");
                    graph.addSeries(series3);
                    staticLabelsFormatter.setHorizontalLabels(dateLabels);
                    graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                    graph.getLegendRenderer().setVisible(true);
                    graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                    series3.setSpacing(15);

                    //series4.setColor(Color.MAGENTA);
                    series4.setTitle("Bar Graph");
                    graph2.addSeries(series4);
                    staticLabelsFormatter2.setHorizontalLabels(dateLabels);
                    graph2.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter2);
                    graph2.getLegendRenderer().setVisible(true);
                    graph2.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

//                barBtn.setClickable(false);
                }
            }
        });

        pointBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                if (exerciseRecords.size() == 0)
                {
                    Toast.makeText(getApplicationContext(),"Please add some exercise records before trying to add line graph",Toast.LENGTH_SHORT).show();
                }
                else {
                    // Clear previous graph
                    graph.removeAllSeries();
                    graph2.removeAllSeries();

                    Toast.makeText(getApplicationContext(), "Adding point graph", Toast.LENGTH_SHORT).show();
                    series5.setColor(Color.MAGENTA);
                    series5.setTitle("Point Graph");
                    graph.addSeries(series5);
                    staticLabelsFormatter.setHorizontalLabels(dateLabels);
                    graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                    graph.getLegendRenderer().setVisible(true);
                    graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

                    series6.setColor(Color.MAGENTA);
                    series6.setTitle("Point Graph");
                    graph2.addSeries(series6);
                    staticLabelsFormatter2.setHorizontalLabels(dateLabels);
                    graph2.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter2);
                    graph2.getLegendRenderer().setVisible(true);
                    graph2.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

//                pointBtn.setClickable(false);
                }
            }
        });

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(),"Line graph: on data point clicked:" + dataPoint,Toast.LENGTH_SHORT).show();
            }
        });
        series2.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(),"Line graph: on data point clicked:" + dataPoint,Toast.LENGTH_SHORT).show();
            }
        });
        series3.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(),"Bar graph: on data point clicked:" + dataPoint,Toast.LENGTH_SHORT).show();
            }
        });
        series4.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(),"Bar graph: on data point clicked:" + dataPoint,Toast.LENGTH_SHORT).show();
            }
        });
        series5.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(),"Point graph: on data point clicked:" + dataPoint,Toast.LENGTH_SHORT).show();
            }
        });
        series6.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(),"Point graph: on data point clicked:" + dataPoint,Toast.LENGTH_SHORT).show();
            }
        });

    }


}


class WeightComparator implements Comparator<ExerciseRecord>
{
    public int compare(ExerciseRecord e1, ExerciseRecord e2)
    {
        return e1.getmWeight().compareTo(e2.getmWeight());
    }
}
