package humber.college.homies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile_page extends Fragment {
    SharedPreferences USR;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final View view = inflater.inflate(R.layout.profile_layout, container, false);

/*
        Intent intent = getIntent();

        String profile_name = intent.getStringExtra("message1");
        String profile_age = intent.getStringExtra("message2");
        String profile_phone = intent.getStringExtra("message3");
        String profile_roommates = intent.getStringExtra("message4");
        String profile_description = intent.getStringExtra("message5");
*/

        USR = getActivity().getSharedPreferences("spDATABASE",0);
        final String username = USR.getString("usernameStorage", getString(R.string.nothing_found));
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("PROFILES");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(username).exists()){
                    ProfileData data = snapshot.child(username).getValue(ProfileData.class);
                    TextView textview = view.findViewById(R.id.UserName);
                    textview.setText(getString(R.string.profileName) +data.getUsername());
                    TextView textview2 = view.findViewById(R.id.Age);
                    textview2.setText(getString(R.string.profileAge) +data.getAge());
                    TextView textview3 = view.findViewById(R.id.Phone);
                    textview3.setText(getString(R.string.profilePhone) +data.getPhoneNumber());
                    TextView textview4 = view.findViewById(R.id.Roommates);
                    textview4.setText(getString(R.string.profileRoommates) +data.getRoomMates());
                    TextView textview5 = view.findViewById(R.id.Description);
                    textview5.setText(data.getDescription());
                }else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
/*
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
                Intent intent = new Intent(getActivity(), Settings_page.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
 */

}