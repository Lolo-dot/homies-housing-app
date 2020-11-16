package humber.college.homies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup_page extends AppCompatActivity {

    EditText mUsername, mEmail, mPhone, mPassword, mConfirmPassword;
    Button  button;
    ProgressBar progressBar;
    boolean validation = true;

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
        progressBar = findViewById(R.id.signupProgressBar);
        progressBar.setVisibility(View.INVISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String phone = mPassword.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String confirmPassword = mConfirmPassword.getText().toString().trim();

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

            }
        });

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
