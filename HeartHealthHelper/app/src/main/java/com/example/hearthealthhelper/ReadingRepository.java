package com.example.hearthealthhelper;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ReadingRepository {
    private ReadingDao readingDao;
    private LiveData<List<Reading>> allReadings;

    public ReadingRepository(Application application) {
        ReadingDatabase readingDatabase = ReadingDatabase.GetInstance(application);
        this.readingDao = readingDatabase.ReadingDao();
        this.allReadings = readingDao.getAllReadings();
    }

    public void insert(Reading reading) { new InsertReadingAsyncTask(readingDao).execute(); }
    public void delete(Reading reading) {new DeleteReadingAsyncTask(readingDao).execute(); }
    public void  update(Reading reading) { new UpdateReadingAsyncTask(readingDao).execute(); }
    public void deleteAll() { new DeleteAllReadingsAsyncTask(readingDao).execute();}
    public LiveData<List<Reading>> getAllReadings() { return allReadings; }


    private class InsertReadingAsyncTask extends AsyncTask<Reading, Void, Void> {

        private ReadingDao readingDao;

        public InsertReadingAsyncTask(ReadingDao readingDao) {
            this.readingDao = readingDao;
        }
        @Override
        protected Void doInBackground(Reading... readings) {
            readingDao.insert(readings[0]);
            return null;
        }
    }

    private class DeleteReadingAsyncTask extends AsyncTask<Reading, Void, Void> {
        private ReadingDao readingDao;

        public DeleteReadingAsyncTask(ReadingDao readingDao) {
            this.readingDao = readingDao;
        }
        @Override
        protected Void doInBackground(Reading... readings) {
            readingDao.delete(readings[0]);
            return null;
        }
    }

    private class UpdateReadingAsyncTask extends  AsyncTask<Reading, Void, Void> {
        private ReadingDao readingDao;

        public UpdateReadingAsyncTask(ReadingDao readingDao) {
            this.readingDao = readingDao;
        }
        @Override
        protected Void doInBackground(Reading... readings) {
            readingDao.update(readings[0]);
            return null;
        }
    }

    private class DeleteAllReadingsAsyncTask extends  AsyncTask<Void, Void, Void> {
        private ReadingDao readingDao;

        public DeleteAllReadingsAsyncTask(ReadingDao readingDao) {
            this.readingDao = readingDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            readingDao.deleteAll();
            return null;
        }
    }
}
