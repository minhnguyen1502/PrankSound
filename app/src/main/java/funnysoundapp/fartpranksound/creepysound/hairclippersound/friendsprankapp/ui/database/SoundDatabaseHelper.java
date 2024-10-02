package funnysoundapp.fartpranksound.creepysound.hairclippersound.friendsprankapp.ui.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SoundDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "soundDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "sounds";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_IMG = "img";
    private static final String COLUMN_IMG_HOME = "img_home";
    private static final String COLUMN_IMG_FAVORITE = "img_favorite";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SOUND = "sound";
    private static final String COLUMN_IS_FAVORITE = "isFavorite";
    private static final String COLUMN_TYPE = "type";

    public SoundDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Inside SoundDatabaseHelper
    public void updateFavorite(int id, boolean isFavorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_FAVORITE, isFavorite ? 1 : 0);
        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public boolean areSoundsPresent() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SOUNDS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_IMG + " INTEGER,"
                + COLUMN_IMG_HOME + " INTEGER,"
                + COLUMN_IMG_FAVORITE + " INTEGER,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_SOUND + " INTEGER,"
                + COLUMN_IS_FAVORITE + " INTEGER,"
                + COLUMN_TYPE + " TEXT" + ")";
        db.execSQL(CREATE_SOUNDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Add a new sound to the database
    public void addSound(SoundModel soundModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMG, soundModel.getImg());
        values.put(COLUMN_IMG_HOME, soundModel.getImgHome());
        values.put(COLUMN_IMG_FAVORITE, soundModel.getImgFavorite());
        values.put(COLUMN_NAME, soundModel.getName());
        values.put(COLUMN_SOUND, soundModel.getSound());
        values.put(COLUMN_IS_FAVORITE, soundModel.isFavorite() ? 1 : 0);
        values.put(COLUMN_TYPE, soundModel.getType());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<SoundModel> getFavoriteSounds() {
        List<SoundModel> favoriteSounds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_IS_FAVORITE + " = 1"; // Query to select only favorite sounds
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                int img = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMG));
                int imgHome = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMG_HOME));
                int imgFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMG_FAVORITE));
                int name = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                int sound = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SOUND));
                boolean isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FAVORITE)) == 1;
                String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE));

                SoundModel soundModel = new SoundModel(id, img,imgHome,imgFavorite, name, sound, isFavorite, type);
                favoriteSounds.add(soundModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favoriteSounds;
    }

    public void addSounds(List<SoundModel> sounds) {
        for (SoundModel sound : sounds) {
            addSound(sound);
        }
    }
    public SoundModel getSoundById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            int img = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMG));
            int imgHome = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMG_HOME));
            int imgFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMG_FAVORITE));
            int name = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            int sound = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SOUND));
            boolean isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FAVORITE)) == 1;
            String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE));

            SoundModel soundModel = new SoundModel(id, img,imgHome,imgFavorite, name, sound, isFavorite, type);
            cursor.close();
            db.close();
            return soundModel;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
            return null;
        }
    }

    // Get all sounds from the database
//    public List<SoundModel> getAllSounds() {
//        List<SoundModel> soundList = new ArrayList<>();
//        String selectQuery = "SELECT * FROM " + TABLE_NAME;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
//                int img = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMG));
//                int img = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMG));
//                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
//                int sound = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SOUND));
//                boolean isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FAVORITE)) == 1;
//                String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE));
//
//                SoundModel soundModel = new SoundModel(id, img, name, sound, isFavorite, type);
//                soundList.add(soundModel);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//        return soundList;
//    }

    public List<SoundModel> getSoundsByType(String type) {
        List<SoundModel> soundsByType = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TYPE + " = ?"; // Query to select sounds by type
        Cursor cursor = db.rawQuery(selectQuery, new String[]{type});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                int img = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMG));
                int imgHome = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMG_HOME));
                int imgFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMG_FAVORITE));
                int name = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                int sound = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SOUND));
                boolean isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FAVORITE)) == 1;
                String soundType = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE));

                SoundModel soundModel = new SoundModel(id, img,imgHome,imgFavorite, name, sound, isFavorite, soundType);
                soundsByType.add(soundModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return soundsByType;
    }

    // Update a sound
//    public int updateSound(SoundModel soundModel) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_IMG, soundModel.getImg());
//        values.put(COLUMN_NAME, soundModel.getName());
//        values.put(COLUMN_SOUND, soundModel.getSound());
//        values.put(COLUMN_IS_FAVORITE, soundModel.isFavorite() ? 1 : 0);
//        values.put(COLUMN_TYPE, soundModel.getType());
//
//        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
//                new String[]{String.valueOf(soundModel.getId())});
//    }
//
//    // Delete a sound
//    public void deleteSound(SoundModel soundModel) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
//                new String[]{String.valueOf(soundModel.getId())});
//        db.close();
//    }
}
