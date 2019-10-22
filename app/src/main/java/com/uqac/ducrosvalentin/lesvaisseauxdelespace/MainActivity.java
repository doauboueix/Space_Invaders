package com.uqac.ducrosvalentin.lesvaisseauxdelespace;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Database database;
    private static MediaPlayer mediaPlayer;
    private int[] listScore = new int[5];
    private int option;
    private int score;
    private int backFromGame;
    private boolean isReset;

    public void Play(View v) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("option", option);
        mediaPlayer.stop();
        startActivity(intent);
    }

    public void Highscores(View v) {
        Intent intent = new Intent(MainActivity.this, HighscoresActivity.class);
        intent.putExtra("score", listScore);
        startActivity(intent);
    }

    public void Options(View v) {
        Intent intent = new Intent(MainActivity.this, OptionsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        option = intent.getIntExtra("option", 2);
        score = intent.getIntExtra("score", 0);
        backFromGame = intent.getIntExtra("boolean", 0);
        isReset = intent.getBooleanExtra("reset",false);

        if(mediaPlayer == null ||  backFromGame == 1){
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.menu_theme);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

        database = new Database(this, "ANDROID_DATABASE", null, 1);

        if(isReset){
            database.reset();
        }

        listScore = database.getListScore();

        int num = database.getNumberOfRows();
        if (score != 0) {
            if (num < 5) {
                database.insertScore(score);
            } else {
                database.updateScore(score);
            }
            listScore = database.getListScore();
        }
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
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        onDestroy();
    }
}
