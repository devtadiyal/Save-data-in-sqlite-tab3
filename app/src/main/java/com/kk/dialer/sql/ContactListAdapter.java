package com.kk.dialer.sql;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.kk.dialer.R;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    //ArrayList<OrderStatusKeyModel> list;
    Context context;
    List<Contact> contacts;
    String[] namearray = new String[]{"Manoj Tiwari", "King Bach", "Bangla Hero", "King Bach"};
    String[] timearray = new String[]{"Reached", "2:30 hour", "Waiting...", "00:45 mins"};
    int[] imgarray = new int[]{R.drawable.ic_contact_picture_holo_dark, R.drawable.ic_contact_picture_holo_dark, R.drawable.ic_contact_picture_holo_dark, R.drawable.ic_contact_picture_holo_dark,};
    int[] statusarray = new int[]{R.drawable.ic_contact_picture_holo_dark, R.drawable.ic_contact_picture_holo_dark, R.drawable.ic_contact_picture_holo_dark, R.drawable.ic_contact_picture_holo_dark,};


    public ContactListAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.showlist, parent, false);



        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.name.setText(contacts.get(position).getName());
        holder.number.setText(contacts.get(position).getPhoneNumber());

        Bitmap bmp = BitmapFactory.decodeByteArray(contacts.get(position).get_image(),
                0, contacts.get(position).get_image().length);
        holder.img.setImageBitmap(bmp);

        /*for (int i = 0; i < 4; i++) {
            holder.img.setImageResource(imgarray[i]);
            holder.name.setText(namearray[i]);
            holder.time.setText(timearray[i]);
            holder.status.setImageResource(statusarray[i]);
        }*/
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img, status;
        TextView name, number;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            number = itemView.findViewById(R.id.number);
          //  status = itemView.findViewById(R.id.status);
            name = itemView.findViewById(R.id.name);

        }
    }
}
