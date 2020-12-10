package humber.college.homies;
//Team Name: Homies
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatDelegate;

import com.facebook.login.LoginManager;

import static humber.college.homies.Login_page.LOGBOOL;

public class Settings_Fragment extends PreferenceFragment {

    public static final String DARK_MODE_SWITCH = "darkmodeSwitch";
    public static final String LOG_OUT_BUTTON = "Settings_Logout";
    public static final String ABOUT_US_BUTTON = "Aboutus";

    private final static String TAG = Settings_Fragment.class.getName();
    public final static String SETTINGS_SHARED_PREFERENCES_FILE_NAME = TAG + ".SETTINGS_SHARED_PREFERENCES_FILE_NAME";

    SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Define the settings file to use by this settings fragment
        getPreferenceManager().setSharedPreferencesName(SETTINGS_SHARED_PREFERENCES_FILE_NAME);

        addPreferencesFromResource(R.xml.settings_preferences);

        sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals(DARK_MODE_SWITCH)) {
                    boolean darkmodeCheck = sharedPreferences.getBoolean(DARK_MODE_SWITCH, false);

                    if (darkmodeCheck) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        getActivity().recreate();
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        getActivity().recreate();
                    }
                }

            }
        };

        Preference logoutPref = (Preference) findPreference(LOG_OUT_BUTTON);
        logoutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(LOGBOOL, false);
                editor.commit();
                LoginManager.getInstance().logOut();
                Intent d = new Intent(getContext(), Login_page.class);
                startActivity(d);
                return true;
            }
        });

        Preference aboutusPref = findPreference(ABOUT_US_BUTTON);
        aboutusPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent d = new Intent(getContext(), AboutUs.class);
                startActivity(d);
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }
}
