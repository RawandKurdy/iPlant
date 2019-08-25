package e.marsgroup.iplant.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ajts.androidmads.sqliteimpex.SQLiteImporterExporter;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsFragment;

import e.marsgroup.iplant.Database.databaseHelper;
import e.marsgroup.iplant.R;

/**
 * Created by rawan on 12/10/2017.
 */

public class settingsframe extends Fragment {
    View view;
    databaseHelper db;
    Button exportdb,importdb;
    SQLiteImporterExporter sqLiteImporterExporter;

    //for backup and export
    String path ;


    //Storage Permission Request Code for Write $ Read Access
    private int STORAGE_PERMISSION_CODE=23;

    @Override
    public View onCreateView(LayoutInflater li , ViewGroup vg, Bundle bd) {
        path=Environment.getExternalStorageDirectory().getAbsolutePath() + "/"; //gets the path to save the db dump  to there
        db=new databaseHelper(getContext()); // a new object of our db

        view=li.inflate(R.layout.settings,vg,false);
        FragmentManager fm=getFragmentManager(); //USING THOSE FRAGMENTS THINGS TO SHOW OUR ABOUT FRAGMENT IN THIS PAGE
        FragmentTransaction fmt=fm.beginTransaction();

        exportdb=view.findViewById(R.id.exportdb);
        importdb=view.findViewById(R.id.importdb);

        //About page with Libraries Fragment getter
        LibsFragment fragment = new LibsBuilder().fragment();  //get the fragment


        String dbname=db.getDatabaseName();

        //closing the db so it wont leak
        db.closeDB();

        //backup and export
        sqLiteImporterExporter= new SQLiteImporterExporter(getContext(), dbname);

         //Listeners for Import and Export DB
        sqLiteImporterExporter.setOnImportListener(new SQLiteImporterExporter.ImportListener() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        sqLiteImporterExporter.setOnExportListener(new SQLiteImporterExporter.ExportListener() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        exportdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                new AlertDialog.Builder(getContext())
                        .setTitle("Export")
                        .setMessage("You are about to export your db? Continue?")
                        .setIcon(
                                getResources().getDrawable(
                                        android.R.drawable.ic_dialog_alert))
                        .setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        //Do Something Here
                                        //exportDB

                                        int check= ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE);
                                        if(check==0) //which means we got the permission
                                        {
                                            try {
                                                sqLiteImporterExporter.exportDataBase(path);
                                                sqLiteImporterExporter.close();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                         else{//we need that permission
                                        Toast.makeText(getContext(), "Grant Permission First", Toast.LENGTH_SHORT).show();

                                        //Request Storage Permission at Start if it doesn't have it granted
                                         ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
                                        }

                                    }
                                })
                        .setNegativeButton("NO",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        //Do Something Here
                                        //Does Nothing

                                    }
                                }).show();

            }
        });

        importdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Import")
                        .setMessage("You are about to Import DB? Continue")
                        .setIcon(
                                getResources().getDrawable(
                                        android.R.drawable.ic_dialog_alert))
                        .setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        //Do Something Here
                                        //importDB


                                       int check= ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE);
                                       if(check==0) //which means we got the permission
                                       {
                                           try {
                                               sqLiteImporterExporter.importDataBase(path);
                                               sqLiteImporterExporter.close();
                                           } catch (Exception e) {
                                               e.printStackTrace();
                                           }
                                       }
                                       else {//we need that permission
                                           Toast.makeText(getContext(), "Grant Permission First", Toast.LENGTH_SHORT).show();
                                           //Request Storage Permission at Start if it doesn't have it granted
                                           ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                                       }


                                    }
                                })
                        .setNegativeButton("NO",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        //Do Something Here
                                        //Does Nothing
                                    }
                                }).show();
            }
        });






        //Finalizes the About Fragment
        fmt.replace(R.id.AboutFrag,fragment);
        fmt.commit();



    return view;}
}
