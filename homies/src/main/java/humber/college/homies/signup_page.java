package humber.college.homies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class signup_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

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
