package humber.college.homies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

public class message_page extends AppCompatActivity {
    public Button writeToDB;
    public EditText edBox;
    public TextView textBox;
    //public TextView textPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);

        writeToDB = (Button) findViewById(R.id.btWriteToDatabase);
        writeToDB.setOnClickListener(onClickWrite);
        textBox = (TextView)findViewById(R.id.tvMessages);
        //textPrefs = (TextView)findViewById(R.id.tvPrefs);

       // textPrefs.setText(loadSavedPreferences()); //set to laod phone numebr and email

        //edBox=(EditText)findViewById(R.id.edMessages);

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


    }//end of oncreate

    //change textbox value (needs to be cleaned up /blended into anotehr fucntion
    public void changetv(String val){
        textBox.setText(val);
    }

    //save loaded rpeferences for phone number and email
    private String loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //loading preferences
        String houseName = sharedPreferences.getString("House Name", null);
        return houseName;
    }

    //onclick lsitener for button
    public View.OnClickListener onClickWrite= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //String data= edBox.getText().toString();
            changetv(loadSavedPreferences());
        }
    };
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
}//end of code