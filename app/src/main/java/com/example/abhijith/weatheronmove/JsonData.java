package com.example.abhijith.weatheronmove;

import android.os.AsyncTask;
import android.util.Log;

import com.example.abhijith.weatheronmove.Util.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Abhijith on 07-Sep-16.
 * Can be used if forecast is not required and only current data is required
 */
public class JsonData {
    String placeName,tempStr,countryName,imgDesc,iconId,humidity;

    protected void getInfo(final String url) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                JSONObject jsonObject = JSONParser.getJSONFromUrl(url);
                try {
                    JSONObject weatherData = new JSONObject(jsonObject.getString("main"));

                    String temp = weatherData.getString("temp").toString();
                    tempStr = changeTemp(temp);

                    humidity = weatherData.getString("humidity").toString();

                    placeName = jsonObject.getString("name");
                    Log.i("Place", placeName);

                    JSONObject sysData = new JSONObject(jsonObject.getString("sys"));
                    countryName = sysData.getString("country");

                    //JSONObject imgData = new JSONObject(jsonObject.getString("weather"));
                    JSONArray imgData = jsonObject.getJSONArray("weather");
                    for (int i = 0; i < imgData.length(); i++) {  // **line 2**
                        JSONObject childJSONObject = imgData.getJSONObject(i);
                        imgDesc = childJSONObject.getString("description");
                        iconId = childJSONObject.getString("icon");
                    }
                    Log.i("Image Desc", imgDesc);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                MainActivity.temp.setText(tempStr+"°C");
                MainActivity.place.setText(new StringBuilder().append(placeName).append(",").append(countryName).toString());
                MainActivity.description.setText(imgDesc);
                MainActivity.humidity.setText(new StringBuilder().append("Humidity-").append(humidity).toString());

            }
        }.execute();
    }

    private String changeTemp(String temp) {
        Double celsius = Double.parseDouble(temp) - 273.0;
        Integer i = celsius.intValue();
        return String.valueOf(i);
    }

}
