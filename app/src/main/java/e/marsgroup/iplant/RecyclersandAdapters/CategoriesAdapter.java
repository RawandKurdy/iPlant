package e.marsgroup.iplant.RecyclersandAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import e.marsgroup.iplant.R;

/**
 * Created by rawan on 3/19/2018.
 */


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.Viewholder> {

    private ArrayList<Categories> data;
    private Categories model;
    private Context context;

    public CategoriesAdapter(ArrayList<Categories> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        // Declaration of Views here

        public ImageView image_category;
        public TextView titleofcat;
        public TextView brieftxtofcat;
        public Button showcat;


        public Viewholder(View itemView) {
            super(itemView);

            // Defining Views here

            image_category = (ImageView)itemView.findViewById(R.id.image_category);
            titleofcat = (TextView)itemView.findViewById(R.id.titleofcat);
            brieftxtofcat = (TextView)itemView.findViewById(R.id.brieftxtofcat);
            showcat = (Button)itemView.findViewById(R.id.showcat);

        }
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plant_category, parent, false);

        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, final int position) {

        //Setting the Recycler Item properties
        model = data.get(position);
        holder.titleofcat.setText(model.getTitleofcat());
        holder.brieftxtofcat.setText(model.getBrieftxtofcat());
        Picasso.get().load(Integer.valueOf(model.getImage_category())).into(holder.image_category); //Setting Image via PICASSO Lib.

        final String showcat=model.getShowcat(); //category identifier
        final String tmpimgid=model.getImage_category(); //image of category

        //When user clicks oN SHOW button in Category Recycler
        holder.showcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewItems=new Intent(v.getContext(), e.marsgroup.iplant.viewItems.class);
                viewItems.putExtra("season",showcat);
                viewItems.putExtra("tmpimgID",tmpimgid);
                v.getContext().startActivity(viewItems);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
