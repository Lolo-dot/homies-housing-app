package humber.college.homies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class edit_profile_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);
    }

    public void Go_To_Profile(View view){
        EditText edittext1 = (EditText)findViewById(R.id.UserName);
        String txt_username = getString(R.string.editprofile_name)+edittext1.getText().toString();

        EditText edittext2 = (EditText)findViewById(R.id.Age);
        String txt_age = getString(R.string.editprofile_age)+edittext2.getText().toString();

        EditText edittext3 = (EditText)findViewById(R.id.Phone);
        String txt_phone = getString(R.string.editprofile_phone)+edittext3.getText().toString();

        EditText edittext4 = (EditText)findViewById(R.id.Roommates);
        String txt_roommates = getString(R.string.editprofile_roomates)+edittext4.getText().toString();

        EditText edittext5 = (EditText)findViewById(R.id.Description);
        String txt_description = getString(R.string.editprofile_description)+edittext5.getText().toString();

        Intent intent = new Intent(view.getContext(), Profile_page.class);
        intent.putExtra("message1", txt_username);
        intent.putExtra("message2", txt_age);
        intent.putExtra("message3", txt_phone);
        intent.putExtra("message4", txt_roommates);
        intent.putExtra("message5", txt_description);
        startActivityForResult(intent, 0);
    }
}
