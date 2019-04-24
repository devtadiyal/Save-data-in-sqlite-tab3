package com.kk.dialer.tabfrag;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;

import com.kk.dialer.R;


import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class TabFragment2 extends Fragment {
    private static final int CONTACT_PICKER_REQUEST = 991;
    public static final int REQUEST_READ_CONTACTS = 79;
    Context context;
    Button uploadmp3;
    private boolean _hasLoadedOnce = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.tabfrag2, container, false);

         uploadmp3 = view.findViewById(R.id.upload_mp3);

        uploadmp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED)) {


                    Intent intent_upload = new Intent();
                    intent_upload.setType("audio/*");
                    intent_upload.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent_upload,1);


                } else {
                    // request permission
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 101);
                }
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){

        if(requestCode == 1){

            if(resultCode == RESULT_OK){

                //the selected audio.
                Uri uri = data.getData();
                getPath(uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // UPDATED!
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            System.out.println("SELECTED AUDIO  " + cursor.getString(column_index));
            Uri uri1 = Uri.parse(cursor.getString(column_index));
            //Setting MediaController and URI, then starting the videoView
            MediaPlayer mp=new MediaPlayer();
            try{
                mp.setDataSource(uri1.getPath());//Write your location here
                mp.prepare();
                mp.start();

            }catch(Exception e){e.printStackTrace();}

            return cursor.getString(column_index);
        } else
            System.out.println("SELECTED AUDIO  ");
        return null;
    }

    /*@Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);


        if (this.isVisible()) {
            // we check that the fragment is becoming visible
            if (isFragmentVisible_ && !_hasLoadedOnce) {
                if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_CONTACTS)
                        == PackageManager.PERMISSION_GRANTED) {
                    //  getContacts();
                    Intent intent = new Intent(getContext(), UnbindService.class);
                    getContext().startService(intent);
                } else {
                    requestLocationPermission();
                }
                _hasLoadedOnce = true;
            }
        }

    }*/
}