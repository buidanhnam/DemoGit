package com.framgia.soundclound.data.source.local.Sqlite;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import static com.framgia.soundclound.data.source.local.Sqlite.AlbumType.NAME_ALBUM;
import static com.framgia.soundclound.data.source.local.Sqlite.AlbumType.ID_ALBUM;
import static com.framgia.soundclound.data.source.local.Sqlite.AlbumType.NUMBER_SONG;
import static com.framgia.soundclound.data.source.local.Sqlite.AlbumType.LIST_TRACK;
import static com.framgia.soundclound.data.source.local.Sqlite.DatabseType.DATABASE_NAME;
import static com.framgia.soundclound.data.source.local.Sqlite.TableName.TABLE_ALBUM;
import static com.framgia.soundclound.data.source.local.Sqlite.VERSION.VERSION_DB;

/**
 * TabType in Tablayout
 */

@StringDef({ NAME_ALBUM, ID_ALBUM, NUMBER_SONG, LIST_TRACK})
@interface AlbumType {
    String NAME_ALBUM = "name";
    String ID_ALBUM = "nam_key";
    String NUMBER_SONG = "number_song";
    String LIST_TRACK = "list_track";
    String IMAGE_ALBUM = "image";
}

@StringDef({DATABASE_NAME})
@interface DatabseType {
    String DATABASE_NAME = "DB_SOUNDCLOUND";

}
@StringDef({TABLE_ALBUM})
@interface TableName{
    String TABLE_ALBUM = "album";
}

@IntDef({VERSION_DB})
@interface  VERSION{
    int VERSION_DB = 1 ;
}

