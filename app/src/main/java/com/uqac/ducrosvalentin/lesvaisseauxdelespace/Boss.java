package com.uqac.ducrosvalentin.lesvaisseauxdelespace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Boss extends Ennemi{

    private static final int DELAY = 250;

    public Boss(Context context, float posX, float posY, int screenX, int screenY, int life, int score){
        super(posX,posY,screenX,screenY,3,5,life,score);
        super.setShoot_delay(DELAY);
        super.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.boss));

        // stretch the first bitmap to a size appropriate for the screen resolution
        super.setBitmap(Bitmap.createScaledBitmap(getBitmap(),
                (int) (getLength()),
                (int) (getHeight()),
                false));
    }
}
