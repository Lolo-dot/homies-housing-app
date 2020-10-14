package humber.college.homies;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class edit_profile_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.s:
                        intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.m:
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

    public void Go_To_Profile(View view){
        Intent intent = new Intent(view.getContext(), profile_page.class);
        startActivityForResult(intent, 0);
    }
}
