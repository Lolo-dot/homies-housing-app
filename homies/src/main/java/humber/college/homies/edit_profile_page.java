package humber.college.homies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class edit_profile_page extends AppCompatActivity {
    SharedPreferences USR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);
    }

    public void Go_To_Profile(View view){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

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

        USR = getSharedPreferences("spDATABASE",0);
        final String username = USR.getString("usernameStorage", "Nothing found");
        final profileData data = new profileData(txt_username, txt_age, txt_phone, txt_roommates,txt_description);
        final DatabaseReference myRef2 = database.getReference("PROFILES/"+username);
        myRef2.setValue(data);


        Intent intent = new Intent(view.getContext(), Profile_page.class);
        startActivityForResult(intent, 0);
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
}
