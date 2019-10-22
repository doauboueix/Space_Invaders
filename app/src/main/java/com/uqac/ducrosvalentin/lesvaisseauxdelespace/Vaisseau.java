package com.uqac.ducrosvalentin.lesvaisseauxdelespace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Vaisseau{

    // The player will be represented by a Bitmap
    private Bitmap bitmap;

    // width and height of our paddle
    private float length;
    private float height;

    // X is the far left of the rectangle which forms our paddle
    private float x;

    // Y is the top coordinate
    private float y;

    // This will hold the pixels per second speed that the paddle will move
    private float speed = 10;

    // Width of the window
    private float screenX;

    // Which ways can the paddle move
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    // Is the ship moving and in which direction
    private int isMoving = STOPPED;

    private int life;

    private  int score;

    // This the the constructor method
    // When we create an object from this class we will pass
    // in the screen width and height
    public Vaisseau(Context context, int screenX, int screenY){

        length = screenX/10;
        height = screenY/10;

        life = 3;

        // Start ship in roughly the screen centre
        x = (screenX / 2)- (length / 2) ;
        y = screenY - 300;

        this.screenX = screenX;

        // Initialize the bitmap
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.vaisseau);

        // stretch the bitmap to a size appropriate for the screen resolution
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (length), (int) (height), false);
    }

    // This is a getter method to make the rectangle that
    // defines our paddle available in BreakoutView class
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

    public int getLife(){
        return life;
    }

    public void setLife(int number){
        life += number;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int gain){
        score += gain;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    // This method will be used to change/set if the paddle is going left, right or nowhere
    public void setMovementState(int state){
        isMoving = state;
    }

    // This update method will be called from update in SpaceInvadersView
    // It determines if the player ship needs to move and changes the coordinates
    // contained in x if necessary
    public void update(){
        if(isMoving == LEFT){
            x -= speed;
            if(x <= 0){
                x = 0;
            }
        }

        if(isMoving == RIGHT){
            x += speed;
            if(x + length >= screenX){
                x = screenX - length;
            }
        }
    }
}
