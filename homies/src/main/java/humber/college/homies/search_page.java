package humber.college.homies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class search_page extends AppCompatActivity {

    SearchView sv;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    public String playerName;
    public ArrayList<Player> playersList=new ArrayList<>();
    RecyclerView rv;
    MyAdapter adapter=new MyAdapter(this,playersList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testsearchlayout);

        //shared pref delcarations
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("Player Name").commit(); //removing old shared pref of phone number/email

        //floating action circle/bar. probs not needed
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        });

        //declaring views
        sv= (SearchView) findViewById(R.id.mSearch);
        rv= (RecyclerView) findViewById(R.id.myRecycler);

        //setting recycler view properties (animations and using linear layout
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setItemAnimator(new DefaultItemAnimator());

        //setting adapter to recylcer view, of custome type  MyAdapter
        rv.setAdapter(adapter);

        //databse for houses catalog. updates houselist and refreshes adapter using hosuelist
        DatabaseReference refp1 = database.getReference("players");
        refp1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                playersList.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) { //loop to get all data from children of houses catalog
                    Player p = postSnapshot.getValue(Player.class); //assigning object from database to new Player p
                    playersList.add(p); //adding new Player p to list
                }
                adapter.notifyDataSetChanged(); //refreshes adapters player list
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(),"failed to get value",Toast.LENGTH_SHORT).show();
            }
        });

        //search view filter
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                adapter.getFilter().filter(query); //gets filter as defined in CustomFilter java class
                return false;
            }
        });

        //bottom navigationbar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar);
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
                    case R.id.b:
                        intent = new Intent(getBaseContext(), bookmark_page.class);
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
        });//end of bottom nav bar

    }// end of oncreate

}//end of code