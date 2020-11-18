package humber.college.homies;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class signup_page extends AppCompatActivity {

    EditText mUsername, mEmail, mPhone, mPassword, mConfirmPassword;
    Button  button;
    boolean validation;
    public RelativeLayout layout1;

    public static final String MYPREFERENCES = "nightModePrefs";
    public static final String KEY_ISNIGHTMODE = "isNightMode";
    SharedPreferences  preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_layout);

        preferences = getSharedPreferences(MYPREFERENCES, MODE_PRIVATE);

        layout1 = findViewById(R.id.signupLayout);

        mUsername = findViewById(R.id.signupUserName);
        mEmail = findViewById(R.id.signupEmailAddress);
        mPhone = findViewById(R.id.signupPhone);
        mPassword = findViewById(R.id.signupPassword);
        mConfirmPassword = findViewById(R.id.signupConfirmPassword);
        button = findViewById(R.id.signupButton);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = mUsername.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String phone = mPassword.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                String confirmPassword = mConfirmPassword.getText().toString().trim();
                validation = true;

                if(username.length() == 0){
                    mUsername.requestFocus();
                    mUsername.setError(getString(R.string.Error1));
                    validation = false;
                }
                else if(username.length() < 2){
                    mUsername.requestFocus();
                    mUsername.setError(getString(R.string.Error2));
                    validation = false;
                }

                if(email.length() == 0){
                    mEmail.requestFocus();
                    mEmail.setError(getString(R.string.Error1));
                    validation = false;
                }
                if(phone.length() == 0){
                    mPhone.requestFocus();
                    mPhone.setError(getString(R.string.Error1));
                    validation = false;
                }
                if(password.length() == 0){
                    mPassword.requestFocus();
                    mPassword.setError(getString(R.string.Error1));
                    validation = false;
                }
                else if(password.length() < 6){
                    mPassword.requestFocus();
                    mPassword.setError(getString(R.string.Error4));
                    validation = false;
                }

                if(confirmPassword.length() == 0){
                    mConfirmPassword.requestFocus();
                    mConfirmPassword.setError(getString(R.string.Error1));
                    validation = false;
                }
                else if(!confirmPassword.equals(password)){
                    mConfirmPassword.requestFocus();
                    mConfirmPassword.setError(getString(R.string.Error3));
                    validation = false;
                }

                if(validation){
                   final DatabaseReference myRef = database.getReference("USER/"+username);
                   final signupData data = new signupData(username, password, email, phone);
                   final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("USER");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child(username).exists()){
                                mUsername.requestFocus();
                                mUsername.setError(getString(R.string.Error5));
                            }else{
                                myRef.setValue(data);
                                Intent intent = new Intent(getApplicationContext(), edit_profile_page.class);
                                startActivityForResult(intent, 0);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

 /*   @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.darkModeItem:
            //    item.setChecked(darkModeChecked);
                if(item.isChecked()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    saveNightModeState(true);
                    recreate();

                   // layout1.setBackgroundColor(Color.BLACK);
                 //   themeUtils.changeToTheme(this, themeUtils.BLACK);
                } else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    saveNightModeState(false);
                    recreate();
                 //   themeUtils.changeToTheme(this, themeUtils.DEFAULT);
                }
            //    darkModeChecked = !item.isChecked();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNightModeState(boolean nightMode) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_ISNIGHTMODE, nightMode);
        editor.apply();
    }

    public void checkNightModeActivated(){
        MenuItem darkSwitch = findViewById(R.id.darkModeItem);
        if(preferences.getBoolean(KEY_ISNIGHTMODE, false)){
            darkSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
 //           darkSwitch.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    } */

    public void Go_To_Login(View view){
        Intent intent = new Intent(this, login_page.class);
        startActivity(intent);
    }
}
