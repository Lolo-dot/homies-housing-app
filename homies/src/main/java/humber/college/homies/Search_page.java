package humber.college.homies;
//Team Name: Homies
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
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
    String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);

        adapter = new MyAdapter(getContext(), housesList);

        //shared pref delcarations
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getString(R.string.number_key_pref)).apply(); //removing old shared pref of phone number/email
        username = sharedPreferences.getString("usernameStorage","failed");

        //sort spinner adpater and functionality
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
                if(sv.getQuery().toString()==null||sv.getQuery().toString().equals("")) {
                    switch (parentView.getItemAtPosition(position).toString()) {
                        case "Sort":
                            break;
                        case "Price:Low to High":
                            Collections.sort(housesList, House.PriceLowTooHigh);
                            adapter.notifyDataSetChanged();
                            break;
                        case "Price:High to Low":
                            Collections.sort(housesList, House.PriceHighToLow);
                            adapter.notifyDataSetChanged();
                            break;
                        case "Alphabetical:A-Z":
                            Collections.sort(housesList, House.NameAToZ);
                            adapter.notifyDataSetChanged();
                            break;
                        case "Alphabetical:Z-A":
                            Collections.sort(housesList, House.NameZToA);
                            adapter.notifyDataSetChanged();
                            break;
                        default:
                            break;
                    }//end of switch
                } else{
                    spinner.setSelection(0);
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.constraintLayout), getString(R.string.emptysearchbar), Snackbar.LENGTH_LONG);snackbar.show();
                }
            }//end of onitme selected

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
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

        //getting master "Houses" list to assign to "userHouses" array list
        final DatabaseReference refUserName = FirebaseDatabase.getInstance().getReference().child("USER").child(username);//reference to specificshared pref username
        DatabaseReference refHouses = database.getReference(getString(R.string.database_ref_search_gae)); //reference to master "Houses"

        ChildEventListener masterList = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                House p = snapshot.getValue(House.class);
                refUserName.child("userHouses").child(p.getName()).child("img").setValue(p.getImg());
                refUserName.child("userHouses").child(p.getName()).child("name").setValue(p.getName());
                refUserName.child("userHouses").child(p.getName()).child("phone").setValue(p.getPhone());
                refUserName.child("userHouses").child(p.getName()).child("pos").setValue(p.getPos());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                House p = snapshot.getValue(House.class);
                refUserName.child("userHouses").child(p.getName()).child("img").setValue(p.getImg());
                refUserName.child("userHouses").child(p.getName()).child("name").setValue(p.getName());
                refUserName.child("userHouses").child(p.getName()).child("phone").setValue(p.getPhone());
                refUserName.child("userHouses").child(p.getName()).child("pos").setValue(p.getPos());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                House p = snapshot.getValue(House.class);
                refUserName.child("userHouses").child(p.getName()).removeValue();
                refUserName.child("userBookmarkedHouses").child(p.getName()).removeValue();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        refHouses.addChildEventListener(masterList);

        //databse for houses catalog. updates houselist and refreshes adapter using hosuelist
        final DatabaseReference refUserNameuserHouses = FirebaseDatabase.getInstance().getReference().child("USER").child(username).child("userHouses");
        refUserNameuserHouses.addValueEventListener(new ValueEventListener() {

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
