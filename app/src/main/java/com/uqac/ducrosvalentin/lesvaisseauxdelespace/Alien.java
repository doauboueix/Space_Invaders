package com.uqac.ducrosvalentin.lesvaisseauxdelespace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.Random;

public class Alien extends Ennemi {

    public Alien(Context context, float posX, float posY, int screenX, int screenY, int life, int score){
        super(posX,posY,screenX,screenY,6,12,life,score);
        Random r=new Random();
        super.setShoot_delay((r.nextDouble()*1000)+750);

        int random = new Random().nextInt(3);

        switch (random){
            case 0 :
                super.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.alien1));
                break;

            case 1 :
                super.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.alien2));
                break;

            case 2 :
                super.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.alien3));
                break;
        }

        // stretch the first bitmap to a size appropriate for the screen resolution
        super.setBitmap(Bitmap.createScaledBitmap(getBitmap(),
                (int) (getLength()),
                (int) (getHeight()),
                false));
    }
}
