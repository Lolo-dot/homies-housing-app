package humber.college.homies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Message_page extends AppCompatActivity {
    public Button writeToDB;
    public String num;
    private static final int CALL_PERMISSION_CODE = 101;
    public TextView txt_error;
    public boolean call_approve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);

        // Permissions code
        call_approve = false;
        writeToDB = (Button) findViewById(R.id.btWriteToDatabase);
        writeToDB.setOnClickListener(onClickWrite);
        txt_error = (TextView) findViewById(R.id.permission_error_message);

        txt_error.setVisibility(View.INVISIBLE);
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
                        intent = new Intent(getBaseContext(), Search_page.class);
                        startActivity(intent);
                        break;
                    case R.id.m:
                        intent = new Intent(getBaseContext(), Message_page.class);
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

        checkPermission(Manifest.permission.CALL_PHONE, CALL_PERMISSION_CODE);
        // Code for updating buttons text
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        num = sharedPreferences.getString(getString(R.string.number_key_pref), null);
        writeToDB.setText(num);


    }//end of oncreate


    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(Message_page.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(Message_page.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
            txt_error.setVisibility(View.INVISIBLE);
            call_approve = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CALL_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Message_page.this, "Call Permission Granted", Toast.LENGTH_SHORT).show();
                txt_error.setVisibility(View.INVISIBLE);
                writeToDB.setVisibility(View.VISIBLE);
                call_approve = true;
            } else {
                Toast.makeText(Message_page.this, "Call Permission Denied", Toast.LENGTH_SHORT).show();
                txt_error.setVisibility(View.VISIBLE);
                writeToDB.setVisibility(View.INVISIBLE);
                call_approve = false;
            }
        }
    }

        //onclick lsitener for buttoncall_approve
    public View.OnClickListener onClickWrite= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Code for requesting and checking permissions
            checkPermission(Manifest.permission.CALL_PHONE, CALL_PERMISSION_CODE);
            if (call_approve){
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+num));
                startActivity(intent);
            }
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
}//end of code