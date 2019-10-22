package com.uqac.ducrosvalentin.lesvaisseauxdelespace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HighscoresActivity extends Activity {

    private int[] listScore = new int[10];
    private TextView textScore;
    private Button reset;
    private static LinearLayout.LayoutParams params;
    private boolean isReset;

    public void reset(View v){
        textScore.setText("No\n\nHighscore\n\nYet");
        reset.setVisibility(View.INVISIBLE);
        isReset = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        textScore = findViewById(R.id.edit_score);
        reset = findViewById(R.id.reset);

        Intent intent = getIntent();
        listScore = intent.getIntArrayExtra("score");

        int i = 0;
        String text = "";

        while(listScore[i] != 0){
            System.out.println(listScore[i]);
            text +=  i+1 + ".   " + listScore[i] + "\n\n";
            i++;
        }
        if(text == ""){
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0,0,0);
            params.gravity = Gravity.CENTER;
            textScore.setLayoutParams(params);
            textScore.setTextSize(40);
            textScore.setText("No\n\nHighscore\n\nYet" );
        }
        else{
            textScore.setText(text);
        }

        if(i != 0){
            reset.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(HighscoresActivity.this, MainActivity.class);
        intent.putExtra("reset", isReset);
        startActivity(intent);
    }
}
