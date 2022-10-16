package vn.tapbi.youtubeplayer3.data.local.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import vn.tapbi.youtubeplayer3.data.model.HistoryModel;
import vn.tapbi.youtubeplayer3.data.model.RecentModel;

@Database(entities = {RecentModel.class , HistoryModel.class}, version = 2 , exportSchema = false)
public abstract class RecentDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "recent.db";
    private static RecentDatabase instance;

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `history` (`id` INTEGER NOT NULL, `keyword` TEXT , PRIMARY KEY(`id`))");
        }
    };


    public static synchronized RecentDatabase getInstance(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),RecentDatabase.class ,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return instance;
    }

    public abstract RecentSeachDao recentDao();
}
