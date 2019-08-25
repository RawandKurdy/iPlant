package e.marsgroup.iplant;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import e.marsgroup.iplant.Database.databaseHelper;
import e.marsgroup.iplant.Database.plant;
import e.marsgroup.iplant.RecyclersandAdapters.Plantshortinfo;
import e.marsgroup.iplant.RecyclersandAdapters.PlantshortinfoAdapter;

public class viewItems extends AppCompatActivity {
    databaseHelper db;
    RecyclerView recycler_view;
    String imgTmp;
    String season;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewitems);

        db=new databaseHelper(getApplicationContext());

        //Lets Set Orientation Lock cuz our activity is meant to be portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        Intent get=getIntent(); //getting our intent
        season=get.getStringExtra("season"); //season yh season
        imgTmp=get.getStringExtra("tmpimgID"); //Set TMP image if internet was not available

        List<plant> tmp=db.getAllPlantsBySeason(season); //GETS PLANTS FROM DB

        setTitle(season.toUpperCase() +" PLANTS");

        //dEBugging
        Log.d("d", season+" plants Count: " + tmp.size());

        ArrayList<Plantshortinfo> list=generateArrayListfromDB(tmp); //prepares the food (resources) for our activity to start doing its job

        recycler_view=findViewById(R.id.recycler_view_items);

        //RecyclerView  look like a vertical list view
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        //RecyclerView  look like a grid view
        // recycler_view.setLayoutManager(new GridLayoutManager(this, 2)); //this one also looks nice! for real i am not joking

        PlantshortinfoAdapter adapter=new PlantshortinfoAdapter(list,getApplicationContext());
        recycler_view.setAdapter(adapter);

        //lets close db here too so it won't leak
        db.closeDB();

    }

    //Generates the special Array List thats needed by our application
    public ArrayList generateArrayListfromDB(List<plant> p){
        ArrayList<Plantshortinfo> returnArray=new ArrayList<>();

        for(plant plant:p){
            Plantshortinfo tmp=new Plantshortinfo(); //Object we neeeed
            tmp.setPlant(plant); //setting plant object to Plants short info ,so it nearly holds all the info inside tooooo
            tmp.setImage(imgTmp); //Image
            tmp.setTitle(plant.getName()); //image
            tmp.setBrieftxt("A "+season+" plant :)"); //brief info
            tmp.setAddplant(String.valueOf(plant.getId())); //Sets the ID to this for button add
            tmp.setInfoofplant(String.valueOf(plant.getId())); //Sets the ID to this for button InfO

            returnArray.add(tmp);//adds it to the arrayList
        }

    return returnArray;} //And Finally here ,it returns the Array
}
