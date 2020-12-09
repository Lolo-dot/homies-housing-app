package humber.college.homies;
//Team Name: Homies
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    public MainActivity(){
        super(R.layout.searchlayout);
    }
    //SearchView sv;
    //private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //public ArrayList<House> housesList=new ArrayList<>();
    //RecyclerView rv;
    //MyAdapter adapter=new MyAdapter(this,housesList);
    ConstraintLayout constraintSnackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlayout);
        final Fragment searchFrag = new Search_page();
        final Fragment bookmarkFrag = new Bookmark_page();
        final Fragment profileFrag = new Profile_page();
        final Fragment messageFrag = new Message_page();
        final Fragment addFrag  = new Add_House();
        if(getIntent().getStringExtra("openFragment")!=null) {
            if (getIntent().getStringExtra("openFragment").equals("MessageFrag")) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragmentContent, messageFrag, null)
                        .commit();
            }
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentContent, searchFrag, null)
                    .commit();
        }
        if(getIntent().getIntExtra("openProfile",0)==1){
            //set the desired fragment as current fragment to fragment pager
            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .replace(R.id.fragmentContent, profileFrag, null).addToBackStack(null).commit();
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar);
            bottomNavigationView.setSelectedItemId(R.id.p);
        }
        constraintSnackLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        createNotification("Welcome to Homies");

        //shared pref delcarations
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("House Name").commit(); //removing old shared pref of phone number/email

        //floating action circle/bar. probs not needed
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.snack_bar_value), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.action_snack_bar), null).show();
            }

        });*/


        //bottom navigationbar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.s:
                        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                                .replace(R.id.fragmentContent, searchFrag, null).addToBackStack(null).commit();
                        break;
                    case R.id.m:
                        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                                .replace(R.id.fragmentContent, messageFrag, null).addToBackStack(null).commit();
                        break;
                    case R.id.a:
                        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                                .replace(R.id.fragmentContent, addFrag, null).addToBackStack(null).commit();
                        break;
                    case R.id.b:
                        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                                .replace(R.id.fragmentContent, bookmarkFrag, null).addToBackStack(null).commit();
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.constraintLayout), R.string.bookmarks_welcome, Snackbar.LENGTH_SHORT);snackbar.show();
                        break;
                    case R.id.p:
                        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                                .replace(R.id.fragmentContent, profileFrag, null).addToBackStack(null).commit();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });//end of bottom nav bar

    }// end of oncreate

    public void Go_To_EditProfile(View view){
        Intent intent = new Intent(this, Edit_profile_page.class);
        //startActivityForResult(intent, 0);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.back_press_tit))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.back_pres_post), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                })
                .setNegativeButton(getString(R.string.back_pres_neg), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do nothing
                        return;
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
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

    public void createNotification(String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                MainActivity.this
        )
                .setSmallIcon(R.drawable.unbookmarked_vector)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(message)
                .setAutoCancel(true);

        Intent noti = new Intent(this, Add_House.class);
        noti.addFlags(noti.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                0, noti, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(
                Context.NOTIFICATION_SERVICE
        );
        notificationManager.notify(0, builder.build());
    }


}//end of code