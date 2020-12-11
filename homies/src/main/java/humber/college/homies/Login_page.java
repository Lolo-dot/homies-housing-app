package humber.college.homies;
//Team Name: Homies
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_page extends AppCompatActivity {

    EditText mUsername, mPassword;
    Button button;
    CheckBox checkBox;
    SharedPreferences prefs;

    public static final String DARK_MODE_SWITCH = "darkmodeSwitch";
    public static final String REMEMBER_DETAILS = "rememberDetails";
    public static final String REMEMBER_USERNAME = "rememberUsername";
    public static final String REMEMBER_PASSWORD = "rememberPassword";
    public static final String LOGBOOL = "logbool";

    // Facebook SignIn Vaiables
    private LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        darkmodeCheck();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        // Checking if user is already logged in through our normal login method
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (prefs.getBoolean(LOGBOOL, false)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        // Facebook API Code
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
        loginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        // End of Facebook code

        mUsername = findViewById(R.id.loginUserName);
        mPassword = findViewById(R.id.loginPassword);
        button = findViewById(R.id.loginButton);
        checkBox = findViewById(R.id.Login_Remember_CheckBox);

        if(prefs.getBoolean(REMEMBER_DETAILS, false)){
            mUsername.setText(prefs.getString(REMEMBER_USERNAME, ""));
            mPassword.setText(prefs.getString(REMEMBER_PASSWORD, ""));
            checkBox.setChecked(true);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = mUsername.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                boolean validation = true;

                if (username.length() == 0) {
                    mUsername.requestFocus();
                    mUsername.setError(getString(R.string.Error1));
                    validation = false;
                }

                if (password.length() == 0) {
                    mPassword.requestFocus();
                    mPassword.setError(getString(R.string.Error1));
                    validation = false;
                }

                if (validation) {
                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("USER");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child(username).exists()) {
                                    String data = snapshot.child(username).child("password").getValue(String.class);
                                    if (data.equals(password)) {
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putString("usernameStorage", username);
                                        editor.putBoolean(LOGBOOL, true);
                                        if (checkBox.isChecked()) {
                                            editor.putBoolean(REMEMBER_DETAILS, true);
                                            editor.putString(REMEMBER_USERNAME, username);
                                            editor.putString(REMEMBER_PASSWORD, password);
                                        } else {
                                            editor.putBoolean(REMEMBER_DETAILS, false);
                                        }
                                        editor.apply();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        mPassword.requestFocus();
                                        mPassword.setError(getString(R.string.Error_Username_Password));
                                    }
                                } else{
                                    mUsername.requestFocus();
                                    mUsername.setError(getString(R.string.Error7));
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

    private void darkmodeCheck() {
        SharedPreferences preferences = getSharedPreferences(Settings_Fragment.SETTINGS_SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        boolean darkmodeCheck = preferences.getBoolean(DARK_MODE_SWITCH, false);

        if (darkmodeCheck) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Stuff for facebook api
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.Exit_Confirmation))
                .setCancelable(false)
                .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                })
                .setNegativeButton(R.string.back_pres_neg, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do nothing
                        return;
                    }
                });
        builder.setIcon(R.drawable.alert_icon);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void Go_To_Signup(View view) {
        Intent intent = new Intent(this, Signup_page.class);
        startActivity(intent);
    }
}
