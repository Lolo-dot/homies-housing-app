package humber.college.homies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;

public class Settings_pageV2 extends AppCompatActivity {

    public static final String DARK_MODE_SWITCH = "darkmodeSwitch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        if(findViewById(R.id.setting_fragment_container) != null) {
            if (savedInstanceState != null)
                return;

            getFragmentManager().beginTransaction().add(R.id.setting_fragment_container, new Settings_Fragment()).commit();
            settingsCheck();
        }
    }

    public void settingsCheck(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean darkmodeCheck = sharedPreferences.getBoolean(DARK_MODE_SWITCH, false);

        if(darkmodeCheck){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            recreate();
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate();
        }
    }
}