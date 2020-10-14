package humber.college.homies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class login_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
            setContentView(R.layout.login_layout);

    }

    public void Go_To_Signup(View view){
        Intent intent = new Intent(view.getContext(), signup_page.class);
        startActivityForResult(intent, 0);
    }

}
