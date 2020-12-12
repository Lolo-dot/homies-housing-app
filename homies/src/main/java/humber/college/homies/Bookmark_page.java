package humber.college.homies;
//Team Name: Homies
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
                if(sv.getQuery().toString()==null||sv.getQuery().toString().equals("")) {
                switch (parentView.getItemAtPosition(position).toString()) {
                    case "Sort":
                        break;
                    case "Price:Low to High":
                            Collections.sort(houseList, House.PriceLowTooHigh);
                            adapter.notifyDataSetChanged();
                        break;
                    case "Price:High to Low":
                        Collections.sort(houseList, House.PriceHighToLow);
                        adapter.notifyDataSetChanged();
                        break;
                    case "Alphabetical:A-Z":
                        Collections.sort(houseList, House.NameAToZ);
                        adapter.notifyDataSetChanged();
                        break;
                    case "Alphabetical:Z-A":
                        Collections.sort(houseList, House.NameZToA);
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
        sv = (SearchView) view.findViewById(R.id.bookSearchView);
        rv = (RecyclerView) view.findViewById(R.id.myRecyclerBookMark);

        //setting rv properties
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));// should be bookmark or this isntead of activity?
        rv.setItemAnimator(new DefaultItemAnimator());

        //setting recycler views adapter ( of type MyAdapter, which is custom made)
        rv.setAdapter(adapter);


        final DatabaseReference refUserName = FirebaseDatabase.getInstance().getReference().child("USER").child(username);//reference to specificshared pref username
        final DatabaseReference refUseBookmarkedrHouses = FirebaseDatabase.getInstance().getReference().child("USER").child(username).child("userBookmarkedHouses");
        //getting bookmarked houses database and adding to arraylist
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
    }
}