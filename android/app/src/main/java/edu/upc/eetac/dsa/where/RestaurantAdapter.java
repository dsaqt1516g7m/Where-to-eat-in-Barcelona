package edu.upc.eetac.dsa.where;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.content.Context;


import java.util.ArrayList;

import edu.upc.eetac.dsa.where.Client.Entity.Restaurant;

/**
 * Created by Carolina on 18/01/2016.
 */
public class RestaurantAdapter extends BaseAdapter {
    private ArrayList<Restaurant> data;
    private LayoutInflater inflater;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_sting, null);
            viewHolder = new ViewHolder();

            viewHolder.tvId = (TextView) convertView
                    .findViewById(R.id.textAddress);
            viewHolder.tvName = (TextView) convertView
                    .findViewById(R.id.textName);
            viewHolder.tvDescription = (TextView) convertView
                    .findViewById(R.id.textViewDescription);
            viewHolder.tvLikes = (TextView) convertView
                    .findViewById(R.id.textViewLikes);
            viewHolder.tvAddress = (TextView) convertView
                    .findViewById(R.id.textViewAddress);
            viewHolder.tvPhone = (TextView) convertView
                    .findViewById(R.id.textViewPhone);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String id = data.get(position).getId();
        String name = data.get(position).getName();
        String description = data.get(position).getDescription();
        Integer likes = data.get(position).getLikes();
        String address = data.get(position).getAddress();
        String phone = data.get(position).getPhone();

        viewHolder.tvId.setText(id);
        viewHolder.tvName.setText(name);
        viewHolder.tvDescription.setText(description);
        viewHolder.tvLikes.setText(likes);
        viewHolder.tvAddress.setText(address);
        viewHolder.tvPhone.setText(phone);
              return convertView;
    }

    private static class ViewHolder {
        TextView tvId;
        TextView tvName;
        TextView tvDescription;
        TextView tvOwner;
        TextView tvLikes;
        TextView tvAddress;
        TextView tvPhone;

    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(((Restaurant) getItem(position)).getId());
    }


    public RestaurantAdapter(Context context, ArrayList<Restaurant> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
    }
}