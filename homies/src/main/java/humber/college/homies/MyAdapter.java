package humber.college.homies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements Filterable{

    Context c;
    ArrayList<House> houses,filterList;
    CustomFilter filter;

    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refBookmarkedHouses = database.getReference();

    public MyAdapter(Context ctx, ArrayList<House> houses)
    {
        this.c=ctx;//context
        this.houses=houses;
        this.filterList=houses;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //model layout to view
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,null);
        MyHolder holder=new MyHolder(v);//view holder
        return holder;
    }

    //binding data and button functionality
    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {

        //getting image from object from @drawable/california bungalow to a resource id (R.id.california_bungalow = 140481 or smth)
        String imgName = houses.get(position).getImg();
        int image = c.getApplicationContext().getResources().getIdentifier(imgName, null, c.getApplicationContext().getPackageName());

        //binding data to views
        holder.posTxt.setText(c.getString(R.string.money_sign)+houses.get(position).getPos());
        holder.nameTxt.setText(houses.get(position).getName());
        holder.img.setImageResource(image);

        //chcking bool bookmark value set as bookmarked_house or unbookmarked_hosue image
            if(houses.get(position).getBookmarked()) {
                holder.bookmarkbutton.setImageResource(R.drawable.bookmarked_house);
            }else {
                holder.bookmarkbutton.setImageResource(R.drawable.unbookmarked_house);
            }

        //click listener from custom ItemCLickListener
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                switch (v.getId()) {
                    case R.id.contactButton://saving email and phone to go to messages page
                        savePreferences("phone_number",houses.get(pos).getPhone());
                        //Toast.makeText(c, houses.get(pos).getName()+"'s Number: "+houses.get(pos).getPhone(), Toast.LENGTH_SHORT).show();
                        //add intent to go to messages here*************
                        Intent intent = new Intent(c.getApplicationContext(), Message_page.class);
                        c.startActivity(intent);
                        break;
                    case R.id.bookmarkButton://setting bookmark true or not, with database writes and reads

                        if(houses.get(pos).getBookmarked()) {
                            //if currently true, set to false set image as unbookmarked
                            refBookmarkedHouses.child("Houses").child(houses.get(pos).getName()).child("bookmarked").setValue(false);
                            holder.bookmarkbutton.setImageResource(R.drawable.unbookmarked_house);
                            //then remove from bookmarks
                            refBookmarkedHouses.child("Bookmarked Houses").child(houses.get(pos).getName()).removeValue();

                        }else {
                            //if currently false, set to true and set image as bookmarked
                            refBookmarkedHouses.child("Houses").child(houses.get(pos).getName()).child("bookmarked").setValue(true);
                            holder.bookmarkbutton.setImageResource(R.drawable.bookmarked_house);
                            //then add to bookmarks
                            House p = new House(houses.get(pos).getName(), houses.get(pos).getPos(),houses.get(pos).getImg(),houses.get(pos).getPhone(),true);
                            refBookmarkedHouses.child("Bookmarked Houses").child(houses.get(pos).getName()).setValue(p);

                        }
                        break;
                } //end of switch statement
            }

        });//end of item click listener aka button functionality

    }//end of onBindViewHolder aka data binding


    //save preferences function for saving phone + email
    private void savePreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //get size of list
    @Override
    public int getItemCount() {
        return houses.size();
    }

    //return filter object
    @Override
    public Filter getFilter() {
        if(filter==null) {
            filter=new CustomFilter(filterList,this);
        }
        return filter;
    }

}//end of code
