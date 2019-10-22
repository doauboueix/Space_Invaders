package com.uqac.ducrosvalentin.lesvaisseauxdelespace;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private SQLiteDatabase bdd;
    private static final String DATABASE_NAME = "ANDROID_DATABASE";
    private static final String TABLE_NAME = "table_score";
    private static final String COL_ID = "ID";
    private static final String COL_SCORE = "score";
    private int numberOfRows;
    private Context context;

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_NAME + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_SCORE + " INTEGER);";

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME + ";");
        onCreate(db);
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public boolean insertScore(int score){

        if(numberOfRows < 5){
            bdd = this.getWritableDatabase();

            //Création d'un ContentValues (fonctionne comme une HashMap)
            ContentValues values = new ContentValues();

            //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
            values.put(COL_SCORE, score);

            //on insère l'objet dans la BDD via le ContentValues
            bdd.insert(TABLE_NAME, null, values);
            bdd.close();

            return true;
        }
        else{
            return false;
        }
    }

    public boolean updateScore(int score){

            bdd = this.getWritableDatabase();
            String updateQuery = "UPDATE " + TABLE_NAME + " SET " + COL_SCORE + " = " + score + " WHERE " + COL_SCORE + " =( SELECT MIN(" + COL_SCORE + ") FROM " + TABLE_NAME + ");";
            Cursor c = bdd.rawQuery(updateQuery, null);
            c.moveToFirst();
            c.close();
            bdd.close();
            return true;
    }

    public void reset(){
        context.deleteDatabase(DATABASE_NAME);
    }

    public int[] getListScore(){
        bdd = this.getReadableDatabase();

        int[] listScore = new int[10];
        int i = 0;

        //Récupère dans un Cursor les valeurs des scores
        Cursor res =  bdd.rawQuery( "select * from " +TABLE_NAME+ " order by " +COL_SCORE+ " desc", null );
        res.moveToFirst();


        while(res.isAfterLast() == false){
            listScore[i] = res.getInt(1);
            i++;
            res.moveToNext();
        }
        res.close();

        numberOfRows = i;
        bdd.close();
        return listScore;
    }

    public int getNumberOfRows(){
        bdd = this.getReadableDatabase();

        int i = 0;

        //Récupère dans un Cursor les valeurs des scores
        Cursor res =  bdd.rawQuery( "select * from " +TABLE_NAME+ " order by " +COL_SCORE+ " desc", null );
        res.moveToFirst();


        while(!res.isAfterLast()){
            i++;
            res.moveToNext();
        }
        res.close();

        numberOfRows = i;
        bdd.close();

        return i;
    }
}
