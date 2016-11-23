package com.example.hp.dramaapp.Ayush;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.hp.dramaapp.R;

public class ImageCustomPlays extends ArrayAdapter<String> {
    private final Activity context;
    private final Integer[] web;
    private final String[] web1;

    //private  final float[] web2;
    public ImageCustomPlays(Activity context,
                            Integer[] web, String[] web1) {
        super(context, R.layout.imagecustomlistplays, web1);
        this.context = context;
        this.web = web;
        this.web1 = web1;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.imagecustomlistplays, parent, false);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
        imageView.setImageResource(web[position]);
//    float valueProb=Float.parseFloat(web1[position]);
//  if(position>2) {
//      if (valueProb > -300) {
//          txtTitle.setTextColor(color_id[2]);
//          txtTitle1.setTextColor(color_id[2]);
//      } else if (valueProb > -400) {
//          txtTitle.setTextColor(color_id[1]);
//          txtTitle1.setTextColor(color_id[1]);
//      } else {
//          txtTitle.setTextColor(color_id[0]);
//          txtTitle1.setTextColor(color_id[0]);
//      }
//  }
        return rowView;
    }

    int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
}

