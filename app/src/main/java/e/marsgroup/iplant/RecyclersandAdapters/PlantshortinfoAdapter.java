package e.marsgroup.iplant.RecyclersandAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import e.marsgroup.iplant.Database.databaseHelper;
import e.marsgroup.iplant.Database.planted;
import e.marsgroup.iplant.R;
import e.marsgroup.iplant.Database.epochtoDate;
import e.marsgroup.iplant.plantpage;

/**
 * Created by rawan on 3/17/2018.
 */


public class PlantshortinfoAdapter extends RecyclerView.Adapter<PlantshortinfoAdapter.Viewholder> {

    private ArrayList<Plantshortinfo> data;
    private Plantshortinfo model;
    private Context context;
    private databaseHelper db;

    public PlantshortinfoAdapter(ArrayList<Plantshortinfo> data, Context context) {
        this.data = data;
        this.context = context;
        db=new databaseHelper(context);
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        // 1. Declare your Views here

        public ImageView image;
      //  public Space top_space;
        public TextView title;
        public TextView brieftxt;
        public Button addplant;
        public Button infoofplant;


        public Viewholder(View itemView) {
            super(itemView);

            // 2. Define your Views here

            image = (ImageView)itemView.findViewById(R.id.imagePlantItem);
           // top_space = (Space)itemView.findViewById(R.id.top_space);
            title = (TextView)itemView.findViewById(R.id.titleofPlantItem);
            brieftxt = (TextView)itemView.findViewById(R.id.brieftxtofPlantItem);
            addplant = (Button)itemView.findViewById(R.id.addplantInPlantItem);
            infoofplant = (Button)itemView.findViewById(R.id.infoofplantInPlantItem);

        }
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plant_recycleritem, parent, false);

        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, final int position) {

        model = data.get(position);
        holder.title.setText(model.getTitle());
        holder.brieftxt.setText(model.getBrieftxt());
        String urlofimg=model.getPlant().getImgurl();
        int tmpimg=Integer.parseInt(model.getImage()); //tmp image
        final String id=model.getAddplant(); //return id same as showplant();
        final long time_needed=model.getPlant().getEstimatedEpochSeconds(); //time for plantation


        Picasso.get().load(urlofimg).placeholder(tmpimg).error(tmpimg).into(holder.image); //loads image if internet was available

        holder.addplant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //When clicking on this button we create a new Object of Planted
                planted tmp=new planted();
                tmp.setIdofPlant(Integer.valueOf(id));

                //Divide by 1000 to get seconds instead of millis
                long current_time=System.currentTimeMillis()/1000; //Current system time from the system
                tmp.setStartEpochTime(current_time); //same as time when he clicks
                tmp.setEstimatedEndEpochTime(current_time+time_needed);
                //time_needed plus current time = the time we need till our plant is ready (planted)


                long id_planted=db.createPlanted(tmp);

                //debugging
                Log.d("d","Planted: "+id_planted+": "+tmp.getStartEpochTime()+" , "+tmp.getEstimatedEndEpochTime() +" ("+time_needed+")");
                Log.d("d","Planted add date"+ epochtoDate.doJob(tmp.getStartEpochTime()));

                //Notify the user that it was added successfully
                Toast.makeText(v.getContext(), "Successfully added !", Toast.LENGTH_SHORT).show();
            }
        });
        holder.infoofplant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Passing the ID of the Plant to the plant page activity to show its details
                Intent infoIntent=new Intent(v.getContext(),plantpage.class);
                infoIntent.putExtra("ID",id);
                v.getContext().startActivity(infoIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}

