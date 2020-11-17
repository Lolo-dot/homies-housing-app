package humber.college.homies;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //OUR VIEWS
    ImageView img;
    TextView nameTxt,posTxt;
    Button contactbutton;
    ImageButton bookmarkbutton;

    ItemClickListener itemClickListener;



    public MyHolder(View itemView) {
        super(itemView);

        this.img= (ImageView) itemView.findViewById(R.id.playerImage);
        this.nameTxt= (TextView) itemView.findViewById(R.id.nameTxt);
        this.posTxt= (TextView) itemView.findViewById(R.id.posTxt);
        this.bookmarkbutton=(ImageButton) itemView.findViewById(R.id.bookmarkButton);
        this.contactbutton=(Button) itemView.findViewById(R.id.contactButton);

        //itemView.setOnClickListener(this);
        bookmarkbutton.setOnClickListener(this);
        contactbutton.setOnClickListener(this);
    }
/*
    public void OnClick(View v){
        OnClickBookmark.itemClickListener.onBookmarkClick(v,getLayoutPosition());
    }

    public void OnClickContact(View v){
        OnClickContact.itemClickListener.onContactClick(v,getLayoutPosition());
    }
*/
    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());

    }

    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener=ic;
    }
}
