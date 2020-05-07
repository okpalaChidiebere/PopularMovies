package com.example.android.popularmovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //go back parent page action
    }

//    public static class MoviesPreferenceFragment extends PreferenceFragment {
    public static class MoviesPreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener{
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

        ListPreference listPreference = (ListPreference) preference;
        int prefIndex = listPreference.findIndexOfValue(stringValue);
        if (prefIndex >= 0) {
            CharSequence[] labels = listPreference.getEntries();
            preference.setSummary(labels[prefIndex]);
        }
        return true;
    }

        //Helper Method used in order to update the preference summary when the settings activity
        // is launched. This will be called in the onCreate of the
        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString); //calling the event listener that updates the UI
        }

    }

}
