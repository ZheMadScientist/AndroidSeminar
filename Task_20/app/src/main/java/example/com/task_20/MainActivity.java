package example.com.task_20;

import android.annotation.SuppressLint;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView latitude, longitude, gpsStatus;
    private LocationManager locationManager;

    String gpsON = "GPS is currently enabled";
    String gpsOFF = "GPS is currently disabled";

    String latitudeS = "Latitude: ";
    String longitudeS = "Longitude: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        gpsStatus = findViewById(R.id.gps_status);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);

        checkEnabled();
    }

    @SuppressLint("MissingPermission")
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }
    };

    private void showLocation(Location location) {
        if (location == null)
            return;

        String lat = latitudeS + String.valueOf(location.getLatitude());
        String lon = longitudeS + String.valueOf(location.getLongitude());

        latitude.setText(lat);
        longitude.setText(lon);
    }

    private void checkEnabled(){
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            gpsStatus.setText(gpsON);
        else gpsStatus.setText(gpsOFF);
    }

}
