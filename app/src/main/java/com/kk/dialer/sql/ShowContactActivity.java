package com.kk.dialer.sql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kk.dialer.R;

import java.util.List;

public class ShowContactActivity extends AppCompatActivity {

    RecyclerView rc ;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);
        rc = findViewById(R.id.rc);

        db = new DatabaseHandler(this);
        List<Contact> contacts = db.getAllContacts();

        ContactListAdapter adapter = new ContactListAdapter(this,contacts);
        // Attach the adapter to the recyclerview to populate items
        rc.setAdapter(adapter);
        // Set layout manager to position the items
        rc.setLayoutManager(new LinearLayoutManager(this));
    }
}
