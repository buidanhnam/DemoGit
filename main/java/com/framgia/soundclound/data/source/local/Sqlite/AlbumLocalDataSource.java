package com.framgia.soundclound.data.source.local.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.framgia.soundclound.data.model.Album;
import com.framgia.soundclound.data.model.Track;
import com.framgia.soundclound.data.source.AlbumDataSource;
import com.framgia.soundclound.util.BindingUtil;

import java.util.ArrayList;
import java.util.List;

import static com.framgia.soundclound.data.source.local.Sqlite.AlbumType.ID_ALBUM;
import static com.framgia.soundclound.data.source.local.Sqlite.AlbumType.IMAGE_ALBUM;
import static com.framgia.soundclound.data.source.local.Sqlite.AlbumType.LIST_TRACK;
import static com.framgia.soundclound.data.source.local.Sqlite.AlbumType.NAME_ALBUM;
import static com.framgia.soundclound.data.source.local.Sqlite.AlbumType.NUMBER_SONG;
import static com.framgia.soundclound.data.source.local.Sqlite.DatabseType.DATABASE_NAME;
import static com.framgia.soundclound.data.source.local.Sqlite.TableName.TABLE_ALBUM;

/**
 * Created by Bui Danh Nam on 8/1/2018.
 */

public class AlbumLocalDataSource extends SQLiteOpenHelper implements AlbumDataSource {
    private static int VERSION = 1;
    private static AlbumLocalDataSource sInstance;

    public static AlbumLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AlbumLocalDataSource(context);
        }
        return sInstance;
    }

    public AlbumLocalDataSource(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + TABLE_ALBUM + " (" +
                NAME_ALBUM + " TEXT, " +
                ID_ALBUM + " INTEGER  primary key," +
                IMAGE_ALBUM + " TEXT, " +
                NUMBER_SONG + " INTEGER," +
                LIST_TRACK + " TEXT)";
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    @Override
    public List<Album> getAllAlbum() {
        List<Album> albums = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_ALBUM,
                null, null, null,
                null, null, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    albums.add(parseCursorToAlbum(cursor));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return albums;
    }


    @Override
    public boolean addAlbum(Album album) {
        if (album == null) {
            return false;
        }
        if (checkNameAlbumExist(album.getName())) {
            return false;
        }
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_ALBUM, album.getName());
//        contentValues.put(ID_ALBUM, album.getId());
        contentValues.put(NUMBER_SONG, album.getNumberSong());
        contentValues.put(IMAGE_ALBUM, album.getImage());
        String listTrack = BindingUtil.getToList(album.getTracks());
        contentValues.put(LIST_TRACK, listTrack);
        long result = sqLiteDatabase.insert(TABLE_ALBUM, null, contentValues);
        return result != -1;
    }

    @Override
    public Album getAlbumById(int idAlbum) {
        SQLiteDatabase database = sInstance.getReadableDatabase();
        Cursor cursor = database.query(TABLE_ALBUM,
                new String[]{ID_ALBUM, NAME_ALBUM, NUMBER_SONG, IMAGE_ALBUM, LIST_TRACK},
                ID_ALBUM + "=?",
                new String[]{String.valueOf(idAlbum)},
                null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return parseCursorToAlbum(cursor);
        }
        return null;
    }


    @Override
    public Album getAlbumByName(String nameAlbum) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_ALBUM,
                new String[]{ID_ALBUM, NAME_ALBUM, NUMBER_SONG, IMAGE_ALBUM, LIST_TRACK},
                NAME_ALBUM + "=?",
                new String[]{String.valueOf(nameAlbum)},
                null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return parseCursorToAlbum(cursor);
        }
        return null;
    }

    @Override
    public boolean addTrackinAlbum(Album album, Track track) {
        if (album == null) {
            return false;
        }
        if (track == null) {
            return false;
        }
        List<Track> tracksOld = album.getTracks();
        if (tracksOld == null) {
            return false;
        }
        if (tracksOld.contains(track)) {
            return false;
        }
        tracksOld.add(track);
        List<Track> tracksNew = new ArrayList<>(tracksOld);
        album.setTracks(tracksNew);
        return updateAlbum(album);
    }

    @Override
    public boolean remoteTrack(Album album, Track track) {
        if (album == null) {
            return false;
        }
        if (track == null) {
            return false;
        }
        List<Track> tracksOld = album.getTracks();
        if (tracksOld == null) {
            return false;
        }
        if (tracksOld.remove(track)) {
            List<Track> tracksNew = new ArrayList<>(tracksOld);
            album.setTracks(tracksNew);
            return updateAlbum(album);
        }
        return false;
    }

    @Override
    public boolean renameAlbum(Album album) {
        if (album == null) {
            return false;
        }
        if (checkNameAlbumExist(album.getName())) {
            return false;
        }
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NUMBER_SONG, album.getName());
        String where = ID_ALBUM + " = " + album.getId();
        int numRow = writableDatabase.update(TABLE_ALBUM, contentValues, where, null);
        return numRow > 0;
    }

    @Override
    public boolean updateAlbum(Album album) {
        if (album == null) {
            return false;
        }
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_ALBUM, album.getName());
        contentValues.put(NUMBER_SONG, album.getNumberSong());
        contentValues.put(IMAGE_ALBUM, album.getImage());
        String listTrack = BindingUtil.getToList(album.getTracks());
        contentValues.put(LIST_TRACK, listTrack);
        String where = ID_ALBUM + " = " + album.getId();
        int numRow = writableDatabase.update(TABLE_ALBUM, contentValues, where, null);
        return numRow > 0;
    }

    public boolean checkNameAlbumExist(String nameAlbum) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_ALBUM,
                new String[]{ID_ALBUM,}, NAME_ALBUM + "=?",
                new String[]{String.valueOf(nameAlbum)},
                null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    @Override
    public boolean deleteAlbum(Album album) {
        if (album == null) {
            return false;
        }
        SQLiteDatabase dbWrite = this.getWritableDatabase();
        int index = dbWrite.delete(TABLE_ALBUM, ID_ALBUM + " = ?",
                new String[]{String.valueOf(album.getId())});
        return index > 0;
    }

    private Album parseCursorToAlbum(Cursor cursor) {
        int indexNameAlbum = cursor.getColumnIndex(NAME_ALBUM);
        int indexKeyName = cursor.getColumnIndex(ID_ALBUM);
        int indexImage = cursor.getColumnIndex(IMAGE_ALBUM);
        int indexNumberSong = cursor.getColumnIndex(NUMBER_SONG);
        int indexListTrack = cursor.getColumnIndex(LIST_TRACK);

        Album album = new Album();
        album.setId(cursor.getInt(indexKeyName));
        album.setName(cursor.getString(indexNameAlbum));
        album.setImage(cursor.getString(indexImage));
        album.setNumberSong(cursor.getInt(indexNumberSong));
        List<Track> tracks = BindingUtil.getToJson(cursor.getString(indexListTrack));
        album.setTracks(tracks);
        return album;
    }

}
