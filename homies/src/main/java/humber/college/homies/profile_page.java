package humber.college.homies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class profile_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        Intent intent = getIntent();
        String profile_name = intent.getStringExtra("message1");
        String profile_age = intent.getStringExtra("message2");
        String profile_phone = intent.getStringExtra("message3");
        String profile_roommates = intent.getStringExtra("message4");
        String profile_description = intent.getStringExtra("message5");

        TextView textview = findViewById(R.id.UserName);
        textview.setText(profile_name);
        TextView textview2 = findViewById(R.id.Age);
        textview2.setText(profile_age);
        TextView textview3 = findViewById(R.id.Phone);
        textview3.setText(profile_phone);
        TextView textview4 = findViewById(R.id.Roommates);
        textview4.setText(profile_roommates);
        TextView textview5 = findViewById(R.id.Description);
        textview5.setText(profile_description);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.s:
                        intent = new Intent(getBaseContext(), search_page.class);
                        startActivity(intent);
                        break;
                    case R.id.m:
                        intent = new Intent(getBaseContext(), message_page.class);
                        startActivity(intent);
                        break;
                    case R.id.p:
                        intent = new Intent(getBaseContext(), profile_page.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public void Go_To_EditProfile(View view){
        Intent intent = new Intent(view.getContext(), edit_profile_page.class);
        startActivityForResult(intent, 0);
    }

}