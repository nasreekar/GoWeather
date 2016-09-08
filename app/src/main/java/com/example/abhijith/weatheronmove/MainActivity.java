package com.example.abhijith.weatheronmove;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.abhijith.weatheronmove.Model.WeatherData;
import com.example.abhijith.weatheronmove.Util.CheckConnection;
import com.example.abhijith.weatheronmove.Util.CustomAdapter;
import com.example.abhijith.weatheronmove.Util.JSONParser;
import com.example.abhijith.weatheronmove.Util.LocationTracker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static TextView coord, place, temp, description, humidity,home_date,home_day;
    ListView listView;
    static ImageView imgView;
    protected LocationManager locationManager;
    public static String apiUrl;
    CheckConnection cc;
    WeatherData wd;
    List<WeatherData> wdList;

    public static String PACKAGE_NAME;
    Drawable d;
    int resourceId;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coord = (TextView) findViewById(R.id.coord);
        place = (TextView) findViewById(R.id.place);
        temp = (TextView) findViewById(R.id.temp);
        imgView = (ImageView) findViewById(R.id.imageView);
        description = (TextView) findViewById(R.id.description);
        humidity = (TextView) findViewById(R.id.humidity);
        listView = (ListView) findViewById(R.id.weatherListView);
        home_date = (TextView)findViewById(R.id.homeDate);
        home_day = (TextView)findViewById(R.id.homeDay);

        PACKAGE_NAME = getApplicationContext().getPackageName();

       /* final View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item, null);
        final TextView txtDay = (TextView) v.findViewById(R.id.list_day);
        final ImageView imgIcon = (ImageView) v.findViewById(R.id.list_imageview);
        final TextView temp = (TextView) v.findViewById(R.id.list_temp);
        ViewGroup viewGroup= ( ViewGroup)listView.getParent();
        viewGroup.addView(v);*/

        cc = new CheckConnection(getApplicationContext());

        if (cc.isNetworkAvailable()) {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            LocationTracker track = new LocationTracker(getApplicationContext(), locationManager);
            if (track.longitude != null && track.latitude != null) {
                String longitude = track.longitude;
                String latitude = track.latitude;

                Log.i("Latitude", latitude);
                Log.i("Longitude", longitude);

                //setting the coordinates text views
                coord.setText(new StringBuilder().append("(").append(latitude).append(",").append(longitude).append(")").toString());

                //url for daily temp
                //apiUrl = String.format("http://api.openweathermap.org/data/2.5/weather?APPID=8dcac8ea033688291b30b5a55dc967d4&lat=%s&lon=%s", latitude, longitude);

                //url for week forecast
                apiUrl = String.format("http://api.openweathermap.org/data/2.5/forecast/daily?APPID=8dcac8ea033688291b30b5a55dc967d4&lat=%s&lon=%s",latitude,longitude);
                Log.i("API CALL URL", apiUrl);

                //JsonData data = new JsonData();
                //data.getInfo(apiUrl);

                wdList = new ArrayList<>();
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        JSONObject allDataForecast = JSONParser.getJSONFromUrl(apiUrl);
                        try {
                            JSONObject cityData = allDataForecast.getJSONObject("city");
                            String cityName = (String) cityData.get("name");
                            String conName = (String)cityData.get("country");
                            int count = (int) allDataForecast.get("cnt");
                            JSONArray arrWeatherList = allDataForecast.getJSONArray("list");
                            for (int i = 0; i < count; i++) {
                                String combineDate = String.valueOf(arrWeatherList.getJSONObject(i).get("dt").toString());
                                String day = getDay(combineDate)[1];
                                String date = getDay(combineDate)[0];
                                JSONObject tempObject = arrWeatherList.getJSONObject(i).getJSONObject("temp");
                                String placeName = cityName;
                                String countryName = conName;
                                String temperature = changeTemp(tempObject.get("day").toString());
                                String humidityCount = arrWeatherList.getJSONObject(i).get("humidity").toString();
                                JSONObject objectWeather = arrWeatherList.getJSONObject(i).getJSONArray("weather").getJSONObject(0);
                                String description = objectWeather.get("description").toString();
                                String iconId = objectWeather.get("icon").toString();
                                wd = new WeatherData(temperature, humidityCount, description, iconId, placeName,countryName, day,date);
                                wdList.add(wd);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        int id = 0;
                        String d = getTodayDate();

                        for(int i=0;i<wdList.size();i++){
                            WeatherData list_wd = wdList.get(i);
                            if(d.equals(list_wd.getDate())){
                                id=i;
                            }
                        }
                        WeatherData w = wdList.get(id);
                        setMainWeatherData(w);

                       /* for (int i = 0; i < wdList.size(); i++) {
                            WeatherData list_wd = wdList.get(i);
                            String arrDay = list_wd.getDay();
                            txtDay.setText(arrDay);
                            temp.setText(new StringBuilder().append(list_wd.getTemp()).append(" °C"));
                            imgIcon.setImageResource(R.drawable.clearsky);

                            *//*String imgName = String.format("art_%s", w.getImageName().substring(0, 2));
                            String PACKAGE_NAME = getApplicationContext().getPackageName();
                            int imgId = getResources().getIdentifier(PACKAGE_NAME + ":drawable/" + imgName, null, null);
                            imgIcon.setImageBitmap(BitmapFactory.decodeResource(getResources(), imgId));*//*
                        }*/

                        listView.setAdapter(new CustomAdapter(MainActivity.this, wdList));
                    }
                }.execute();

                //listView.setAdapter(new CustomAdapter(this, wdList));

            } else {
                callDialog("GPS is not enabled.");
            }
        } else {
            callDialog("No Internet Connection");
        }
    }
    //Utils Functions
    private void callDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private String[] getDay(String x) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat formatter2 = new SimpleDateFormat("E");
        long seconds = Long.parseLong(x);
        long milliSeconds = 1000 * seconds;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        String date = formatter.format(calendar.getTime());
        String day = formatter2.format(calendar.getTime());
        String[] dateNDay = {date, day};
        return dateNDay;
    }

    private String changeTemp(String x) {
        Double celsius = Double.parseDouble(x) - 273.0;
        Integer i = celsius.intValue();
        return String.valueOf(i);
    }

    private String getTodayDate(){
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String todayDate = formatter.format(date);
        return todayDate;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setMainWeatherData(WeatherData w) {
        temp.setText(w.getTemp()+"°C");
        place.setText(new StringBuilder().append(w.getName()).append(",").append(w.getCountryName()).toString());
        description.setText(w.getCloudStatus());
        humidity.setText(new StringBuilder().append("Humidity-").append(w.getHumidity()).toString());
        home_day.setText(w.getDay());
        home_date.setText(w.getDate());
        d=getIconDrawable(w.getImageName());
        imgView.setImageDrawable(d);
    }

    public Drawable getIconDrawable(String icId){
        resourceId =getResources().getIdentifier("drawable/icon_"+icId,null,PACKAGE_NAME);
        Drawable weatherIcon = ContextCompat.getDrawable(this,resourceId);
        return weatherIcon;
    }
}


