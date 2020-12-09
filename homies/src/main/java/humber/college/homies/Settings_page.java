package humber.college.homies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.AccessToken;

public class Settings_page extends AppCompatActivity {

    private Switch darkSwitch;
    boolean isLoggedIn;
    SharedPreferences USR;
    public static final String MYPREFERENCES = "nightModePrefs";
    public static final String KEY_ISNIGHTMODE = "isNightMode";
    SharedPreferences preferences;
    boolean vali_normal_login;
    boolean vali_face_login;
    Button log_out_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        if(findViewById(R.id.setting_fragment_container) != null){
            if(savedInstanceState != null)
                return;

            getFragmentManager().beginTransaction().add(R.id.setting_fragment_container, new Settings_Fragment()).commit();
        }

    }

    @Override
    public void onBackPressed() {
        vali_normal_login = false;
        vali_face_login = false;
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();

        // checking if normal loging is good
        if(!USR.getBoolean("logbool",false)){
            vali_normal_login=true;
            //Toast.makeText(getApplicationContext(),"Normal False",Toast.LENGTH_LONG).show();
        }
        if(!isLoggedIn){
            vali_face_login = true;
        }
        if (vali_face_login&&vali_normal_login){
            Intent intent = new Intent(getApplicationContext(), Login_page.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        //Toast.makeText(getApplicationContext(),"Back Press",Toast.LENGTH_LONG).show();
    }
}