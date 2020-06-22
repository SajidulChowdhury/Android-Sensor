package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView myText;
    private double previousMagnitude = 0;
    private double previousZ = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myText = findViewById(R.id.myText);
        myText.setText("StandingD");
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener positionDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent != null) {
                    float x_acceleration = sensorEvent.values[0];
                    float y_acceleration = sensorEvent.values[1];
                    float z_acceleration = sensorEvent.values[2];
                    double currentZ = z_acceleration;
                    double zDelta = currentZ - previousZ;
                    previousZ = currentZ;
                    double magnitude = Math.sqrt(x_acceleration * x_acceleration + y_acceleration * y_acceleration + z_acceleration * z_acceleration);
                    double magnitudeDelta = magnitude - previousMagnitude;
                    previousMagnitude = magnitude;


                    if (zDelta < 0) {
                        if (z_acceleration == 0) {
                            myText.setText("Sitting");
                        }
                    }


                    if (magnitudeDelta > 0 && magnitudeDelta < 2) {
                        myText.setText("Standing");
                    }
                    if (magnitudeDelta >= 2 && magnitudeDelta < 5) {
                        myText.setText("Walking");
                    }
                    if (magnitudeDelta >= 5) {
                        myText.setText("Running");
                    }


                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(positionDetector, sensor, sensorManager.SENSOR_DELAY_NORMAL);
    }
}