package humber.college.homies;
//Team Name: Homies
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.Arrays;

public class Login_page extends AppCompatActivity {

    EditText mUsername, mPassword;
    Button button;
    CheckBox checkBox;
    SharedPreferences USR;

    public static final String MYPREFERENCES = "nightModePrefs";
    SharedPreferences preferences;
    public static final String DARK_MODE_SWITCH = "darkmodeSwitch";
    public static final String REMEMBER_DETAILS = "rememberDetails";
    public static final String REMEMBER_USERNAME = "rememberUsername";
    public static final String REMEMBER_PASSWORD = "rememberPassword";

    // Facebook SignIn Vaiables
    private LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        darkmodeCheck();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        // Checking if user is already logged in through our normal login method
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (prefs.getBoolean("logbool", false)) {
            Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login Unsuccessfull", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        // End of Facebook code



        mUsername = findViewById(R.id.loginUserName);
        mPassword = findViewById(R.id.loginPassword);
        button = findViewById(R.id.loginButton);
        checkBox = findViewById(R.id.Login_Remember_CheckBox);
        preferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        //     checkNightModeActivated();

        USR = getSharedPreferences("spDATABASE", 0);
        if(USR.getBoolean(REMEMBER_DETAILS, false)){
            mUsername.setText(USR.getString(REMEMBER_USERNAME, ""));
            mPassword.setText(USR.getString(REMEMBER_PASSWORD, ""));
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
                                SignupData data = snapshot.child(username).getValue(SignupData.class);
                                if (data.getPassword().equals(password)) {
                                    USR = getSharedPreferences("spDATABASE", 0);
                                    SharedPreferences.Editor editor = USR.edit();
                                    editor.putString("usernameStorage", username);
                                    editor.putBoolean("logbool", true);
                                    if(checkBox.isChecked()){
                                        editor.putBoolean(REMEMBER_DETAILS, true);
                                        editor.putString(REMEMBER_USERNAME, username);
                                        editor.putString(REMEMBER_PASSWORD, password);
                                    }
                                    else
                                        editor.putBoolean(REMEMBER_DETAILS, false);
                                    editor.apply();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    mPassword.requestFocus();
                                    mPassword.setError(getString(R.string.Error3));
                                }
                            } else {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.Exit_Confirmation);
        builder.setCancelable(true);

        builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        builder.setNegativeButton(getString(R.string.back_pres_neg), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //do nothing
                return;
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void Go_To_Signup(View view) {
        Intent intent = new Intent(this, Signup_page.class);
        startActivity(intent);
    }
}
