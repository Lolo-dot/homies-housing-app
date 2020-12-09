package humber.college.homies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements Filterable{

    Context c;
    ArrayList<House> houses,filterList;
    CustomFilter filter;
    //PriceFilter priceFilter;

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

        if (imgName.contains("@drawable")) {
            int image = c.getApplicationContext().getResources().getIdentifier(imgName, null, c.getApplicationContext().getPackageName());
            holder.img.setImageResource(image);
        }else{
            holder.img.setImageBitmap(string_toImage(imgName));
            //Toast.makeText(c,"bitmap set!", Toast.LENGTH_SHORT).show();
        }
        //binding data to views
        holder.posTxt.setText(c.getString(R.string.money_sign)+houses.get(position).getPos());
        holder.nameTxt.setText(houses.get(position).getName());
        //holder.img.setImageResource(image);
        //holder.img.setImageBitmap();

        //chcking bool bookmark value set as bookmarked_house or unbookmarked_hosue image
            if(houses.get(position).getBookmarked()) {
                holder.bookmarkbutton.setImageResource(R.drawable.bookmarked_house);
            }else {
                holder.bookmarkbutton.setImageResource(R.drawable.add_houses);
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
                        //Intent intent = new Intent(c.getApplicationContext(), MainActivity.class);
                        //intent.putExtra("openFragment","MessageFrag");
                        //c.startActivity(intent);
                        BottomNavigationView bottomNavigationView = (BottomNavigationView) ((AppCompatActivity)c).findViewById(R.id.bottom_bar);
                        bottomNavigationView.setSelectedItemId(R.id.m);
                        final Fragment messageFrag = new Message_page();
                        ((AppCompatActivity)c).getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                                .replace(R.id.fragmentContent, messageFrag, null).addToBackStack(null).commit();
                        break;
                    case R.id.bookmarkButton://setting bookmark true or not, with database writes and reads

                        if(houses.get(pos).getBookmarked()) {
                            //if currently true, set to false set image as unbookmarked
                            refBookmarkedHouses.child("Houses").child(houses.get(pos).getName()).child("bookmarked").setValue(false);
                            holder.bookmarkbutton.setImageResource(R.drawable.add_houses);
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

    public Bitmap string_toImage(String img) {
        String default_img = "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";
        Bitmap error_img;
        try {
            byte[] b = Base64.decode(img, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            return bitmap;
        } catch (Exception e) {
            byte[] b = Base64.decode(default_img, Base64.DEFAULT);
            error_img = BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return error_img;
    }

}//end of code
