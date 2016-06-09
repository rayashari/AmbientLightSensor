package com.rayasha.ambientlightsensorapp;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity  implements  SensorEventListener{
    ProgressBar lightMeter;
    TextView tvMaxValue, tvReader;
    SensorManager sensorManager;
    Sensor lightSensor;
    TextView name,type,maxRange,minDelay,power,resolution,vendor,version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightMeter = (ProgressBar) findViewById(R.id.lightmeter);
        tvMaxValue = (TextView)findViewById(R.id.max);
        tvReader = (TextView)findViewById(R.id.reading);
        //implement sensor manager

        name =(TextView)findViewById(R.id.sensorName);
        type = (TextView)findViewById(R.id.type);
        maxRange =(TextView)findViewById(R.id.maxRange);
        minDelay =(TextView)findViewById(R.id.minDelay);
        power =(TextView)findViewById(R.id.power);
        resolution =(TextView)findViewById(R.id.resolution);
        vendor =(TextView)findViewById(R.id.vendor);
        version =(TextView)findViewById(R.id.version);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        name.setText(String.valueOf(lightSensor.getName()));
        type.setText(String.valueOf(lightSensor.getType()));
        maxRange.setText(String.valueOf(lightSensor.getMaximumRange()));
        minDelay.setText(String.valueOf(lightSensor.getMinDelay()));
        power.setText(String.valueOf(lightSensor.getPower()));
        resolution.setText(String.valueOf(lightSensor.getResolution()));
        vendor.setText(String.valueOf(lightSensor.getVendor()));
        version.setText(String.valueOf(lightSensor.getVersion()));



        if(lightSensor == null){
            Toast.makeText(getApplicationContext(),"No Sensor",Toast.LENGTH_SHORT).show();
        }else{
            float max = lightSensor.getMaximumRange();
            lightMeter.setMax((int)max);
            tvMaxValue.setText("Max Value: "+String.valueOf(max));
//            sensorManager.registerListener(lightSensorEventListener,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType()==Sensor.TYPE_LIGHT){
                float currentReading = event.values[0];
                lightMeter.setProgress((int)currentReading);
                tvReader.setText("Current Value: "+String.valueOf(currentReading)+" lux");
            }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

//    SensorEventListener lightSensorEventListener = new SensorEventListener() {
//        @Override
//        public void onSensorChanged(SensorEvent event) {
//            if(event.sensor.getType()==Sensor.TYPE_LIGHT){
//                float currentReading = event.values[0];
//                lightMeter.setProgress((int)currentReading);
//                tvReader.setText("Current Value: "+String.valueOf(currentReading)+" lux");
//            }
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//        }
//    };

}
