package com.uqac.ducrosvalentin.lesvaisseauxdelespace;

import android.content.Context;

import java.util.ArrayList;

public class Level {

    private ArrayList<Ennemi> listEnnemi = new ArrayList<>();
    private int numLevel;
    private int numberEnnemi;
    private int numTmp = 0;
    private int screenX;
    private int screenY;

    public Level(int screenX, int screenY){
        numLevel = 1;
        this.screenX = screenX;
        this.screenY = screenY;
    }

    public boolean setLevel(Context context, int level){
        int posX = 150;
        int posY = 150;
        switch (level){
            case 1 :
                for (int column = 0; column < 4; column++) {
                    for (int row = 0; row < 3; row++) {
                        listEnnemi.add(new Alien(context, posX, posY, screenX, screenY,1,10));
                        posY += 150;
                    }
                    posY = 150;
                    posX += 200;
                }
                numberEnnemi = 12;
                return true;

            case 2:
                for (int column = 0; column < 4; column++) {
                    for (int row = 0; row < 3; row++) {
                        listEnnemi.add(new Alien(context, posX, posY, screenX, screenY,1,20));
                        numberEnnemi += 1;
                        posY += 150;
                    }
                    posY = 150;
                    posX += 200;
                }
                numberEnnemi = 12;
                return true;

            case 3 :
                for (int column = 0; column < 4; column++) {
                    for (int row = 0; row < 3; row++) {
                        listEnnemi.add(new Alien(context, posX, posY, screenX, screenY,1,30));
                        numberEnnemi += 1;
                        posY += 150;
                    }
                    posY = 150;
                    posX += 200;
                }
                numberEnnemi = 12;
                return true;

            case 4 :
                for (int column = 0; column < 4; column++) {
                    for (int row = 0; row < 3; row++) {
                        listEnnemi.add(new Alien(context, posX, posY, screenX, screenY,1,30));
                        numberEnnemi += 1;
                        posY += 150;
                    }
                    posY = 150;
                    posX += 200;
                }
                numberEnnemi = 12;
                return true;

            case 5 :
                for (int column = 0; column < 4; column++) {
                    for (int row = 0; row < 3; row++) {
                        listEnnemi.add(new Alien(context, posX, posY, screenX, screenY,1,30));
                        numberEnnemi += 1;
                        posY += 150;
                    }
                    posY = 150;
                    posX += 200;
                }
                numberEnnemi = 12;
                return true;

            case 6 :
                listEnnemi.add(new Boss(context, screenX/2, 200, screenX, screenY,20,100));
                numberEnnemi = 1;
                return true;
        }
        return false;
    }

    public ArrayList<Ennemi> getListEnnemis(){
        return listEnnemi;
    }

    public boolean isLevelCleared(){

        for(Ennemi ennemi : listEnnemi){
            if(!ennemi.getVisibility()){
                numTmp++;
            }
        }
        if(numTmp == numberEnnemi){
            numTmp = 0;
            listEnnemi.removeAll(listEnnemi);
            return true;
        }
        numTmp = 0;
        return false;
    }

    public int getNumLevel(){
        return numLevel;
    }

    public void setNumLevel(){
        numLevel++;
    }
}
