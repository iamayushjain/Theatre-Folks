package com.example.hp.dramaapp.Ayush;



import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hp.dramaapp.R;

public class adapterWordsListView extends ArrayAdapter<String>{
private final Activity context;
private final String[] web;
private final String[] web1;
    //private  final float[] web2;
public adapterWordsListView(Activity context,
                                   String[] web, String[] web1) {
super(context, R.layout.adapter_layout_list1, web);
this.context = context;
this.web = web;
this.web1 = web1;
//this.web2 = web2;
}
@Override
public View getView(int position, View view, ViewGroup parent) {

    LayoutInflater inflater = context.getLayoutInflater();
    View rowView= inflater.inflate(R.layout.adapter_layout_list1, parent, false);
    int color_id[]={Color.RED,Color.BLUE,Color.DKGRAY};

    TextView txtTitle = (TextView) rowView.findViewById(R.id.textView1);
    TextView txtTitle1 = (TextView) rowView.findViewById(R.id.textView2);


    txtTitle.setText(web[position]);

    txtTitle1.setText(web1[position]);
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
int randomWithRange(int min, int max)
{
   int range = (max - min) + 1;     
   return (int)(Math.random() * range) + min;
}
}

