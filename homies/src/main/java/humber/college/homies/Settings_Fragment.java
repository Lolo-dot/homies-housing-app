package humber.college.homies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

import java.time.Instant;

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
                GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
                if(signInAccount != null){
                    FirebaseAuth.getInstance().signOut();
                }
                else {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("logbool", false);
                    editor.commit();
                }
                Intent d = new Intent(getContext(), Login_page.class);
                startActivity(d);
                return true;
            }
        });

        Preference aboutusPref = findPreference(ABOUT_US_BUTTON);
        aboutusPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                String Description = "Currently there a lot of students (especially international students) struggling to find affordable housing near the college/university that they attend. Our application aims to combat that problem by allowing students to search for housing near their institutions, that also meet their requirements. The renters place advertisements of their housings with the requirements that they have, and the students use filters to search for suitable housing.";
                builder.setMessage(Description);
                builder.setCancelable(true);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
