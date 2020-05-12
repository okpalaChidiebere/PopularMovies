package com.example.android.popularmovies.sync;

import android.content.Context;
import android.util.Log;

import com.example.android.popularmovies.repositiory.MovieRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class MovieSyncViewModel extends ViewModel {

    // The name of the Sync Data work
    public static final String SYNC_DATA_WORK_NAME = "sync_data_work_name";
    public static final String TAG_SYNC_DATA = "TAG_SYNC_DATA";

    private MovieRepository mRepository;

    private WorkManager mWorkManager;
    // New instance variable for the WorkInfo
    private LiveData<List<WorkInfo>> mSavedWorkInfo;

    public LiveData<List<WorkInfo>> GetData(Context context){

        if (mSavedWorkInfo == null) {
            mWorkManager = WorkManager.getInstance(context);
            mSavedWorkInfo = mWorkManager.getWorkInfosByTagLiveData(TAG_SYNC_DATA);
            loadData();
        }
        return mSavedWorkInfo;
    }

    public void loadData() {

        // Create Network constraint
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();


        PeriodicWorkRequest periodicSyncDataWork =
                new PeriodicWorkRequest.Builder(MovieWorkManager.class, 20, TimeUnit.HOURS) //sync every 20 hrs
                        .addTag(TAG_SYNC_DATA)
                        .setConstraints(constraints)
                        // setting a backoff on case the work needs to retry
                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .build();
        mWorkManager.enqueueUniquePeriodicWork(
                SYNC_DATA_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP, //Existing Periodic Work policy
                periodicSyncDataWork //work request
        );

    }

    public LiveData<List<WorkInfo>> getOutputWorkInfo() {
        return mSavedWorkInfo;
    }

    public void cancelWork() {
        Log.i("VIEWMODEL", "Cancelling work");
        mWorkManager.cancelUniqueWork(SYNC_DATA_WORK_NAME);
    }

}
