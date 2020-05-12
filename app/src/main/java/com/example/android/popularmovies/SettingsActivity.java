package com.example.android.popularmovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.android.popularmovies.sync.MovieSyncUtils;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //go back parent page action
    }

//    public static class MoviesPreferenceFragment extends PreferenceFragment {
    public static class MoviesPreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener{

        private static final String CONN_ERROR = "No Internet Connection";
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.settings_main);

            //find the preference using the key
            Preference porpularityDESC = findPreference(getString(R.string.settings_sort_key));
            //Update Preference Summary
            bindPreferenceSummaryToValue(porpularityDESC);
        }

    //this method will be called when the user has changed a Preference, so we update the UI
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        String stringValue = newValue.toString();
        Toast error = Toast.makeText(getActivity(), CONN_ERROR, Toast.LENGTH_SHORT);

        if(isConnected()) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                CharSequence[] labels = listPreference.getEntries();
                preference.setSummary(labels[prefIndex]);
            }
        }
        else{
            error.show();
            return false;
        }
        return true;
    }


    @Override
    public void onStop() {
        super.onStop();
        // unregister the preference change listener
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // register the preference change listener
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Activity activity = getActivity();
        Toast error = Toast.makeText(getActivity(), "Please connect to the Internet", Toast.LENGTH_SHORT);

        if (key.equals(getString(R.string.settings_sort_key))) {

            if(isConnected()) {
                MovieSyncUtils.startImmediateSync(activity);
            }
        }
    }

        //Helper Method used in order to update the preference summary when the settings activity
        // is launched. This will be called in the onCreate of the
        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString); //calling the event listener that updates the UI
        }

    private boolean isConnected() {
        // Check for connectivity status
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    }

}
