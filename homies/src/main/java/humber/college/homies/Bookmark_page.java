package humber.college.homies;
//Team Name: Homies
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Bookmark_page extends Fragment {

    SearchView sv;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    public ArrayList<House> houseList = new ArrayList<>();
    RecyclerView rv;
    MyAdapter adapter;
    String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookmark_content_main, container, false);

        adapter = new MyAdapter(getContext(), houseList);

        //decalring shared prefs
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getString(R.string.number_key_pref)).commit(); //removing old phone number/email sharepref on startup.

        username = sharedPreferences.getString("usernameStorage","failed");

        //flotaing action bar, probs not needed
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabBookMark);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.snack_bar_value), Snackbar.LENGTH_LONG)
                        .setAction(R.string.action_snack_bar, null).show();
            }

        });*/

//Populate the spinner in the fragment
        final Spinner spinner = (Spinner) view.findViewById(R.id.bookSortsSpinner);


        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(view.getContext(), R.array.sortTypes,
                android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapterSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                //Toast.makeText(getContext(),"price low to hhhhigh selected",Toast.LENGTH_SHORT).show();
                if(sv.getQuery().toString()==null||sv.getQuery().toString().equals("")) {
                switch (parentView.getItemAtPosition(position).toString()) {
                    case "Sort":
                        //Toast.makeText(getContext(),"Sort",Toast.LENGTH_SHORT).show();

                        break;
                    case "Price:Low to High":
                            //Toast.makeText(getContext(),"price low to high selected",Toast.LENGTH_SHORT).show();
                            Collections.sort(houseList, House.PriceLowTooHigh);
                            adapter.notifyDataSetChanged();
                        break;
                    case "Price:High to Low":
                        //Toast.makeText(getContext(),"Price:High to Low",Toast.LENGTH_SHORT).show();
                        Collections.sort(houseList, House.PriceHighToLow);
                        adapter.notifyDataSetChanged();
                        break;
                    case "Alphabetical:A-Z":
                        //Toast.makeText(getContext(),"Alphabetical:A-Z",Toast.LENGTH_SHORT).show();
                        Collections.sort(houseList, House.NameAToZ);
                        adapter.notifyDataSetChanged();
                        break;
                    case "Alphabetical:Z-A":
                        //Toast.makeText(getContext(),"Alphabetical:Z-A",Toast.LENGTH_SHORT).show();
                        Collections.sort(houseList, House.NameZToA);
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        //Toast.makeText(getContext(),"default break",Toast.LENGTH_SHORT).show();
                        break;
                }//end of switch
                } else{
                    spinner.setSelection(0);
                    //Toast.makeText(getContext(),"Search bar must be empty to change sort",Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.constraintLayout), "Search bar must be empty to change sort", Snackbar.LENGTH_LONG);snackbar.show();
                }
            }//end of onitme selected

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                Toast.makeText(getContext(),"price low to high sssssselected",Toast.LENGTH_SHORT).show();
            }
        });

        //declaring views
        sv = (SearchView) view.findViewById(R.id.bookSearchView);
        rv = (RecyclerView) view.findViewById(R.id.myRecyclerBookMark);
        //but = (Button) view.findViewById(R.id.bookButton);
/*
        but.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(sv.getQuery().toString()==null||sv.getQuery().toString().equals("")) {
                    //Toast.makeText(getContext(),"success",Toast.LENGTH_SHORT).show();
                    Collections.sort(houseList, House.HouseNameAZ);
                    adapter.notifyDataSetChanged();
                } else{
                    Toast.makeText(getContext(),"Search bar must be empty to change sort",Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        //setting rv properties
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));// should be bookmark or this isntead of activity?
        rv.setItemAnimator(new DefaultItemAnimator());

        //setting recycler views adapter ( of type MyAdapter, which is custom made)
        rv.setAdapter(adapter);


        final DatabaseReference refUserName = FirebaseDatabase.getInstance().getReference().child("USER").child(username);//reference to specificshared pref username

        final DatabaseReference refUseBookmarkedrHouses = FirebaseDatabase.getInstance().getReference().child("USER").child(username).child("userBookmarkedHouses");
        //getting bookmarked houses database and adding to arraylist
       // DatabaseReference refp1 = database.getReference(getString(R.string.bookmark_path_page_database));
        refUseBookmarkedrHouses.addValueEventListener(new ValueEventListener() {
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
                Toast.makeText(getActivity(), getString(R.string.toast_onCanceled_bookmark), Toast.LENGTH_SHORT).show();
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

        return view;
    }//end of oncreate view
/*
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using

        // On selecting a spinner item
        String item = parent.getItemAtPosition(pos).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        if(parent.getItemAtPosition(pos).toString().equals("Price:Low to High")){
            if(sv.getQuery().toString()==null||sv.getQuery().toString().equals("")) {
                Toast.makeText(getContext(),"price low to high selected",Toast.LENGTH_SHORT).show();
                Collections.sort(houseList, House.HouseNameAZ);
                adapter.notifyDataSetChanged();
            } else{
                Toast.makeText(getContext(),"Search bar must be empty to change sort",Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}