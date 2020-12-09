package humber.college.homies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Settings_pageV2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        if(findViewById(R.id.setting_fragment_container) != null) {
            if (savedInstanceState != null)
                return;

            getFragmentManager().beginTransaction().add(R.id.setting_fragment_container, new Settings_Fragment()).commit();
        }
    }


}