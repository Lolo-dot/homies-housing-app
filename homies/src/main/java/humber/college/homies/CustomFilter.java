package humber.college.homies;

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter {

    MyAdapter adapter;
    ArrayList<House> filterList;

    //get arraylsit to filter
    public CustomFilter(ArrayList<House> filterList,MyAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;
    }

    //filter work
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //check if text(constraint) is zero
        if(constraint != null && constraint.length() > 0) {
            constraint=constraint.toString().toUpperCase(); //make upeprcase for fitlering
            ArrayList<House> filteredHouses=new ArrayList<>();

            for (int i=0;i<filterList.size();i++) {
                //check if matches/ if filter list item has text in searchbox (constraint)
                if(filterList.get(i).getName().toUpperCase().contains(constraint)) {
                    //add homes(from filter list) to filteredhouses list
                    filteredHouses.add(filterList.get(i));
                }
            }
            results.count=filteredHouses.size();//matching size of list
            results.values=filteredHouses;//matching values
        }else {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }//end of actual fitler work

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.houses= (ArrayList<House>) results.values;
        //refresh adapter with new list values
        adapter.notifyDataSetChanged();
    }
}