package humber.college.homies;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login_page extends AppCompatActivity {

    EditText mUsername, mPassword;
    Button button;
    SharedPreferences USR;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        mUsername = findViewById(R.id.loginUserName);
        mPassword = findViewById(R.id.loginPassword);
        button = findViewById(R.id.loginButton);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = mUsername.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
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
                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("USER");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.child(username).exists()){
                              signupData data = snapshot.child(username).getValue(signupData.class);
                              if(data.getPassword().equals(password)){
                                  USR = getSharedPreferences("spDATABASE",0);
                                  SharedPreferences.Editor editor = USR.edit();
                                  editor.putString("usernameStorage", username);
                                  editor.apply();
                                  Intent intent = new Intent(getApplicationContext(), Search_page.class);
                                  startActivity(intent);
                              }
                              else{
                                    mPassword.requestFocus();
                                    mPassword.setError(getString(R.string.Error6));
                                }
                            }else{
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage(R.string.Exit_Confirmation);
        builder.setCancelable(true);

        builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void Go_To_Signup(View view){
        Intent intent = new Intent(this, signup_page.class);
        startActivity(intent);
    }

}
