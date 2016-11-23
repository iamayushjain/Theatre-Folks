package com.example.hp.dramaapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hp.dramaapp.Ayush.ListPlays;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String[] quest = {"Height", "Weight", "Complexion", "Gender", "Genre", "Age"};
    String[][] options =
            {
                    {"Tall", "Short", "Average"},
                    {"Fit", "Fat", "Thin"},
                    {"Fair", "Dark", "Average"},
                    {"Male", "Female", "Flexible"},
                    {"Comedy", "Serious", "Flexible"},
                    {"Child", "Young", "Old"}};
    TextView option1, option2, option3, option4, option5, option6, option7, option8, option9, questions;
    int counter = 0;
    ImageButton cancel_button_1;
    ImageButton cancel_button_2;
    ImageButton cancel_button_3;
    ImageButton cancel_button_4;
    ImageButton cancel_button_5;
    ImageButton cancel_button_6;
    ArrayList<String> answer_keys = new ArrayList();
    Button submit;
    RelativeLayout relativeLayoutMain, relativelayoutoption1, relativelayoutoption2, relativelayoutoption3, relativelayoutoption4, relativelayoutoption5, relativelayoutoption6, relativelayoutok;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //int i=0;
        relativelayoutok = (RelativeLayout) findViewById(R.id.relativeLayoutOk);
        relativeLayoutMain = (RelativeLayout) findViewById(R.id.relativeLayout1);
        relativelayoutoption1 = (RelativeLayout) findViewById(R.id.relativeLayoutoption1);
        relativelayoutoption2 = (RelativeLayout) findViewById(R.id.relativeLayoutoption2);
        relativelayoutoption3 = (RelativeLayout) findViewById(R.id.relativeLayoutoption3);
        relativelayoutoption4 = (RelativeLayout) findViewById(R.id.relativeLayoutoption4);
        relativelayoutoption5 = (RelativeLayout) findViewById(R.id.relativeLayoutoption5);
        relativelayoutoption6 = (RelativeLayout) findViewById(R.id.relativeLayoutoption6);

        submit = (Button) findViewById(R.id.submit);
        visibilty_for_relative_layouts();
        option1 = (TextView) findViewById(R.id.option1);
        option2 = (TextView) findViewById(R.id.option2);
        option3 = (TextView) findViewById(R.id.option3);
        option4 = (TextView) findViewById(R.id.answer1);
        //option4.setVisibility(View.GONE);
        option5 = (TextView) findViewById(R.id.answer2);
        //option5.setVisibility(View.GONE);
        option6 = (TextView) findViewById(R.id.answer3);
        //option6.setVisibility(View.GONE);
        option7 = (TextView) findViewById(R.id.answer4);
        //option7.setVisibility(View.GONE);
        option8 = (TextView) findViewById(R.id.answer5);
        //option8.setVisibility(View.GONE);
        option9 = (TextView) findViewById(R.id.answer6);
        //option9.setVisibility(View.GONE);
        cancel_button_1 = (ImageButton) findViewById(R.id.cancelButton1);
        cancel_button_2 = (ImageButton) findViewById(R.id.cancelButton2);
        cancel_button_3 = (ImageButton) findViewById(R.id.cancelButton3);
        cancel_button_4 = (ImageButton) findViewById(R.id.cancelButton4);
        cancel_button_5 = (ImageButton) findViewById(R.id.cancelButton5);
        cancel_button_6 = (ImageButton) findViewById(R.id.cancelButton6);
        questions = (TextView) findViewById(R.id.question);
        option1.setText(options[counter][0]);
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 1;
                features(i);
            }
        });
        option2.setText(options[counter][1]);
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 2;
                features(i);
            }
        });
        option3.setText(options[counter][2]);
        option3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int i = 3;
                features(i);
            }
        });
        cancel_button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer_keys.clear();
                //counter=0;
                ini();
                visibilty_for_relative_layouts();

            }
        });
        cancel_button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = 1;
                remove_keys(1);
                setQuestion2();
                relativelayoutok.setVisibility(View.GONE);
                relativelayoutoption2.setVisibility(View.GONE);
                relativelayoutoption3.setVisibility(View.GONE);
                relativelayoutoption4.setVisibility(View.GONE);
                relativelayoutoption5.setVisibility(View.GONE);
                relativelayoutoption6.setVisibility(View.GONE);

            }
        });
        cancel_button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = 2;
                remove_keys(2);
                setQuestion3();
                relativelayoutok.setVisibility(View.GONE);
                relativelayoutoption3.setVisibility(View.GONE);
                relativelayoutoption4.setVisibility(View.GONE);
                relativelayoutoption5.setVisibility(View.GONE);
                relativelayoutoption6.setVisibility(View.GONE);

            }
        });
        cancel_button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = 3;
                remove_keys(3);
                setQuestion4();
                relativelayoutok.setVisibility(View.GONE);
                relativelayoutoption4.setVisibility(View.GONE);
                relativelayoutoption5.setVisibility(View.GONE);
                relativelayoutoption6.setVisibility(View.GONE);

            }
        });
        cancel_button_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = 4;
                remove_keys(4);
                setQuestion5();
                relativelayoutok.setVisibility(View.GONE);
                relativelayoutoption5.setVisibility(View.GONE);
                relativelayoutoption6.setVisibility(View.GONE);

            }
        });
        cancel_button_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = 5;
                remove_keys(5);
                setQuestion6();
                relativelayoutok.setVisibility(View.GONE);
                relativelayoutoption6.setVisibility(View.GONE);

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String choice[]= (String[]) answer_keys.toArray();
                String choice1 = "";
//                SharedPreferences sp = getSharedPreferences("MM", 0);
//                String params = sp.getString("KK", "");
//try {
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    final DatabaseReference myRef1 = database.getReference(params.replaceAll("[^A-Za-z]", ""));
    for (String s : answer_keys) {
        choice1 += "," + s;
    }
//}
//                catch (Exception e)
//                {
//
//                }
                StringBuilder sb = new StringBuilder(choice1);
                sb.deleteCharAt(0);
                SharedPreferences sp1 = getSharedPreferences("Choices", 0);
                SharedPreferences.Editor ed1 = sp1.edit();
                ed1.putString("Parameters", sb.toString());
                ed1.commit();
                Intent intent=new Intent(MainActivity.this, ListPlays.class);
                startActivity(intent);
                // Toast.makeText(getApplicationContext(),choice1,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void features(int i) {
        relativeLayoutMain.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_slide_in));
        counter++;
        try {
            questions.setText(quest[counter]);
        } catch (Exception e) {
            questions.setText(quest[counter - 1]);
        }
        if (counter == 1) {
            if (i == 1)
                option4.setText(option1.getText());
            else if (i == 2)
                option4.setText(option2.getText());
            else option4.setText(option3.getText());
            //option4.setVisibility(View.VISIBLE);
            relativelayoutoption1.setVisibility(View.VISIBLE);    //relative layout option1 set visibilty true
            answer_keys.add(option4.getText().toString());
            option1.setText(options[counter][0]);
            option2.setText(options[counter][1]);
            option3.setText(options[counter][2]);

        } else if (counter == 2) {
            //   counter++;
            questions.setText(quest[counter]);
            if (i == 1)
                option5.setText(option1.getText());
            else if (i == 2)
                option5.setText(option2.getText());
            else option5.setText(option3.getText());
            //option5.setVisibility(View.VISIBLE);
            relativelayoutoption2.setVisibility(View.VISIBLE);    //relative layout option2 set visibilty true
            answer_keys.add(option5.getText().toString());
            option1.setText(options[counter][0]);
            option2.setText(options[counter][1]);
            option3.setText(options[counter][2]);
        } else if (counter == 3) {
            //  counter++;
            questions.setText(quest[counter]);
            if (i == 1)
                option6.setText(option1.getText());
            else if (i == 2)
                option6.setText(option2.getText());
            else option6.setText(option3.getText());
            //option6.setVisibility(View.VISIBLE);
            relativelayoutoption3.setVisibility(View.VISIBLE);    //relative layout option3 set visibilty true
            answer_keys.add(option6.getText().toString());
            option1.setText(options[counter][0]);
            option2.setText(options[counter][1]);
            option3.setText(options[counter][2]);
        } else if (counter == 4) {
            // counter++;
            questions.setText(quest[counter]);
            if (i == 1)
                option7.setText(option1.getText());
            else if (i == 2)
                option7.setText(option2.getText());
            else option7.setText(option3.getText());
            //     option7.setVisibility(View.VISIBLE);
            relativelayoutoption4.setVisibility(View.VISIBLE);    //relative layout option4 set visibilty true
            answer_keys.add(option7.getText().toString());
            option1.setText(options[counter][0]);
            option2.setText(options[counter][1]);
            option3.setText(options[counter][2]);
        } else if (counter == 5) {
            // counter++;
            questions.setText(quest[counter]);
            if (i == 1)
                option8.setText(option1.getText());
            else if (i == 2)
                option8.setText(option2.getText());
            else option8.setText(option3.getText());
            // option8.setVisibility(View.VISIBLE);
            relativelayoutoption5.setVisibility(View.VISIBLE);    //relative layout option5 set visibilty true
            answer_keys.add(option8.getText().toString());
            option1.setText(options[counter][0]);
            option2.setText(options[counter][1]);
            option3.setText(options[counter][2]);
        } else if (counter == 6) {
            // counter++;
            counter = -1;
            //questions.setText(quest[counter]);
            if (i == 1)
                option9.setText(option1.getText());
            else if (i == 2)
                option9.setText(option2.getText());
            else option9.setText(option3.getText());
            // option9.setVisibility(View.VISIBLE);
            relativelayoutoption6.setVisibility(View.VISIBLE);    //relative layout option6 set visibilty true
            answer_keys.add(option9.getText().toString());
            relativelayoutok.setVisibility(View.VISIBLE);
            relativelayoutok.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.in_middle));
//            option1.setText(options[counter][0]);
//            option2.setText(options[counter][1]);
//            option3.setText(options[counter][2]);
        }
    }

    public void remove_keys(int j) {
        for (; j < answer_keys.size(); j++)
            answer_keys.remove(j);
    }

    public void ini() {
        counter = 0;
        questions.setText("Height");
        option1.setText(options[0][0]);
        option2.setText(options[0][1]);
        option3.setText(options[0][2]);

    }

    public void visibilty_for_relative_layouts() {
        relativelayoutoption1.setVisibility(View.GONE);
        relativelayoutoption2.setVisibility(View.GONE);
        relativelayoutoption3.setVisibility(View.GONE);
        relativelayoutoption4.setVisibility(View.GONE);
        relativelayoutoption5.setVisibility(View.GONE);
        relativelayoutoption6.setVisibility(View.GONE);
        relativelayoutok.setVisibility(View.GONE);
    }

    public void setQuestion2() {
        relativeLayoutMain.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_slide_in));
        questions.setText(quest[1]);
        option1.setText(options[1][0]);
        option2.setText(options[1][1]);
        option3.setText(options[1][2]);
    }

    public void setQuestion3() {
        relativeLayoutMain.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_slide_in));
        questions.setText(quest[2]);
        option1.setText(options[2][0]);
        option2.setText(options[2][1]);
        option3.setText(options[2][2]);
    }

    public void setQuestion4() {
        relativeLayoutMain.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_slide_in));
        questions.setText(quest[3]);
        option1.setText(options[3][0]);
        option2.setText(options[3][1]);
        option3.setText(options[3][2]);
    }

    public void setQuestion5() {
        relativeLayoutMain.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_slide_in));
        questions.setText(quest[4]);
        option1.setText(options[4][0]);
        option2.setText(options[4][1]);
        option3.setText(options[4][2]);
    }

    public void setQuestion6() {
        relativeLayoutMain.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_slide_in));
        questions.setText(quest[5]);
        option1.setText(options[5][0]);
        option2.setText(options[5][1]);
        option3.setText(options[5][2]);
    }


}

