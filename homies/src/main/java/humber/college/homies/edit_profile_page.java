package humber.college.homies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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
        EditText edittext1 = (EditText)findViewById(R.id.UserName);
        String txt_username = edittext1.getText().toString();

        EditText edittext2 = (EditText)findViewById(R.id.Age);
        String txt_age = edittext2.getText().toString();

        EditText edittext3 = (EditText)findViewById(R.id.Phone);
        String txt_phone = edittext3.getText().toString();

        EditText edittext4 = (EditText)findViewById(R.id.Roommates);
        String txt_roommates = edittext4.getText().toString();

        EditText edittext5 = (EditText)findViewById(R.id.Description);
        String txt_description = edittext5.getText().toString();

        Intent intent = new Intent(view.getContext(), profile_page.class);
        intent.putExtra("message1", txt_username);
        intent.putExtra("message2", txt_age);
        intent.putExtra("message3", txt_phone);
        intent.putExtra("message4", txt_roommates);
        intent.putExtra("message5", txt_description);
        startActivityForResult(intent, 0);
    }
}
