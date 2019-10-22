package com.uqac.ducrosvalentin.lesvaisseauxdelespace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.support.v4.content.res.ResourcesCompat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private Context context;

    private GameActivity gameActivity;

    // This is our thread
    private MainThread thread;

    // The size of the screen in pixels
    private int screenX;
    private int screenY;

    // The player
    private Vaisseau vaisseau;

    private Bitmap bitmapLife1;
    private Bitmap bitmapLife2;
    private Bitmap bitmapLife3;

    private final MediaPlayer kill;
    private MediaPlayer lose_life;
    private MediaPlayer shoot_ship;
    private MediaPlayer shoot_ennemi;
    private MediaPlayer brick_hit;
    private MediaPlayer bonus_pick;

    private Bitmap background;

    private Level level;

    private int option;

    private Paint paint;
    private Paint paint2;

    private int SHOOT_DELAY = 750;

    private  long time_spawn_bullet = System.currentTimeMillis();
    private long time_bonus = System.currentTimeMillis();

    private ArrayList<Bullet> listBullet = new ArrayList<>();

    private ArrayList<Bullet> listBulletEnnemi = new ArrayList<>();

    private ArrayList<Wall> listWall = new ArrayList<>();

    private ArrayList<Bonus> listBonus = new ArrayList<>();

    public GameView(Context context, int x, int y, int option) {
        super(context);

        screenX = x;
        screenY = y;

        this.context = context;
        this.option = option;

        // Initializing the bitmaps
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background_game);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);

        bitmapLife1 = BitmapFactory.decodeResource(getResources(), R.drawable.hp1);
        bitmapLife2 = BitmapFactory.decodeResource(getResources(), R.drawable.hp2);
        bitmapLife3 = BitmapFactory.decodeResource(getResources(), R.drawable.hp3);

        kill = MediaPlayer.create(context,R.raw.explosion);
        lose_life = MediaPlayer.create(context,R.raw.lose_life);
        shoot_ship = MediaPlayer.create(context,R.raw.shoot1);
        shoot_ennemi = MediaPlayer.create(context,R.raw.shoot2);
        brick_hit = MediaPlayer.create(context,R.raw.brick_hit);
        bonus_pick = MediaPlayer.create(context,R.raw.bonus);

        listWall.add(new Wall(context,screenX,screenY,screenX/10));
        listWall.add(new Wall(context,screenX,screenY,screenX - screenX/10 - screenX/4));

        getHolder().addCallback(this);

        thread = new MainThread(this, getHolder());

        gameActivity = (GameActivity) context;

        setFocusable(true);

        vaisseau = new Vaisseau(getContext(), x, y);

        level = new Level(screenX, screenY);
        level.setLevel(context, level.getNumLevel());

        paint = new Paint();
        paint.setColorFilter(new LightingColorFilter(Color.RED,0));

        paint2 = new Paint();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(this, getHolder());
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(option == 2) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {

                // Player has touched the screen
                case MotionEvent.ACTION_DOWN:

                    if (event.getX() > screenX / 2) {
                        vaisseau.setMovementState(vaisseau.RIGHT);
                    } else {
                        vaisseau.setMovementState(vaisseau.LEFT);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    vaisseau.setMovementState(vaisseau.STOPPED);
                    break;
            }
            return true;
        }
        return false;
    }

    public void update() {

        vaisseau.update();

        if(System.currentTimeMillis() >= time_bonus + 4000){
            listBonus.add(new Bonus(context,screenX,screenY));
            time_bonus = System.currentTimeMillis();
        }

        if(System.currentTimeMillis() >= time_spawn_bullet + SHOOT_DELAY){
            listBullet.add(new Bullet(context, screenX, screenY, vaisseau.getX() + vaisseau.getLength() / 3, vaisseau.getY() - 100, 0));
            shoot_ship.start();
            time_spawn_bullet = System.currentTimeMillis();
        }

        for(Bonus bonus : listBonus){
            if(bonus.isVisible()){
                bonus.update();
                if((bonus.getX() <= vaisseau.getX() && bonus.getX() + bonus.getLength() >= vaisseau.getX() || bonus.getX() >= vaisseau.getX() && bonus.getX() <= vaisseau.getX() + vaisseau.getLength()) && bonus.getY() + bonus.getHeight() >= vaisseau.getY()){
                    triggerEffect(bonus);
                    bonus_pick.start();
                    listBonus.remove(bonus);
                }
            }
        }

        for(Ennemi ennemi : level.getListEnnemis()) {
            if (ennemi.getVisibility()) {
                // Move the next invader
                ennemi.update();
                if(System.currentTimeMillis() >= ennemi.getTime() + ennemi.getDelay()){
                    listBulletEnnemi.add(new Bullet(context, screenX, screenY, ennemi.getX() + ennemi.getLength()/2, ennemi.getY() + 100, 1));
                    shoot_ennemi.start();
                    ennemi.setTime(System.currentTimeMillis());
                }
            }
        }

        // Ship's bullets
        for(Bullet b : listBullet){
            if (b.getStatus()) {
                b.update();
                for(Wall wall : listWall){
                    if(wall.isVisible()){
                        if(b.getX() >= wall.getX() && b.getX() <= wall.getX() + wall.getLength() && b.getY()  <= wall.getY()){
                            b.setInactive();
                        }
                    }
                }
                for (Ennemi ennemi : level.getListEnnemis()) {
                    if (ennemi.getVisibility()) {
                        if (b.getX() >= ennemi.getX() && b.getX() <= ennemi.getX() + ennemi.getLength() && b.getY() >= ennemi.getY() && b.getY() <= ennemi.getY() + ennemi.getBitmap().getHeight()) {
                            ennemi.decrementLife();
                            b.setInactive();
                            vaisseau.setScore(ennemi.getScoreEarned());
                            kill.start();
                        }
                    }
                }
            }
        }

        // Ennemis's bullets
        for(Bullet b : listBulletEnnemi){
            if(b.getStatus()){
                b.update();
                for(Wall wall : listWall){
                    if(wall.isVisible()){
                        if(b.getX() >= wall.getX() && b.getX() <= wall.getX() + wall.getLength() && b.getY() + b.getHeight() >= wall.getY()){
                            b.setInactive();
                            wall.decrementLife();
                            if(!wall.isVisible()){
                                brick_hit.start();
                            }
                        }
                    }
                }
                if (b.getX() >= vaisseau.getX() && b.getX() <= vaisseau.getX() + vaisseau.getLength() && b.getY() + b.getHeight() >= vaisseau.getY() && b.getY() + b.getHeight() <= vaisseau.getY() + vaisseau.getHeight()) {
                    b.setInactive();
                    vaisseau.setLife(-1);
                    lose_life.start();
                    if (vaisseau.getLife() == 0) {
                        gameActivity.lose();
                    }
                }
            }
        }

        /*
        if (level.isLevelCleared()) {
            new CountDownTimer(2000, 1000) {
                public void onFinish() {
                    level.setNumLevel();
                    if (!level.setLevel(getContext(), level.getNumLevel())) {
                        gameActivity.win(vaisseau.getScore());
                    }
                }

                public void onTick(long millisUntilFinished) {
                    // millisUntilFinished    The amount of time until finished.
                }
            }.start();
        }*/

        if (level.isLevelCleared()) {
            level.setNumLevel();
            if (!level.setLevel(getContext(), level.getNumLevel())) {
                gameActivity.win(vaisseau.getScore());
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Draw the background
        canvas.drawBitmap(background, 0, 0,null);

        // Draw the player's ship
        canvas.drawBitmap(vaisseau.getBitmap(), vaisseau.getX(), vaisseau.getY(), null);

        // Draw the ennemis
        for (Ennemi ennemi : level.getListEnnemis()) {
            if (ennemi.getVisibility()) {
                canvas.drawBitmap(ennemi.getBitmap(), ennemi.getX(), ennemi.getY(), null);
            }
        }

        for(Bullet b : listBullet){
            if(b.getStatus()) {
                canvas.drawBitmap(b.getBitmap2(), b.getX(), b.getY(), null);
            }
        }

        for(Bullet b : listBulletEnnemi){
            if(b.getStatus()) {
                canvas.drawBitmap(b.getBitmap(), b.getX(), b.getY(), null);
            }
        }

        for(Wall wall : listWall){
            if(wall.isVisible()){
                if(wall.getLife() >= 5){
                    canvas.drawBitmap(wall.getBitmap(),wall.getX(),wall.getY(),null);
                }
                else {
                    canvas.drawBitmap(wall.getBitmap(),wall.getX(),wall.getY(),null);
                }
            }
        }

        for(Bonus bonus : listBonus){
            if(bonus.isVisible()){
                canvas.drawBitmap(bonus.getBitmap(), bonus.getX(), bonus.getY(), null);
            }
        }

        // Draw the player's score
        paint2.setColor(Color.WHITE);
        paint2.setTextSize(60);
        paint2.setTypeface(ResourcesCompat.getFont(context, R.font.arcade_font));

        canvas.drawText("SCORE : " + vaisseau.getScore(), 20, 60, paint2);
        canvas.drawText("LIFE : ", 650, 60, paint2);

        // Draw the player's life
        switch (vaisseau.getLife()) {

            case 3:
                canvas.drawBitmap(bitmapLife3, 800, -10, null);
                break;

            case 2:
                canvas.drawBitmap(bitmapLife2, 800, -10, null);
                break;

            case 1:
                canvas.drawBitmap(bitmapLife1, 800, -10, null);
                break;
        }

        // Draw the animation at end of levels

    }

    public Vaisseau getVaisseau(){
        return vaisseau;
    }

    public void triggerEffect(Bonus bonus){
        switch (bonus.getType()){
            case "powerup" :
                SHOOT_DELAY -= 100;
                break;

            case "life" :
                if(vaisseau.getLife() != 3){
                    vaisseau.setLife(1);
                }
                break;

            case "bomb" :
                int random = new Random().nextInt(level.getListEnnemis().size());
                while(!level.getListEnnemis().get(random).getVisibility()){
                    random = new Random().nextInt(level.getListEnnemis().size());
                    if( level.getListEnnemis().get(random).getVisibility()){
                        level.getListEnnemis().get(random).setInvisible();
                        vaisseau.setScore(level.getListEnnemis().get(random).getScoreEarned());
                        break;
                    }
                }
                kill.start();
                break;

            case "wall" :
                for(Wall wall : listWall){
                    wall.regenerate();
                }
                break;
        }
    }
}
