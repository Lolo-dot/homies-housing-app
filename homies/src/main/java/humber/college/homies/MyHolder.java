package humber.college.homies;
//Team Name: Homies
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView img;
    TextView nameTxt,posTxt;
    Button contactbutton;
    ImageButton bookmarkbutton;

    ItemClickListener itemClickListener;

    //holder with declaring values
    public MyHolder(View itemView) {
        super(itemView);

        this.img= (ImageView) itemView.findViewById(R.id.houseImage);
        this.nameTxt= (TextView) itemView.findViewById(R.id.nameTxt);
        this.posTxt= (TextView) itemView.findViewById(R.id.posTxt);
        this.bookmarkbutton=(ImageButton) itemView.findViewById(R.id.bookmarkButton);
        this.contactbutton=(Button) itemView.findViewById(R.id.contactButton);

        //itemView.setOnClickListener(this); this is for the entire lsit item. might use in future to blowup more details about the house
        bookmarkbutton.setOnClickListener(this);
        contactbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());
    }
    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener=ic;
    }
}
