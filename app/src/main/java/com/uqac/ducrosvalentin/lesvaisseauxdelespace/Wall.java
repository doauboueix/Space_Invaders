package com.uqac.ducrosvalentin.lesvaisseauxdelespace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Wall {
    private Bitmap bitmap;
    private Bitmap bitmap2;
    private int life = 10;
    private float x;
    private float y;
    private float length;
    private float height;
    private boolean isVisible;

    public Wall(Context context,int screenX, int screenY,int x){
        length = screenX/4;
        height = screenY/15;

        // Start ship in roughly the screen centre
        this.x = x ;
        y = screenY - 600;

        isVisible = true;

        // Initialize Bitmaps
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.wall);
        bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.wall2);

        // stretch the bitmap to a size appropriate for the screen resolution
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) length, (int) height, false);
        bitmap2 = Bitmap.createScaledBitmap(bitmap2, (int) length, (int) height, false);
    }

    public Bitmap getBitmap(){
        if(life >= 10){
            return bitmap;
        }
        else{
            return bitmap2;
        }
    }

    public float getX(){
        return x;
    }

    public  float getY(){
        return y;
    }

    public float getLength(){
        return length;
    }

    public int getLife(){
        return  life;
    }

    public void decrementLife(){
        if(life > 0){
            life --;
        }
        else{
            isVisible = false;
        }

    }

    public boolean isVisible(){
        return isVisible;
    }

    public void regenerate(){
        life = 20;
        isVisible = true;
    }
}
