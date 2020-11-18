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

public class bookmark_page extends AppCompatActivity {

    SearchView sv;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    public ArrayList<House> houseList=new ArrayList<>();
    RecyclerView rv;
    MyAdapter adapter=new MyAdapter(this,houseList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testbookmark_layout);

        //decalring shared prefs
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("house Name").commit(); //removing old phone number/email sharepref on startup.

        //flotaing action bar, probs not needed
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabBookMark);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        });

        //declaring views
        sv= (SearchView) findViewById(R.id.bookSearchView);
        rv= (RecyclerView) findViewById(R.id.myRecyclerBookMark);

        //setting rv properties
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

        //setting recycler views adapter ( of type MyAdapter, which is custom made)
        rv.setAdapter(adapter);

        //getting bookmarked houses database and adding to arraylist
        DatabaseReference refp1 = database.getReference("Bookmarked Houses");
        refp1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                houseList.clear();//clearing current list

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {//loop to get all values and add to houseList
                    House p = postSnapshot.getValue(House.class);
                    houseList.add(p);
                }
                adapter.notifyDataSetChanged(); //refreshing adapter with updated list values??
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(),"failed to get value",Toast.LENGTH_SHORT).show();
            }
        });

        //setting filter to sv (search view) on typing
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                adapter.getFilter().filter(query);
                return false;
            }
        });

        //bottom navigation bar
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

    }//end of oncreate

}//end of code