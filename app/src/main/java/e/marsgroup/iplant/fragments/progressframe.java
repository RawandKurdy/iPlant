package e.marsgroup.iplant.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import e.marsgroup.iplant.Database.databaseHelper;
import e.marsgroup.iplant.Database.plant;
import e.marsgroup.iplant.Database.planted;
import e.marsgroup.iplant.R;
import e.marsgroup.iplant.RecyclersandAdapters.Planting;
import e.marsgroup.iplant.RecyclersandAdapters.PlantingAdapter;
import e.marsgroup.iplant.TrackPlantedActivity;

/**
 * Created by rawan on 12/10/2017.
 */

public class progressframe extends Fragment {
    View view;
    RecyclerView recyclerView;
    databaseHelper db;
    FloatingActionButton floatingActionButton;
    @Override
    public View onCreateView(LayoutInflater li , ViewGroup vg, Bundle bd) {

        view=li.inflate(R.layout.progress,vg,false);
        db=new databaseHelper(getContext());
        floatingActionButton=view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), TrackPlantedActivity.class));
            }
        });

        List<planted> plantedList=db.getAllPlanteds();

        //Debugging Purposes
        Log.d("planted","size is "+plantedList.size());

        ArrayList<Planting> list =generateArrayListfromDB(plantedList);

        RecyclerView recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view_progress);


        //RecyclerView to look like a vertical list view
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));

        //RecyclerView to look like a grid view
        //recycler_view.setLayoutManager(new GridLayoutManager(this, 2)); //NOT USED HERE

        PlantingAdapter adapter = new PlantingAdapter(list, getContext());
        recycler_view.setAdapter(adapter);
        return view;}

    public ArrayList generateArrayListfromDB(List<planted> p) {
        ArrayList<Planting> returnArray = new ArrayList<>();
         int x=1;
        for (planted planted : p) {
            Planting tmp = new Planting();

            //tmp2
            plant tmp2=db.getPlant(planted.getIdofPlant()); //NEW Object to work on
            tmp.setPlanted(planted); //Grabs the plant Object with itself ,contains a lot of useful Data
            tmp.setImageInProgess(tmp2.getImgurl()); //URL of Image to load if internet was available
            tmp.setTitleofProgress(tmp2.getName()+" ["+x+"]");
            returnArray.add(tmp);//Add our object to the list
            x++; //just a number I added to differentiate plants in progress ,in case you had multiple of the same plant
        }
        return  returnArray;
    }



    }
