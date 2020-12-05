package humber.college.homies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile_page extends AppCompatActivity {
    SharedPreferences USR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);


        Intent intent = getIntent();

        String profile_name = intent.getStringExtra("message1");
        String profile_age = intent.getStringExtra("message2");
        String profile_phone = intent.getStringExtra("message3");
        String profile_roommates = intent.getStringExtra("message4");
        String profile_description = intent.getStringExtra("message5");


        USR = getSharedPreferences("spDATABASE",0);
        final String username = USR.getString("usernameStorage", getString(R.string.nothing_found));
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("PROFILES");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(username).exists()){
                    ProfileData data = snapshot.child(username).getValue(ProfileData.class);
                    TextView textview = findViewById(R.id.UserName);
                    textview.setText(getString(R.string.profileName) +data.getUsername());
                    TextView textview2 = findViewById(R.id.Age);
                    textview2.setText(getString(R.string.profileAge) +data.getAge());
                    TextView textview3 = findViewById(R.id.Phone);
                    textview3.setText(getString(R.string.profilePhone) +data.getPhoneNumber());
                    TextView textview4 = findViewById(R.id.Roommates);
                    textview4.setText(getString(R.string.profileRoommates) +data.getRoomMates());
                    TextView textview5 = findViewById(R.id.Description);
                    textview5.setText(data.getDescription());
                }else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.s:
                        intent = new Intent(getBaseContext(), Search_page.class);
                        startActivity(intent);
                        break;
                    case R.id.m:
                        intent = new Intent(getBaseContext(),  Message_page.class);
                        startActivity(intent);
                        break;
                    case R.id.b:
                        intent = new Intent(getBaseContext(), Bookmark_page.class);
                        startActivity(intent);
                        break;
                    case R.id.p:
                        intent = new Intent(getBaseContext(), Profile_page.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.settings_item:
                Intent intent = new Intent(this, Settings_page.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Go_To_EditProfile(View view){
        Intent intent = new Intent(this, edit_profile_page.class);
        //startActivityForResult(intent, 0);
        startActivity(intent);
    }

}