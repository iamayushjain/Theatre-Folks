package com.example.hp.dramaapp.Ayush;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hp.dramaapp.R;

import java.util.ArrayList;

/**
 * Created by PASSION IS LIFE on 20-10-2016.
 */

public class OptionsClass extends Activity {
    TextView textView, textView1;
    Button trainingButton, testButton;
    String actor, actorScore,playName;
    ArrayList<String> dialoguesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_layout);
        init();
    }

    void init() {
        textView = (TextView) findViewById(R.id.textView);
        textView1 = (TextView) findViewById(R.id.textView1);
        trainingButton = (Button) findViewById(R.id.button2);
        testButton = (Button) findViewById(R.id.button1);
        ((RelativeLayout)findViewById(R.id.relativeLayout1)).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_slide_in));

        Bundle bund = getIntent().getExtras();
        actor = bund.getString("Actor");
        actorScore = bund.getString("ProfilePercentage");
        playName = bund.getString("Play");
        dialoguesList = bund.getStringArrayList("Dialogues");

        textView.setText(actor);
        textView1.setText(bund.getString("Intro"));
        System.out.println(actorScore);
        System.out.println(playName);
        System.out.println(dialoguesList.toString());

        trainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionsClass.this, RecorderActivityPractise.class);
                intent.putExtra("Actor", actor);
                intent.putExtra("ProfilePercentage", actorScore);
                intent.putExtra("Dialogues", dialoguesList);

                intent.putExtra("Play", playName);
                startActivity(intent);

            }
        });
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionsClass.this, RecorderActivity.class);
                intent.putExtra("Actor", actor);
                intent.putExtra("ProfilePercentage", actorScore);
                intent.putExtra("Dialogues", dialoguesList);
                intent.putExtra("Play", playName);
                startActivity(intent);

            }
        });

    }
}
