package e.marsgroup.iplant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import e.marsgroup.iplant.Database.databaseHelper;
import e.marsgroup.iplant.Database.plant;

public class plantpage extends AppCompatActivity {

    databaseHelper db;
    TextView longtxt;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantpage);
        //This activity is fine can be portrait or landscape

        longtxt=findViewById(R.id.longtxt);
        img=findViewById(R.id.imageinPlantPage);
        db=new databaseHelper(getApplicationContext());

        Intent get=getIntent();
        int id=Integer.parseInt(get.getStringExtra("ID"));

        plant TMP=db.getPlant(id);

        //closing db
        db.closeDB();

        setTitle(TMP.getName());
        Picasso.get().load(TMP.getImgurl()).into(img); //loads the image behind the scrolling text in the top

        longtxt.setText(TMP.getInfo());//addddds a very long text below the title with scrolling feature $ WOW!
        final String moreinfourl=TMP.getMoreinfoURL(); //sets the more info URL

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.moreinfoButton); //Adds the more info button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //An Intent to handle the URL in the URi that we are passing to the system via Intent
                //Then your browser will handle the link if you have a browser installed
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(moreinfourl));
                startActivity(i);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //Doing projects isn't fun really :(
    //When you don't have enough motivation
    //21318345A
    //time==0345AM Mind blowing? tbh No!
}
