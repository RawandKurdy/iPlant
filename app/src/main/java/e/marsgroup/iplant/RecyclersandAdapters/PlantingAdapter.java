package e.marsgroup.iplant.RecyclersandAdapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import e.marsgroup.iplant.R;
import e.marsgroup.iplant.plantpage;

/**
 * Created by rawan on 3/17/2018.
 */


public class PlantingAdapter extends RecyclerView.Adapter<PlantingAdapter.Viewholder> {

    private ArrayList<Planting> data;
    private Planting model;
    private Context context;

    public PlantingAdapter(ArrayList<Planting> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        // Views here

        public ImageView imageInProgess;
        public TextView titleofProgress;
        public Button infoofplantinProgess;
        public ProgressBar progressBar;


        public Viewholder(View itemView) {
            super(itemView);

            // Defining Views here

            imageInProgess = (ImageView)itemView.findViewById(R.id.imageInProgess);
            titleofProgress = (TextView)itemView.findViewById(R.id.titleofProgress);
            infoofplantinProgess = (Button)itemView.findViewById(R.id.infoofplantinProgess);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progressBar);

        }
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plantingprogess, parent, false);

        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

        model = data.get(position);
        // setting the data to the Views here
        final int id= model.getPlanted().getIdofPlant();
        holder.progressBar.setProgress(100); //to have a filled progress bar so here we alert via Colors

        long currenttime=System.currentTimeMillis()/1000; //gets the current time

        //Debugging
        Log.d("d","time"+currenttime);

        if(model.getPlanted().getStartEpochTime()<currenttime)
        holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED)); //Still Not finished so progressbar becomes RED
        if(model.getPlanted().getEstimatedEndEpochTime()<currenttime)
        holder.progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));//Finished so progress bar becomes GREEN

        //Debugging
        Log.d("d","start"+model.getPlanted().getStartEpochTime());
        Log.d("d","end"+model.getPlanted().getEstimatedEndEpochTime());

        holder.titleofProgress.setText(model.getTitleofProgress());
        Picasso.get().load(model.getImageInProgess()).placeholder(R.drawable.progressplant)
                .error(R.drawable.progressplant).into(holder.imageInProgess); //loads image if internet was available or else load placeholder

        holder.infoofplantinProgess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //When clicks on info button in Progress Fragment on one of the progress items
                //Here we only send the id of the plant
                //that we are planting tot the show plant page to show its info in the plant page activity
                Intent infoIntent=new Intent(v.getContext(),plantpage.class);
                infoIntent.putExtra("ID",String.valueOf(id));
                v.getContext().startActivity(infoIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
