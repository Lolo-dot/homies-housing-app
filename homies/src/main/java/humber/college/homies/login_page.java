package humber.college.homies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class login_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
            setContentView(R.layout.login_layout);

    }

    private Boolean validationName(){
        EditText edittext1 = (EditText)findViewById(R.id.UserName);
        String txt_username = edittext1.getText().toString();

        if(txt_username.isEmpty()) {
            edittext1.setText("Field Can Not Be Empty");
            return false;
        }
        return true;
    }

    private Boolean validationPword(){
        EditText edittext1 = (EditText)findViewById(R.id.Password);
        String txt_username = edittext1.getText().toString();

        if(txt_username.isEmpty()) {
            edittext1.setText("Field Can Not Be Empty");
            return false;
        }
        return true;
    }

    public void Go_To_Profile(View view){

        if((validationName())&&(validationPword())) {
            Intent intent = new Intent(view.getContext(), signup_page.class);
            startActivity(intent);
        }
    }

    public void Go_To_Signup(View view){
        Intent intent = new Intent(view.getContext(), signup_page.class);
        startActivityForResult(intent, 0);
    }

}
