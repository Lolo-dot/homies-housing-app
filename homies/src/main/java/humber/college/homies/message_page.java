package humber.college.homies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class message_page extends AppCompatActivity {
    public Button writeToDB;
    public EditText edBox;
    public TextView textBox;

    //private FirebaseDatabase database;
    //private DatabaseReference myRefTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);

        writeToDB = (Button) findViewById(R.id.btWriteToDatabase);
        writeToDB.setOnClickListener(onClickWrite);
        textBox = (TextView)findViewById(R.id.tvMessages);

        edBox=(EditText)findViewById(R.id.edMessages);

        //database = FirebaseDatabase.getInstance();
        //myRefTest = database.getReference("TestMessage");

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");

       // myRefTest.setValue("Hello, World!");

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

    public void testFireBaseWrite(String value){
       // FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");

        //myRef.setValue("Hello, World!");
        //myRef.setValue(value);
        //Toast.makeText(getApplicationContext(),"writing "+value+" to FireBase!",Toast.LENGTH_SHORT).show();
        //myRef.addValueEventListener(new ValueEventListener() {
            //@Override
            //public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //textBox.setText(value);
            //}

            //@Override
            //public void onCancelled(DatabaseError error) {
             //   Toast.makeText(getApplicationContext(),"failed to get value",Toast.LENGTH_SHORT).show();
            //}
        //});
    }


    public View.OnClickListener onClickWrite= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String data= edBox.getText().toString();
            //Toast.makeText(getApplicationContext(),"writing "+data+" to FireBase!",Toast.LENGTH_SHORT).show();
            testFireBaseWrite(data);
        }
    };

}