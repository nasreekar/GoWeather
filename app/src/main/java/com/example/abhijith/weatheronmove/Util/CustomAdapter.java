package com.example.abhijith.weatheronmove.Util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhijith.weatheronmove.MainActivity;
import com.example.abhijith.weatheronmove.Model.WeatherData;
import com.example.abhijith.weatheronmove.R;

import java.util.List;

/**
 * Created by Abhijith on 08-Sep-16.
 */
public class CustomAdapter extends BaseAdapter {

    List<WeatherData> result;
    Context context;
    private static LayoutInflater inflater=null;

    public CustomAdapter(Context context, List<WeatherData> weatherDataList) {
        // TODO Auto-generated constructor stub
        result=weatherDataList;
        this.context=context;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView txtDay;
        ImageView imgIcon;
        TextView temp;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        //final MainActivity m = new MainActivity();
        final View rowView;

        rowView = inflater.inflate(R.layout.list_item, null);
        if (position % 2 == 1) {
            rowView.setBackgroundColor(context.getResources().getColor(R.color.listItemColor));
        } else {
            rowView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.txtDay=(TextView) rowView.findViewById(R.id.list_day);
        holder.imgIcon=(ImageView) rowView.findViewById(R.id.list_imageview);
        holder.temp = (TextView) rowView.findViewById(R.id.list_temp);

        String arrDay = result.get(position).getDay();
        holder.txtDay.setText(new StringBuilder().append(arrDay).append("(").append(result.get(position).getDate()).append(")"));

        String PACKAGE_NAME = context.getPackageName();
        int imgId = context.getResources().getIdentifier("drawable/icon_"+result.get(position).getImageName(),null,PACKAGE_NAME);
        Drawable weatherIcon = ContextCompat.getDrawable(context,imgId);
        holder.imgIcon.setImageDrawable(weatherIcon);

        holder.temp.setText(new StringBuilder().append(result.get(position).getTemp()).append("°C"));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "You Clicked "+result.get(position).getDay(), Toast.LENGTH_LONG).show();
                ((MainActivity)context).setMainWeatherData(result.get(position));
            }
        });
        return rowView;
    }
}

