package humber.college.homies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

import java.time.Instant;

public class Settings_Fragment extends PreferenceFragment {

    public static final String DARK_MODE_SWITCH = "darkmodeSwitch";
    public static final String LOG_OUT_BUTTON = "Settings_Logout";
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    //Dark Mode Related Stuff
    public static final String MYPREFERENCES = "nightModePrefs";
    public static final String KEY_ISNIGHTMODE = "isNightMode";
    SharedPreferences preferences;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preferences);

        Preference logoutPref = (Preference) findPreference(LOG_OUT_BUTTON);
        logoutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
                if(googleSignInAccount != null)
                    FirebaseAuth.getInstance().signOut();
                else{
                    Intent d = new Intent(getContext(), Login_page.class);
                    startActivity(d);
                }
                return true;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
}
