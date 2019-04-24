package com.kk.dialer.tabfrag;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.kk.dialer.R;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class TabFragment1 extends Fragment {
    VideoView videoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tabfrag1, container, false);

        videoView = (VideoView) view.findViewById(R.id.videoview);
        Button upload = view.findViewById(R.id.upload);
        //Creating MediaController


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED)) {


                    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickIntent.setType("video/*");
                    startActivityForResult(pickIntent, 143);
                    Intent intent = new Intent();


                } else {
                    // request permission
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 101);
                }
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 143) {
                Uri selectedImageUri = data.getData();

                // OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();

                if (filemanagerstring != null) {

                    /*System.out.println("SELECTED IMAGE PATH "+filemanagerstring);
                    MediaController mediaController= new MediaController(getContext());
                    mediaController.setAnchorView(videoView);
                    Uri uri=Uri.parse(filemanagerstring+".mp4");
                    //Setting MediaController and URI, then starting the videoView
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(uri);
                    videoView.requestFocus();
                    videoView.start();*/
                    getPath(selectedImageUri);
                } else {
                    System.out.println("SELECTED " + filemanagerstring);
                }

            }
        }
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
            System.out.println("SELECTED IMAGE  " + cursor.getString(column_index));
            MediaController mediaController = new MediaController(getContext());
            mediaController.setAnchorView(videoView);
            Uri uri1 = Uri.parse(cursor.getString(column_index));
            //Setting MediaController and URI, then starting the videoView
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(uri1);
            videoView.requestFocus();
            videoView.start();
            return cursor.getString(column_index);
        } else
            System.out.println("SELECTED IMAGE  ");
        return null;
    }
}