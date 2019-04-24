package com.kk.dialer.tabfrag;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.kk.dialer.R;
import com.kk.dialer.sql.Contact;
import com.kk.dialer.sql.ContactListAdapter;
import com.kk.dialer.sql.DatabaseHandler;
import com.kk.dialer.sql.ShowContactActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class TabFragment3 extends Fragment {
    ImageView image;
    DatabaseHandler db;
    private EditText fname, number;
    private ImageView pic;
    private byte[] imagepath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabfrag3, container, false);

        pic = view.findViewById(R.id.pic);
        fname = view.findViewById(R.id.name);
        number = view.findViewById(R.id.number);

        Button save = view.findViewById(R.id.save);
        Button display = view.findViewById(R.id.display);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fname.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Enter name", Toast.LENGTH_LONG).show();
                } else if (number.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Enter number", Toast.LENGTH_LONG).show();
                } else {
                    db = new DatabaseHandler(getActivity());
                    // Inserting Contacts
                    Log.d("Insert: ", "Inserting ..");
                db.addContact(new Contact(fname.getText().toString().trim(),
                            number.getText().toString().trim(),
                            imagepath));

                    clear();
                }
            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reading all contacts

                Intent intent = new Intent(getContext(), ShowContactActivity.class);
                startActivity(intent);
                Log.d("Reading: ", "Reading all contacts..");


               /* List<Contact> contacts = db.getAllContacts();
                for (Contact cn : contacts) {
                    String log = "Id: " + cn.getID() + " ," +
                            "Name: " + cn.getName() + " ," +
                            "Phone: " + cn.getPhoneNumber() +
                            "Image: " + cn.get_image();
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                }*/

            }
        });

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED)) {

                    selectImage();
                } else {
                    // request permission
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 101);
                }

            }
        });

        return view;
    }

    public void clear()
    {
        pic.setImageResource(R.drawable.ic_launcher_foreground);
        fname.setText("");
        number.setText("");
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        imagepath = stream.toByteArray();
        return stream.toByteArray();
    }

   /* // get the base 64 string
    String imgString = Base64.encodeToString(getBytesFromBitmap(someImg),
            Base64.NO_WRAP);*/

    public void selectImage() {
        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pic.setImageURI(imageUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                getBytesFromBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}