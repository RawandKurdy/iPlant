package e.marsgroup.iplant.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rawan on 3/18/2018.
 */

public class databaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "iplant";

    // Table Names
    private static final String TABLE_PLANT = "plant";
    private static final String TABLE_PLANTED = "planted";

    //Common Columns
    private static final String KEY_ID = "id";

    //Plant Columns
    private static final String KEY_NAME = "name";
    private static final String KEY_INFO = "info";
    private static final String KEY_WATERINFO = "waterinfo";
    private static final String KEY_SEASON = "season";
    private static final String KEY_IMGURL = "imgurl";
    private static final String KEY_MOREINFOURL = "moreinfourl";
    private static final String KEY_ESTIMATEDEPOCHSECONDS = "estimatedepochseconds";

    //Planted Columns
    private static final String KEY_IDOFPLANT = "idofplant";
    private static final String KEY_STARTEPOCHTIME = "startepochtime";
    private static final String KEY_ESTIMATEDENDEPOCHTIME = "estimatedendepochtime";

    //Plant table create statement
    private static final String CREATE_TABLE_PLANT = "CREATE TABLE "
            + TABLE_PLANT + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_INFO + " TEXT,"+ KEY_WATERINFO
            + " TEXT,"+ KEY_SEASON + " TEXT,"+ KEY_IMGURL
            + " TEXT," + KEY_MOREINFOURL + " TEXT,"+ KEY_ESTIMATEDEPOCHSECONDS + " INTEGER" + ")";

    //Planted table create statement
    private static final String CREATE_TABLE_PLANTED = "CREATE TABLE " + TABLE_PLANTED
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_IDOFPLANT + " INTEGER,"
            + KEY_STARTEPOCHTIME + " INTEGER," + KEY_ESTIMATEDENDEPOCHTIME + " INTEGER" + ")";

    public databaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_PLANT);
        db.execSQL(CREATE_TABLE_PLANTED);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANTED);

        // create new tables
        onCreate(db);
    }

    //Create a plant
    public long createPlant(plant plant) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, plant.getName());
        values.put(KEY_INFO, plant.getInfo());
        values.put(KEY_WATERINFO, plant.getWaterinfo());
        values.put(KEY_SEASON, plant.getSeason());
        values.put(KEY_IMGURL, plant.getImgurl());
        values.put(KEY_MOREINFOURL, plant.getMoreinfoURL());
        values.put(KEY_ESTIMATEDEPOCHSECONDS, plant.getEstimatedEpochSeconds());
        // insert row
        long plant_id = db.insert(TABLE_PLANT, null, values);

        return plant_id;
    }

    //Returns A plant
    public plant getPlant(long plant_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PLANT + " WHERE "
                + KEY_ID + " = " + plant_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        plant plant = new plant();
        plant.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        plant.setName((c.getString(c.getColumnIndex(KEY_NAME))));
        plant.setInfo(c.getString(c.getColumnIndex(KEY_INFO)));
        plant.setWaterinfo(c.getString(c.getColumnIndex(KEY_WATERINFO)));
        plant.setSeason((c.getString(c.getColumnIndex(KEY_SEASON))));
        plant.setImgurl(c.getString(c.getColumnIndex(KEY_IMGURL)));
        plant.setMoreinfoURL(c.getString(c.getColumnIndex(KEY_MOREINFOURL)));
        plant.setEstimatedEpochSeconds((c.getLong(c.getColumnIndex(KEY_ESTIMATEDEPOCHSECONDS))));


        return plant;
    }

    //Count Plants
    public int getCountOfPlants() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  COUNT(id) AS NUM_P FROM " + TABLE_PLANT ;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        int count=c.getInt(c.getColumnIndex("NUM_P"));

        return count;
    }

    /*
 * getting all plants
 * */
    public List<plant> getAllPlants() {
        List<plant> plants = new ArrayList<plant>();
        String selectQuery = "SELECT  * FROM " + TABLE_PLANT;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                plant plant = new plant();
                plant.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                plant.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                plant.setInfo(c.getString(c.getColumnIndex(KEY_INFO)));
                plant.setWaterinfo(c.getString(c.getColumnIndex(KEY_WATERINFO)));
                plant.setSeason((c.getString(c.getColumnIndex(KEY_SEASON))));
                plant.setImgurl(c.getString(c.getColumnIndex(KEY_IMGURL)));
                plant.setMoreinfoURL(c.getString(c.getColumnIndex(KEY_MOREINFOURL)));
                plant.setEstimatedEpochSeconds((c.getLong(c.getColumnIndex(KEY_ESTIMATEDEPOCHSECONDS))));

                // adding to plants list
                plants.add(plant);
            } while (c.moveToNext());
        }

        return plants;
    }


    /*
 * getting all plants
 * */
    public List<plant> getPlantsRandomly(int limit) {
        List<plant> plants = new ArrayList<plant>();
        String selectQuery = "SELECT * FROM " +TABLE_PLANT+
                " ORDER BY RANDOM() LIMIT "+limit ;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                plant plant = new plant();
                plant.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                plant.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                plant.setInfo(c.getString(c.getColumnIndex(KEY_INFO)));
                plant.setWaterinfo(c.getString(c.getColumnIndex(KEY_WATERINFO)));
                plant.setSeason((c.getString(c.getColumnIndex(KEY_SEASON))));
                plant.setImgurl(c.getString(c.getColumnIndex(KEY_IMGURL)));
                plant.setMoreinfoURL(c.getString(c.getColumnIndex(KEY_MOREINFOURL)));
                plant.setEstimatedEpochSeconds((c.getLong(c.getColumnIndex(KEY_ESTIMATEDEPOCHSECONDS))));

                // adding to plants list
                plants.add(plant);
            } while (c.moveToNext());
        }

        return plants;
    }



    /*
* getting all plants per season
* */
    public List<plant> getAllPlantsBySeason(String season) {
        List<plant> plants = new ArrayList<plant>();
        String selectQuery = "SELECT  * FROM " + TABLE_PLANT +" WHERE "+KEY_SEASON +" ='"+season+"'";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                plant plant = new plant();
                plant.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                plant.setName((c.getString(c.getColumnIndex(KEY_NAME))));
                plant.setInfo(c.getString(c.getColumnIndex(KEY_INFO)));
                plant.setWaterinfo(c.getString(c.getColumnIndex(KEY_WATERINFO)));
                plant.setSeason((c.getString(c.getColumnIndex(KEY_SEASON))));
                plant.setImgurl(c.getString(c.getColumnIndex(KEY_IMGURL)));
                plant.setMoreinfoURL(c.getString(c.getColumnIndex(KEY_MOREINFOURL)));
                plant.setEstimatedEpochSeconds((c.getLong(c.getColumnIndex(KEY_ESTIMATEDEPOCHSECONDS))));

                // adding to plants list
                plants.add(plant);
            } while (c.moveToNext());
        }

        return plants;
    }

    //NOT USED
    /*
 * Updating a plant
 */
    public int updatePlant(plant plant) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, plant.getName());
        values.put(KEY_INFO, plant.getInfo());
        values.put(KEY_WATERINFO, plant.getWaterinfo());
        values.put(KEY_SEASON, plant.getSeason());
        values.put(KEY_IMGURL, plant.getImgurl());
        values.put(KEY_MOREINFOURL, plant.getMoreinfoURL());
        values.put(KEY_ESTIMATEDEPOCHSECONDS, plant.getEstimatedEpochSeconds());

        // updating row
        return db.update(TABLE_PLANT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(plant.getId()) });
    }

    //NOT USED
    /*
 * Deleting a Plant
 */
    public void deletePlant(long plant_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLANT, KEY_ID + " = ?",
                new String[] { String.valueOf(plant_id) });
    }

    //Planted

    //Create a planted
    public long createPlanted(planted planted) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IDOFPLANT, planted.getIdofPlant());
        values.put(KEY_STARTEPOCHTIME, planted.getStartEpochTime());
        values.put(KEY_ESTIMATEDENDEPOCHTIME, planted.getEstimatedEndEpochTime());
        // insert row
        long planted_id = db.insert(TABLE_PLANTED, null, values);

        return planted_id;
    }

    //Returns A planted
    public planted getPlanted(long planted_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PLANTED + " WHERE "
                + KEY_ID + " = " + planted_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        planted planted = new planted();
        planted.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        planted.setIdofPlant((c.getInt(c.getColumnIndex(KEY_IDOFPLANT))));
        planted.setStartEpochTime(c.getLong(c.getColumnIndex(KEY_STARTEPOCHTIME)));
        planted.setEstimatedEndEpochTime(c.getLong(c.getColumnIndex(KEY_ESTIMATEDENDEPOCHTIME)));

        return planted;
    }

    /*
 * getting all planteds
 * */
    public List<planted> getAllPlanteds() {
        List<planted> planteds = new ArrayList<planted>();
        String selectQuery = "SELECT  * FROM " + TABLE_PLANTED;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                planted planted = new planted();
                planted.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                planted.setIdofPlant((c.getInt(c.getColumnIndex(KEY_IDOFPLANT))));
                planted.setStartEpochTime(c.getLong(c.getColumnIndex(KEY_STARTEPOCHTIME)));
                planted.setEstimatedEndEpochTime(c.getLong(c.getColumnIndex(KEY_ESTIMATEDENDEPOCHTIME)));

                // adding to planteds list
                planteds.add(planted);
            } while (c.moveToNext());
        }

        return planteds;
    }

    //NOT USED
    /*
 * Updating a planted
 */
    public int updatePlanted(planted planted) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IDOFPLANT, planted.getIdofPlant());
        values.put(KEY_STARTEPOCHTIME, planted.getStartEpochTime());
        values.put(KEY_ESTIMATEDENDEPOCHTIME, planted.getEstimatedEndEpochTime());

        // updating row
        return db.update(TABLE_PLANTED, values, KEY_ID + " = ?",
                new String[] { String.valueOf(planted.getId()) });
    }

    //NOT USED
    /*
 * Deleting a Plant
 */
    public void deletePlanted(long planted_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLANTED, KEY_ID + " = ?",
                new String[] { String.valueOf(planted_id) });
    }

    //after you done quereing
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
