package humber.college.homies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    ProgressBar progressBar;
    boolean validation;

 /*   private static final int SIZE = 128;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1"; */

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

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
                else if (usernameExists(username)){
                    mUsername.requestFocus();
                    mUsername.setError(getString(R.string.Error5));
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
                                validation = false;
                            }else{
                                myRef.setValue(data);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                if(validation){
                    Intent intent = new Intent(view.getContext(), edit_profile_page.class);
                    startActivityForResult(intent, 0);
                }
            }
        });
    }

    /*public static String HashFunction(String password){
            final SecureRandom random = null;
            byte[] salt = new byte[SIZE/8];
            random.nextBytes(salt);
            byte[] dk = pbkdf2(password, salt, 1 << cost);
    }

    public static byte[] pbkdf2(char[] password, byte[] salt, int iterations){
        KeySpec spec = new PBEKeySpec(password, salt, iterations, SIZE);
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM);
            return f.generateSecret(spec).getEncoded();
        }
        catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Missing algorithm: " + ALGORITHM, ex);
        }
        catch (InvalidKeySpecException ex) {
            throw new IllegalStateException("Invalid SecretKeyFactory", ex);
        }
    }*/

    public boolean usernameExists(String username) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("USER/" + username);
        return (reference != null);
    }

    public void Go_To_EditProfile(View view){
        Intent intent = new Intent(view.getContext(), edit_profile_page.class);
        startActivityForResult(intent, 0);
    }

    public void Go_To_Search(View view) {
    }

    public void Go_To_Signup(View view) {
    }
    public void Go_To_Login(View view){
        Intent intent = new Intent(this, login_page.class);
        startActivity(intent);
    }
}
