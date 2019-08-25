package e.marsgroup.iplant.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DigitalClock;
import android.widget.ImageView;


import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import e.marsgroup.iplant.HomeScreen;

import e.marsgroup.iplant.R;
import e.marsgroup.iplant.Database.*;
import e.marsgroup.iplant.plantpage;

/**
 * Created by rawan on 12/10/2017.
 */

public class homeframe extends Fragment {
    View viewhome;
    databaseHelper db;
     //Sliding Cards
     SliderLayout sliderShow ;
    @Override
    public View onCreateView(LayoutInflater li , ViewGroup vg, Bundle bd) {
        viewhome=li.inflate(R.layout.home,vg,false);
        db=new databaseHelper(getContext());
        sliderShow=(SliderLayout) viewhome.findViewById(R.id.slider);


        //No of Slide u want to have
        int num_of_pages = 5;

        List<plant> random=db.getPlantsRandomly(num_of_pages); //if life was this easy seeing duplicates ,we would have lived a different life
        //lets close db here tooo
        db.closeDB();



        for (plant a:random
             ) {
            TextSliderView textSliderView = new TextSliderView(getContext()); //Our Slider ITEM
            plant tmp=a;
            final String id=String.valueOf(tmp.getId());
            textSliderView.setPicasso(Picasso.get());
            textSliderView.description(tmp.getName()).image(tmp.getImgurl());
            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    //Passing the ID of the Plant to the plant page activity to show its details
                    Intent infoIntent=new Intent(getContext(),plantpage.class);
                    infoIntent.putExtra("ID",id);
                    startActivity(infoIntent);
                }
            });
            sliderShow.addSlider(textSliderView); //And Finally adds the Slider Item to Slider

        }





    return viewhome;}

    //to stop leak
    @Override
    public void onStop() {
        sliderShow.stopAutoCycle();
        //sliderShow.removeAllSliders();
        super.onStop();
    }
}
