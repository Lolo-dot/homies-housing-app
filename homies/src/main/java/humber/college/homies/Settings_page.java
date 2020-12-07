package humber.college.homies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

        // Checking if user is still logged
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();
        USR = getSharedPreferences("spDATABASE",0);
        log_out_btn = (Button) findViewById(R.id.normal_logout_btn);

        // checking if normal loging is good
        USR = getSharedPreferences("spDATABASE",0);
        if(!USR.getBoolean("logbool",false)){
            log_out_btn.setVisibility(View.INVISIBLE);
        }

        log_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = USR.edit();
                editor.putBoolean("logbool",false);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), Login_page.class);
                startActivity(intent);
            }
        });


        // If user pressed log out
        //if (!isLoggedIn){
        //    Intent intent = new Intent(getApplicationContext(), Login_page.class);
        //    startActivity(intent);
        //}


        preferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        darkSwitch = (Switch)findViewById(R.id.darkmodeSwitch);
        checkNightModeActivated();

        darkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    saveNightModeState(true);
                    recreate();
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    saveNightModeState(false);
                    recreate();
                }
            }
        });
    }

    private void saveNightModeState(boolean nightMode) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_ISNIGHTMODE, nightMode);
        editor.apply();
    }

    public void checkNightModeActivated(){
        if(preferences.getBoolean(KEY_ISNIGHTMODE, false)){
            darkSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            darkSwitch.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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