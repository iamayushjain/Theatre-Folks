package com.example.hp.dramaapp.Ayush;


import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hp.dramaapp.R;

public class adapterWordsListViewColor extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] web;
    private final String[] web1;
    private final String[] web2;
    private final String[] web3;

    //private  final float[] web2;
    public adapterWordsListViewColor(Activity context,
                                     String[] web, String[] web1,String[] web2,String[] web3) {
        super(context, R.layout.adapter_layout, web);
        this.context = context;
        this.web = web;
        this.web1 = web1;
        this.web2 = web2;
        this.web3 = web3;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adapter_layout, parent, false);
        int color_id[] = {Color.RED, Color.BLUE, Color.DKGRAY};

        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView1);
        TextView txtTitle1 = (TextView) rowView.findViewById(R.id.textView2);
        TextView txtTitle2 = (TextView) rowView.findViewById(R.id.textView3);
        //TextView txtTitle3 = (TextView) rowView.findViewById(R.id.textView4);


        txtTitle.setText(web[position]);

        txtTitle1.setText(web1[position]);
        txtTitle2.setText(web3[position]+"     "+web2[position]);

       // txtTitle3.setText(web3[position]);

        try {
            float valueProb = Float.parseFloat(web1[position]);

            //  if (position > 2) {

            if (valueProb < 100) {
                //txtTitle.setTE
                txtTitle.setTextColor((Color.HSVToColor(new float[]{(float) (valueProb / 100) * 120f, 1f, 1f})));
                //txtTitle1.setTextColor(color_id[2]);
            } else {
                txtTitle.setTextColor(Color.HSVToColor(new float[]{(float) 120f, 1f, 1f}));
            }
        } catch (Exception e) {

        }

        return rowView;
    }

    int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
}

