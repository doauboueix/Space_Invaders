package com.uqac.ducrosvalentin.lesvaisseauxdelespace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bullet {

    private float x;
    private float y;

    // width and height of our bullet
    private float length;
    private float height;


    // Which way is it shooting
    public int UP = 0;
    public int DOWN = 1;

    // Going nowhere
    int direction;
    float speed = 20;

    private boolean isActive;

    private Bitmap bitmap;

    private Bitmap bitmap2;

    private int screenY;


    public Bullet(Context context, int x, int y, float startX, float startY, int direction) {

        this.x = startX;
        this.y = startY;
        this.direction = direction;

        isActive = true;

        screenY = y;

        length = x / 50;
        height = y / 20;

        // Initialize the bitmap
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.missile);
        bitmap2 = BitmapFactory.decodeResource(context.getResources(),R.drawable.missile2);

        // stretch the bitmap to a size appropriate for the screen resolution
        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (length),
                (int) (height),
                false);

        bitmap2 = Bitmap.createScaledBitmap(bitmap2,
                (int) (length),
                (int) (height),
                false);
    }

    public void update() {
        if (direction == UP) {
            y -= speed;
            if (y <= 0) {
                isActive = false;
            }
        } else {
            y += speed;
            if (y >= screenY) {
                isActive = false;
            }
        }
    }

    public float getLength(){
        return length;
    }

    public float getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean getStatus() {
        return isActive;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Bitmap getBitmap2() {
        return bitmap2;
    }

    public void setInactive() {
        isActive = false;
    }
}
