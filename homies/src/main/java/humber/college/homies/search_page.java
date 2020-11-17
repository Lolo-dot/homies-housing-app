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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("Player Name").commit();




        //private FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");

        //Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        });

        sv= (SearchView) findViewById(R.id.mSearch);
        rv= (RecyclerView) findViewById(R.id.myRecycler);

        //SET ITS PROPETRIES
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setAdapter(adapter);


        DatabaseReference refp1 = database.getReference("players");
        refp1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String name = dataSnapshot.getValue(String.class);
                //Toast.makeText(getApplicationContext(),"loading message:"+playerName,Toast.LENGTH_SHORT).show();

                //players.clear();
                playersList.clear();


                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Player p = postSnapshot.getValue(Player.class);
                    //p.setName(name);
                    //p.setPos("Midfielder");
                    //p.setImg(R.drawable.herera);
                    playersList.add(p);
/*
                    for(Player p:playersList) {
                        if(p.getName().equals(p.getName())) {
                            p.setName(p.getName());
                            p.setPhone(p.getPhone());
                            p.setPos(p.getPos());
                            p.setImg(p.getImg());
                            p.setBookmarked(p.getBookmarked());
                        }
                    }*/

                    //String imgName2 = "@drawable/carrick";
                    //int image = getResources().getIdentifier(imgName2, null, getPackageName());
                    //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>" +imgName2+"<<<<<<<<<<<<<<<<<<<<<<<<");
                    //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>" +image+"<<<<<<<<<<<<<<<<<<<<<<<<");

                }
                adapter.notifyDataSetChanged();

               /* Player p=new Player();
                p.setName(name);
                p.setPos("Midfielder");
                p.setImg(R.drawable.herera);
                players.add(p);

                p=new Player();
                p.setName("David De Geaa");
                p.setPos("Goalkeeper");
                p.setImg(R.drawable.degea);
                players.add(p);

                p=new Player();
                p.setName("Michael Carrick");
                p.setPos("Midfielder");
                p.setImg(R.drawable.carrick);
                players.add(p);

                p=new Player();
                p.setName("Juan Mata");
                p.setPos("Playmaker");
                p.setImg(R.drawable.mata);
                players.add(p);

                p=new Player();
                p.setName("Diego Costa");
                p.setPos("Striker");
                p.setImg(R.drawable.costa);
                players.add(p);

                p=new Player();
                p.setName("Oscar");
                p.setPos("Playmaker");
                p.setImg(R.drawable.oscar);
                players.add(p);*/
               // p.getimg;
                //int image = getApplicationContext().getResources().getIdentifier(imgName, null, getApplicationContext().getPackageName());


            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(),"failed to get value",Toast.LENGTH_SHORT).show();
            }
        });

        //ADAPTER
        //final MyAdapter adapter=new MyAdapter(this,players);
        //rv.setAdapter(adapter);

        //SEARCH
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
        });
    }

    /*private void savePreferences(String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }*/

/*
    public String AccessDatabase(String playerNum){

        DatabaseReference refp1 = database.getReference("p1");
        refp1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String name = dataSnapshot.getValue(String.class);
                Toast.makeText(getApplicationContext(),"loading message:"+playerName,Toast.LENGTH_SHORT).show();

                ArrayList<Player> players=new ArrayList<>();

                Player p=new Player();
                p.setName(name);
                p.setPos("Midfielder");
                p.setImg(R.drawable.herera);
                players.add(p);

                p=new Player();
                p.setName("David De Geaa");
                p.setPos("Goalkeeper");
                p.setImg(R.drawable.degea);
                players.add(p);

                p=new Player();
                p.setName("Michael Carrick");
                p.setPos("Midfielder");
                p.setImg(R.drawable.carrick);
                players.add(p);

                p=new Player();
                p.setName("Juan Mata");
                p.setPos("Playmaker");
                p.setImg(R.drawable.mata);
                players.add(p);

                p=new Player();
                p.setName("Diego Costa");
                p.setPos("Striker");
                p.setImg(R.drawable.costa);
                players.add(p);

                p=new Player();
                p.setName("Oscar");
                p.setPos("Playmaker");
                p.setImg(R.drawable.oscar);
                players.add(p);

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(),"failed to get value",Toast.LENGTH_SHORT).show();
            }
        });
        return playerName;
    }*/

    public void getName(String Name){
        playerName=Name;
    }




      /*  ArrayList<Player> players=new ArrayList<>();

        Player p=new Player();
        p.setName(playerName);
        p.setPos("Midfielder");
        p.setImg(R.drawable.herera);
        players.add(p);

        p=new Player();
        p.setName("David De Geaa");
        p.setPos("Goalkeeper");
        p.setImg(R.drawable.degea);
        players.add(p);

        p=new Player();
        p.setName("Michael Carrick");
        p.setPos("Midfielder");
        p.setImg(R.drawable.carrick);
        players.add(p);

        p=new Player();
        p.setName("Juan Mata");
        p.setPos("Playmaker");
        p.setImg(R.drawable.mata);
        players.add(p);

        p=new Player();
        p.setName("Diego Costa");
        p.setPos("Striker");
        p.setImg(R.drawable.costa);
        players.add(p);

        p=new Player();
        p.setName("Oscar");
        p.setPos("Playmaker");
        p.setImg(R.drawable.oscar);
        players.add(p);*/



}