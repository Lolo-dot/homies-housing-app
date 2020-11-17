package humber.college.homies;

import android.content.Context;
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
    ArrayList<Player> players,filterList;
    CustomFilter filter;

    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference refBookmarkedHouses = database.getReference();


    public MyAdapter(Context ctx, ArrayList<Player> players)
    {
        this.c=ctx;
        this.players=players;
        this.filterList=players;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //CONVERT XML TO VIEW ONBJ
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,null);

        //HOLDER
        MyHolder holder=new MyHolder(v);

        return holder;
    }

    //DATA BOUND TO VIEWS
    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {

        //BIND DATA
        String imgName = players.get(position).getImg();
        int image = c.getApplicationContext().getResources().getIdentifier(imgName, null, c.getApplicationContext().getPackageName());

        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>" +imgName+"<<<<<<<<<<<<<<<<<<<<<<<<");
        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>" +image+"<<<<<<<<<<<<<<<<<<<<<<<<");


        holder.posTxt.setText(players.get(position).getPos());
        holder.nameTxt.setText(players.get(position).getName());
        holder.img.setImageResource(image);


            if(players.get(position).getBookmarked()) {
                holder.bookmarkbutton.setImageResource(R.drawable.bookmarked_house);
            }else {
                holder.bookmarkbutton.setImageResource(R.drawable.unbookmarked_house);
            }




        //IMPLEMENT CLICK LISTENET
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                switch (v.getId()) {
                    case R.id.contactButton:
                        Toast.makeText(c, players.get(pos).getName()+"'s Number: "+players.get(pos).getPhone(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.bookmarkButton:
                        if(players.get(pos).getBookmarked()) {
                            //if currently true, set to false and un bookmark
                            refBookmarkedHouses.child("players").child(players.get(pos).getName()).child("bookmarked").setValue(false);
                            holder.bookmarkbutton.setImageResource(R.drawable.unbookmarked_house);
                            //remove from bookmarks
                            refBookmarkedHouses.child("Bookmarked Houses").child(players.get(pos).getName()).removeValue();
                        }else {
                            //if currently false, set to true and bookmark
                            refBookmarkedHouses.child("players").child(players.get(pos).getName()).child("bookmarked").setValue(true);
                            holder.bookmarkbutton.setImageResource(R.drawable.bookmarked_house);

                            Player p = new Player(players.get(pos).getName(), players.get(pos).getPos(),players.get(pos).getImg(),players.get(pos).getPhone(),players.get(pos).getBookmarked());
                            refBookmarkedHouses.child("Bookmarked Houses").child(players.get(pos).getName()).setValue(p);

                        }
                        //Toast.makeText(c, "bookmark", Toast.LENGTH_SHORT).show();
                        break;

                    // Snackbar.make(v,players.get(pos).getPhone(),Snackbar.LENGTH_SHORT).show();
                    //savePreferences("Player Name",players.get(pos).getPhone());

                    //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>" +objName+"<<<<<<<<<<<<<<<<<<<<<<<<");
                    //Player p = new Player(players.get(pos).getName(), players.get(pos).getPos(),players.get(pos).getImg(),players.get(pos).getPhone());
                    //Player pnew = new Player ("Anderson", "Striker","@drawable/herera","696 123 4567");

                    //refBookmarkedHouses.child("Bookmarked Houses").child(players.get(pos).getName()).setValue(p);

                    //savetoDB();
                    //refBookmarkedHouses.child("alanisawesome").setValueAsync(new User("June 23, 1912", "Alan Turing"));

                    //add go to messages intent,
                }
            }

        });
    }
    /*public void savetoDB(){

        //refBookmarkedHouses.setValue(object

        Player pnew = new Player ("Anderson", "Striker","@drawable/herera","696 123 4567");
        refBookmarkedHouses.child("Bookmarked Houses").child("Messi").setValue(pnew);
     /*   refBookmarkedHouses.child("Bookmarked Houses").child("Messi").setValue(pnew, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference reference) {
                if (databaseError != null) {
                    Log.e("TAG", "Failed to write message", databaseError.toException());
                }
            }
        });
    }*/

    private void savePreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //GET TOTAL NUM OF PLAYERS
    @Override
    public int getItemCount() {
        return players.size();
    }

    //RETURN FILTER OBJ
    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(filterList,this);
        }

        return filter;
    }
}
