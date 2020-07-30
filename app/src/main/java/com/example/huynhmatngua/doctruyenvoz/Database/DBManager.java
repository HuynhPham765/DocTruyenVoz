package com.example.huynhmatngua.doctruyenvoz.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.huynhmatngua.doctruyenvoz.Object.Story;

import java.net.IDN;
import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Save_Story";
    private static final String TABLE_NAME = "Story";
    private static final String ID = "ID";
    private static final String NAME = "name";
    private static final String CHAP = "chap";
    private static final String BODY = "body";

    private Context context;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + "int auto_increment," +
                NAME + " TEXT, " +
                CHAP + " TEXT, " +
                BODY + " TEXT)";
        db.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfylly", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "Drop successfylly", Toast.LENGTH_SHORT).show();
    }

    public void addStory(Story story){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, story.getName());
        values.put(CHAP, story.getChap());
        values.put(BODY, story.getBody());
        //Neu de null thi khi value bang null thi loi

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public ArrayList<Story> getAllStory() {
        ArrayList<Story> storyArrayList = new ArrayList<Story>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor == null){
            return null;
        }

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                String chap = cursor.getString(2);
                String body = cursor.getString(3);
                Story story = new Story(name, chap, body);
                storyArrayList.add(story);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return storyArrayList;
    }

    public ArrayList<String> getAllNameOfStory(){

        ArrayList<String> nameArrayList = new ArrayList<>();
        ArrayList<Story> storyArrayList = getAllStory();

        if(storyArrayList == null){
            return null;
        }

        for(int i=0; i<storyArrayList.size(); i++){
            if(checkNameExist(storyArrayList.get(i).getName(), nameArrayList) == false){
                nameArrayList.add(storyArrayList.get(i).getName());
            }
        }
        return nameArrayList;
    }

    public boolean checkNameExist(String name, ArrayList<String> nameArrayList){

        if(nameArrayList != null) {
            for (int i = 0; i < nameArrayList.size(); i++){
                if(nameArrayList.get(i).equals(name)){
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<String> getChapByName(String name){

        ArrayList<String> chapArrayList = new ArrayList<>();
        ArrayList<Story> storyArrayList = getAllStory();

        if(storyArrayList == null){
            return null;
        }

        for(int i=0; i<storyArrayList.size(); i++){
            if(storyArrayList.get(i).getName().equals(name)){

                chapArrayList.add(storyArrayList.get(i).getChap());
            }
        }

        return chapArrayList;
    }

    public ArrayList<String> getChapByNameAfterSort(String name){

        ArrayList<String> chapArrayList = new ArrayList<>();
        ArrayList<Story> storyArrayList = getAllStory();

        if(storyArrayList == null){
            return null;
        }

        ArrayList<Story> sortChap = sortChap(storyArrayList);

        for(int i=0; i<sortChap.size(); i++){
            if(sortChap.get(i).getName().equals(name)){

                chapArrayList.add(sortChap.get(i).getChap());
            }
        }

        return chapArrayList;
    }

    public String getBodybyNameChap(String name, String chap){

        ArrayList<Story> storyArrayList = getAllStory();

        if(storyArrayList == null){
            return null;
        }

        for(int i=0; i<storyArrayList.size(); i++){
            if(storyArrayList.get(i).getName().equals(name) && storyArrayList.get(i).getChap().equals(chap)){

                return storyArrayList.get(i).getBody();
            }
        }
        return null;
    }

    private ArrayList<Story> sortChap(ArrayList<Story> storyArrayList){

        ArrayList<Story> sortChapArrayList = new ArrayList<>();
        int sizeOfList = storyArrayList.size();

        for (int i=0; i<sizeOfList; i++){
            Story minStory = minStory(storyArrayList);
            sortChapArrayList.add(minStory);
            storyArrayList.remove(minStory);
        }
        return sortChapArrayList;
    }

    private Story minStory(ArrayList<Story> storyArrayList){

        if(storyArrayList == null){
            return null;
        }

        for(int i=0; i<storyArrayList.size(); i++){

            Story storyCurrent = storyArrayList.get(i);
            String[] chapCurrent = storyCurrent.getChap().trim().split("\\s");
            if(chapCurrent[1].length() > 2){
                return storyCurrent;
            }
        }

        Story minStory = storyArrayList.get(0);
        String[] chap = minStory.getChap().trim().split("\\s");
        int minChap = Integer.parseInt(chap[1]);
        for (int i=1; i<storyArrayList.size(); i++){

            Story storyCurrent = storyArrayList.get(i);
            String[] chapCurrent = storyCurrent.getChap().trim().split("\\s");
            int mChapCurrent = Integer.parseInt(chapCurrent[1]);
            if(minChap > mChapCurrent){
                minChap = mChapCurrent;
                minStory = storyCurrent;
            }
        }

        return minStory;
    }
}
