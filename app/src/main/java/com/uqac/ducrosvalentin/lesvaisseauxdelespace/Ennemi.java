package com.uqac.ducrosvalentin.lesvaisseauxdelespace;

import android.graphics.Bitmap;
import java.util.Random;

public class Ennemi {

    // The player ship will be represented by a Bitmap
    private Bitmap bitmap;

    // How long and high our paddle will be
    private float length;
    private float height;

    // X is the far left of the rectangle which forms our paddle
    private float x;

    // Y is the top coordinate
    private float y;

    // This will hold the pixels per second speed that the paddle will move
    private float speed = 10;

    public final int LEFT = 0;
    public final int RIGHT = 1;

    // Is the ship moving and in which direction
    private int direction;

    private int screenX;

    private boolean isVisible;

    private int scoreEarned;

    private int life;

    private double shoot_delay;
    private double time;

    public Ennemi(float posX, float posY,int screenX,int screenY, int diviseurX, int diviseurY, int life, int score) {
        direction = new Random().nextInt(2);
        isVisible = true;
        time = System.currentTimeMillis();
        x = posX;
        y = posY;
        this.screenX = screenX;
        this.life = life;
        scoreEarned = score;

        length = screenX / diviseurX;
        height = screenY / diviseurY;

    }

    public void setInvisible() {
        isVisible = false;
    }

    public boolean getVisibility() {
        return isVisible;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getHeight() {
        return height;
    }

    public float getLength() {
        return length;
    }

    public void decrementLife(){
        life--;
        if(life == 0){
            isVisible = false;
        }
    }


    public int getScoreEarned(){
        return scoreEarned;
    }

    public void update() {
        if (direction == LEFT) {
            x -= speed;
        }

        if (direction == RIGHT) {
            x += speed;
        }

        if (x <= 0 || x >= screenX - length) {
            dropDownAndReverse();
        }
    }

    public void dropDownAndReverse() {
        if (direction == LEFT) {
            direction = RIGHT;
        } else {
            direction = LEFT;
        }
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getDelay() {
        return shoot_delay;
    }

    public void setShoot_delay(double delay){
        shoot_delay = delay;
    }
}