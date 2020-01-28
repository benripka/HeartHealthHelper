package com.example.hearthealthhelper;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;

@Database(entities = Reading.class, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ReadingDatabase extends RoomDatabase {

    private static ReadingDatabase instance;

    public abstract ReadingDao ReadingDao();

    public static synchronized ReadingDatabase GetInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ReadingDatabase.class, "reading_database").fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    private static RoomDatabase.Callback onCreate = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbTask(instance).execute();
        }
    };

    private static class PopulateDbTask extends AsyncTask<Void, Void, Void> {

        private ReadingDao readingDao;

        public PopulateDbTask(ReadingDatabase readingDb) {
            this.readingDao = readingDb.ReadingDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            readingDao.insert(new Reading(new Date(), 120, 80, 65, "Everything seems ok"));
            readingDao.insert(new Reading(new Date(), 134, 82, 34, "Everything seems ok"));
            readingDao.insert(new Reading(new Date(), 137, 45, 45, "Everything seems ok"));
            return null;
        }
    }
}
