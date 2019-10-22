package com.uqac.ducrosvalentin.lesvaisseauxdelespace;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import com.tomer.fadingtextview.FadingTextView;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lose extends Activity {

    private MediaPlayer mediaPlayer;
    FadingTextView text;

    @Override
    public void onBackPressed(){
        backToMenu();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.gameover_theme);
        mediaPlayer.start();

        text = findViewById(R.id.text_animation);
        text.setTimeout(2500, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:
                backToMenu();
        }
        return true;
    }

    private void backToMenu(){
        mediaPlayer.stop();
        Intent intent = new Intent(Lose.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("boolean",1);
        startActivity(intent);
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
        mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }
}
