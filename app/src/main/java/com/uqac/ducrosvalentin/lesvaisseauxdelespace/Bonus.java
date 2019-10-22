package com.uqac.ducrosvalentin.lesvaisseauxdelespace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.Random;

public class Bonus {
    private Bitmap bitmap;
    private float x;
    private float y;
    private float length;
    private float height;
    private int speed = 10;
    private int screenY;
    private boolean isVisible;
    private String type;

    public Bonus(Context context, int screenX, int screenY){
        length = screenX/15;
        height = screenY/18;

        this.screenY = screenY;
        isVisible = true;

        x = new Random().nextFloat() * (screenX - length);
        y = 0;

        int random = new Random().nextInt(4);

        switch (random){
            case 0 :
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.powerup);
                type = "powerup";
                break;

            case 1 :
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.life);
                type = "life";
                break;

            case 2 :
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb);
                type = "bomb";
                break;

            case 3 :
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.charpentier);
                type = "wall";
                break;
        }


        // stretch the bitmap to a size appropriate for the screen resolution
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) length, (int) height, false);
    }

    public void update(){
        if(y <= screenY){
            y += speed;
        }
        else{
            isVisible = false;
        }
    }

    public boolean isVisible(){
        return isVisible;
    }

    public void setInvisible(){
        isVisible = false;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getLength(){
        return length;
    }

    public float getHeight(){
        return height;
    }

    public String getType(){
        return type;
    }
}
