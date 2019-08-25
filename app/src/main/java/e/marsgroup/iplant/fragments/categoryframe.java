package e.marsgroup.iplant.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import e.marsgroup.iplant.Database.databaseHelper;
import e.marsgroup.iplant.Database.plant;
import e.marsgroup.iplant.R;
import e.marsgroup.iplant.RecyclersandAdapters.Categories;
import e.marsgroup.iplant.RecyclersandAdapters.CategoriesAdapter;

/**
 * Created by rawan on 12/10/2017.
 */

public class categoryframe extends Fragment {
    View view;
    RecyclerView recycler_view;

    @Override
    public View onCreateView(LayoutInflater li , ViewGroup vg, Bundle bd) {

        view=li.inflate(R.layout.category,vg,false);

        ArrayList<Categories> list = returnSeasons();

         recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);

        //RecyclerView to look like a vertical list view
       recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));

        //RecyclerView to look like a grid view
      //recycler_view.setLayoutManager(new GridLayoutManager(getContext(), 2)); //NOT USED In my app ,Left it here just show possibility

        CategoriesAdapter adapter = new CategoriesAdapter(list, getContext());
        recycler_view.setAdapter(adapter);

    return view;}

    public ArrayList returnSeasons(){
        ArrayList a=new ArrayList();

        Categories spring = new Categories();
        spring.setTitleofcat("SPRING");
        spring.setBrieftxtofcat("All spring plants are here");
        spring.setShowcat("spring"); //key that I will return to DB later to get the plants
        spring.setImage_category(String.valueOf(R.drawable.spring));
        a.add(spring);


        Categories summer = new Categories();
        summer.setTitleofcat("SUMMER");
        summer.setBrieftxtofcat("All summer plants are here");
        summer.setShowcat("summer");
        summer.setImage_category(String.valueOf(R.drawable.summer));
        a.add(summer);

        Categories fall = new Categories();
        fall.setTitleofcat("FALL");
        fall.setBrieftxtofcat("All fall plants are here");
        fall.setShowcat("fall");
        fall.setImage_category(String.valueOf(R.drawable.fall));
        a.add(fall);

        Categories winter = new Categories();
        winter.setTitleofcat("WINTER");
        winter.setBrieftxtofcat("All winter plants are here");
        winter.setShowcat("winter");
        winter.setImage_category(String.valueOf(R.drawable.winter));
        a.add(winter);

    return a;}
}
