package com.uqac.ducrosvalentin.lesvaisseauxdelespace;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;

import java.util.List;

public class GameActivity extends Activity implements SensorEventListener {
    private GameView gameview;

    private static MediaPlayer mediaPlayer;
    private Sensor sensor;
    private SensorManager sensorManager;

    public void lose(){
        Intent intent = new Intent(GameActivity.this, Lose.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void win(int score){
        Intent intent = new Intent(GameActivity.this, Win.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle = new Bundle();
        bundle.putInt("score", score);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.game_theme);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        int option;
        Intent intent = getIntent();
        option = intent.getIntExtra("option",0);

        if(option == 1){
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }

        // Initialize gameView and set it as the view
        gameview = new GameView(this,size.x,size.y, option);
        setContentView(gameview);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.values[0] >= 0) {
            gameview.getVaisseau().setMovementState(gameview.getVaisseau().LEFT);
            gameview.getVaisseau().setSpeed(event.values[0] * 6);
        }
        else{
            gameview.getVaisseau().setMovementState(gameview.getVaisseau().RIGHT);
            gameview.getVaisseau().setSpeed(event.values[0] * -6);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.pause();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                onDestroy();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("boolean",1);
        startActivity(intent);
    }
}