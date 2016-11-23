package com.example.hp.dramaapp.Ayush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hp.dramaapp.R;

import org.w3c.dom.Text;

public class ResultActivity extends AppCompatActivity {

    TextView typeofperformance;
    TextView testScore, actorName;
    ListView listView;
    String[] parametersName = {"Profile Matching %", "Dialogues Delivery%"};
    String[] parametersValue = {"Profile Matching %", "Dialogues Delivery%"};
    String actorStringName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);
        init();
        typeofperformance=(TextView)findViewById(R.id.typeofperformance);
        Bundle bund = getIntent().getExtras();
        parametersValue[0] = bund.getString("ProfilePercentage");
        parametersValue[1] = bund.getString("DialoguePercent");
        actorStringName = bund.getString("Actor");

        adapterWordsListView customAdapter = new adapterWordsListView(this,
                parametersName,
                parametersValue
        );

        listView.setAdapter(customAdapter);
        System.out.println((Float.parseFloat(parametersValue[0]) + Float.parseFloat(parametersValue[1])));
        System.out.println((Float.parseFloat(parametersValue[0]) + Float.parseFloat(parametersValue[1]) )/ 2 + "");
        float ans= (float) ((Float.parseFloat(parametersValue[0]) + 1.2*Float.parseFloat(parametersValue[1])) / 2);
        String a=String.format("%.2f",ans);
        testScore.setText(a+ "");

        actorName.setText(actorStringName);
        String performance;
        if(ans<=40)
        {
            performance="Needs Improvement";
        }
        else if(ans>40 && ans<=55)
        {
            performance="Average";
        }
        else if(ans>55 && ans<=75)
        {
            performance="Good";
        }
        else{
            performance="Very Nice";
        }
         typeofperformance=(TextView)findViewById(R.id.typeofperformance);
        typeofperformance.setText(performance);
    }

    void init() {
        testScore = (TextView) findViewById(R.id.textView4);
        actorName = (TextView) findViewById(R.id.textView);
        listView = (ListView) findViewById(R.id.listView);




    }

    @Override
    public void onBackPressed() {
        //
        Intent i=new Intent(new Intent(this,ListPlays.class));
        startActivity(i);
        finish();
    }
}
