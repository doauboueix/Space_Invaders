package com.uqac.ducrosvalentin.lesvaisseauxdelespace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class OptionsActivity extends Activity {

    private Button bouton;
    private Boolean isOption1Selected = false;
    private Boolean isOption2Selected = false;
    private ImageView image1;
    private ImageView image2;

    public void selectOption1(View v){
        bouton = findViewById(R.id.button_option);
        if(bouton.getVisibility() == View.INVISIBLE){
            bouton.setVisibility(View.VISIBLE);
        }

        image1 = findViewById(R.id.option1);
        image2 = findViewById(R.id.option2);

        if(isOption2Selected){
            image2.clearColorFilter();
            isOption2Selected = false;
        }
        isOption1Selected = true;
        image1.setColorFilter(ContextCompat.getColor(this, R.color.option_tint), android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    public void selectOption2(View v){
        bouton = findViewById(R.id.button_option);
        if(bouton.getVisibility() == View.INVISIBLE){
            bouton.setVisibility(View.VISIBLE);
        }

        image1 = findViewById(R.id.option1);
        image2 = findViewById(R.id.option2);

        if(isOption1Selected){
            image1.clearColorFilter();
            isOption1Selected = false;
        }
        isOption2Selected = true;
        image2.setColorFilter(ContextCompat.getColor(this, R.color.option_tint), android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    public void valider(View v){
        Intent intent = new Intent(OptionsActivity.this, MainActivity.class);
        if(isOption1Selected){
            intent.putExtra("option",1);
        }
        else{
            intent.putExtra("option",2);
        }
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }
}
