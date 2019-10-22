import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.SyncStateContract;

import java.util.Currency;

public class OrientationControl implements SensorEventListener{

    private SensorManager manager;
    private Sensor accelerometer;
    private Sensor magnometer;

    private float[] accelOutput;
    private float[] magOutput;

    private float[] orientation;
    public float[] getOrientation(){
        return orientation;
    }

    private float[] startOrientation = null;
    public float[] getStartOrientation(){
        return startOrientation;
    }

    public void newGame(){
        startOrientation = null;
    }

    public OrientationControl(Context context){
        manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void register(){
        manager.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this,magnometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public void pause(){
        manager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor,int accuracy){

    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accelOutput = event.values;
        }
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magOutput = event.values;
        }
        if(accelOutput != null && magOutput != null){

        }
    }
}
