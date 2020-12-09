package humber.college.homies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

public class Search_page extends Fragment {

    SearchView sv;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    public ArrayList<House> housesList = new ArrayList<>();
    RecyclerView rv;
    MyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);

        adapter = new MyAdapter(getContext(), housesList);

        //shared pref delcarations
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getString(R.string.number_key_pref)).commit(); //removing old shared pref of phone number/email

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

        final Spinner spinner = (Spinner) view.findViewById(R.id.searchSortsSpinner);


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
                            Collections.sort(housesList, House.PriceLowTooHigh);
                            adapter.notifyDataSetChanged();
                            break;
                        case "Price:High to Low":
                            //Toast.makeText(getContext(),"Price:High to Low",Toast.LENGTH_SHORT).show();
                            Collections.sort(housesList, House.PriceHighToLow);
                            adapter.notifyDataSetChanged();
                            break;
                        case "Alphabetical:A-Z":
                            //Toast.makeText(getContext(),"Alphabetical:A-Z",Toast.LENGTH_SHORT).show();
                            Collections.sort(housesList, House.NameAToZ);
                            adapter.notifyDataSetChanged();
                            break;
                        case "Alphabetical:Z-A":
                            //Toast.makeText(getContext(),"Alphabetical:Z-A",Toast.LENGTH_SHORT).show();
                            Collections.sort(housesList, House.NameZToA);
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
        sv = (SearchView) view.findViewById(R.id.mSearch);
        rv = (RecyclerView) view.findViewById(R.id.myRecycler);

        //setting recycler view properties (animations and using linear layout
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setItemAnimator(new DefaultItemAnimator());

        //setting adapter to recylcer view, of custome type  MyAdapter
        rv.setAdapter(adapter);

        //databse for houses catalog. updates houselist and refreshes adapter using hosuelist
        DatabaseReference refp1 = database.getReference(getString(R.string.database_ref_search_gae));
        refp1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                housesList.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) { //loop to get all data from children of houses catalog
                    House p = postSnapshot.getValue(House.class); //assigning object from database to new House p
                    housesList.add(p); //adding new house p to list
                }
                adapter.notifyDataSetChanged(); //refreshes adapters house list
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getActivity(), getString(R.string.on_cancel_search_page_error), Toast.LENGTH_SHORT).show();
            }
        });

        //search view filter
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                adapter.getFilter().filter(query); //gets filter as defined in CustomFilter java class
                return false;
            }
        });

        return view;
    }// end of oncreate
}
