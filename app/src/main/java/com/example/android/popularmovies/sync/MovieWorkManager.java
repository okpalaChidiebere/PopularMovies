package com.example.android.popularmovies.sync;

import android.content.Context;
import android.util.Log;

import com.example.android.popularmovies.utils.NotificationUtils;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MovieWorkManager extends Worker {

    private static final String TAG = MovieWorkManager.class.getSimpleName();

    public MovieWorkManager(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {

        Log.i(TAG, "Fetching Data from Remote host");

        // Do the work here--in this case, upload the images.
        Context context = getApplicationContext();
        //MovieSyncTask.syncMovie(context);
        MovieSyncUtils.startImmediateSync(context);
        NotificationUtils.notifyUserOfNewWeather(context);

        // Indicate whether the task finished successfully with the Result
        return Result.success();
    }

    //more better implementation here
    //https://medium.com/swlh/periodic-tasks-with-android-workmanager-c901dd9ba7bc
}
