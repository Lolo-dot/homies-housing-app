package humber.college.homies;
//Team Name: Homies
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

public class Settings_page extends AppCompatActivity {

    SharedPreferences preferences;
    boolean vali_normal_login;
    boolean vali_face_login;

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

    /*public void Go_To_Login(View view) {

        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this, Login_page.class);
        startActivity(intent);
    }*/

   /* @Override
    public void onBackPressed() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean normal_log = prefs.getBoolean("logbool",false);

        if (isLoggedIn==false&&normal_log==false){
            Intent intent = new Intent(getApplicationContext(), Login_page.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
    /*@Override
    public void onBackPressed() {

    }*/
}