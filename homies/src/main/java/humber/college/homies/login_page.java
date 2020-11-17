package humber.college.homies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class login_page extends AppCompatActivity {

    EditText mUsername, mPassword;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        mUsername = findViewById(R.id.loginUserName);
        mPassword = findViewById(R.id.loginPassword);
        button = findViewById(R.id.loginButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                boolean validation = true;

                if(username.length() == 0){
                    mUsername.requestFocus();
                    mUsername.setError(getString(R.string.Error1));
                    validation = false;
                }

                if(password.length() == 0){
                    mPassword.requestFocus();
                    mPassword.setError(getString(R.string.Error1));
                    validation = false;
                }

                if(validation){
                    Intent intent = new Intent(view.getContext(), search_page.class);
                    startActivity(intent);
                }
            }
        });

    }

   /* public void Go_To_Search(View view){

        if((validationName())&&(validationPword())) {
            Intent intent = new Intent(this, search_page.class);
            startActivity(intent);
        }
    }*/

    public void Go_To_Signup(View view){
        Intent intent = new Intent(this, signup_page.class);
        startActivity(intent);
    }

}
