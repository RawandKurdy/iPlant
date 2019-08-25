package e.marsgroup.iplant.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import e.marsgroup.iplant.R;

/**
 * Created by rawan on 12/10/2017.
 */

public class customframe extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater li , ViewGroup vg, Bundle bd) {

        view=li.inflate(R.layout.custom,vg,false);
        //NO LONGER DEVELOPED *GOT PERMISSION FROM Project Manager
        //LEFT CODE FOR FUTURE DEVELOPMENT IF NEEDED
       // view=li.inflate(R.layout.category,vg,false);
        return view;}
}
