package humber.college.homies;

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

    //Google Signin Variables
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private ConstraintLayout constraintLayout;
    ImageButton verifyGoogle;
    private FirebaseAuth mAuth;

    // Facebook SignIn Vaiables
    private LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

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

        mAuth = FirebaseAuth.getInstance();
        createGoogleRequest();
        mUsername = findViewById(R.id.loginUserName);
        mPassword = findViewById(R.id.loginPassword);
        button = findViewById(R.id.loginButton);
        checkBox = findViewById(R.id.Login_Remember_CheckBox);
        verifyGoogle = findViewById(R.id.Login_Google);
        constraintLayout = findViewById(R.id.loginLayout);
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

        verifyGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn_Google();
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


    private void createGoogleRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn_Google() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }

        // Stuff for facebook api
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                            FirebaseUser user = mAuth.getCurrentUser();

                            final String username = user.getDisplayName();
                            String password = null;
                            String email = user.getEmail();

                            final DatabaseReference myRef = database.getReference("USER/" + username);
                            final SignupData data = new SignupData(username, password, email);
                            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("USER");
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.child(username).exists()) {
                                        Intent intent = new Intent(getApplicationContext(), Search_page.class);
                                        startActivity(intent);
                                    } else {
                                        myRef.setValue(data);
                                        Intent intent = new Intent(getApplicationContext(), edit_profile_page.class);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            Intent intent = new Intent(getApplicationContext(), Search_page.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Snackbar.make(constraintLayout, getString(R.string.Authentication_Failed), Snackbar.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
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
