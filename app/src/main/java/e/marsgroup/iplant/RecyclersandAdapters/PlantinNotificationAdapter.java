package e.marsgroup.iplant.RecyclersandAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import e.marsgroup.iplant.R;

/**
 * Created by rawan on 3/20/2018.
 */


public class PlantinNotificationAdapter extends RecyclerView.Adapter<PlantinNotificationAdapter.Viewholder> {

    private ArrayList<PlantinNotification> data;
    private PlantinNotification model;
    private Context context;

    public PlantinNotificationAdapter(ArrayList<PlantinNotification> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        //Views here

        public ImageView imageInPlantNotification;
        public TextView titleofPlantinNotification;
        public TextView waterinfoInNotification;
        public TextView endDate;
        public TextView startDate;


        public Viewholder(View itemView) {
            super(itemView);

            // Initialization of Views here

            imageInPlantNotification = (ImageView)itemView.findViewById(R.id.imageInPlantNotification);
            titleofPlantinNotification = (TextView)itemView.findViewById(R.id.titleofPlantinNotification);
            waterinfoInNotification = (TextView)itemView.findViewById(R.id.waterinfoInNotification);
            endDate = (TextView)itemView.findViewById(R.id.endDate);
            startDate = (TextView)itemView.findViewById(R.id.startDate);

        }
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plantedprogressnotification, parent, false);

        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

        model = data.get(position);

        // setting data to the Views
        Picasso.get().load(model.getImageInPlantNotification()).error(R.drawable.progressplant)
                .placeholder(R.drawable.progressplant).into(holder.imageInPlantNotification);//setting the Image
        holder.titleofPlantinNotification.setText(model.getTitleofPlantinNotification()); //TITLE
        holder.startDate.setText(model.getStartDate());
        holder.endDate.setText(model.getEndDate());
        holder.waterinfoInNotification.setText(model.getWaterinfoInNotification());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}

