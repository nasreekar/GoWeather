package com.example.abhijith.weatheronmove.Util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by Abhijith on 07-Sep-16.
 */
public class LocationTracker implements LocationListener {
    Context context;
    LocationManager locManager;
    public String longitude;
    public String latitude;
    boolean gps_enabled = false;
    boolean network_enabled = false;

    public LocationTracker(Context applicationContext, LocationManager locationMgr) {
        this.context = applicationContext;
        this.locManager = locationMgr;
        getCurrentLocation();
    }

    public void getCurrentLocation() {
        try {
            gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (gps_enabled) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this, null);
        }
        if (network_enabled) {
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this, null);
        }

        Location location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        try {
            longitude = String.valueOf(location.getLongitude());
            latitude = String.valueOf(location.getLatitude());
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        longitude = String.valueOf(location.getLongitude());
        latitude = String.valueOf(location.getLatitude());
        Log.d("msg", "changed Loc : "+longitude + ":"+latitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        //showSettingsAlert();
    }

    /*private void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS Settings");

        // Setting Dialog Message
        alertDialog
                .setMessage("GPS is not enabled. Do you want to go to settings menu?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }*/
}
