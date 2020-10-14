package humber.college.homies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

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


}