package edu.upc.eetac.dsa.where;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.security.acl.Owner;

import edu.upc.eetac.dsa.where.Client.Entity.RestaurantCollection;

/**
 * Created by Carolina on 18/01/2016.
 */
public class RestaurantCollectionAdapter extends BaseAdapter {
    private RestaurantCollection restaurantCollection;
    private LayoutInflater layoutInflater;;

    public RestaurantCollectionAdapter(Context context, RestaurantCollection restaurantCollection){
        layoutInflater = LayoutInflater.from(context);
        this.restaurantCollection = restaurantCollection;
    }

    @Override
    public int getCount() {
        return restaurantCollection.getRestaurants().size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantCollection.getRestaurants().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_sting, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String name = restaurantCollection.getRestaurants().get(position).getName();
        String Description = restaurantCollection.getRestaurants().get(position).getDescription();
        String Address = restaurantCollection.getRestaurants().get(position).getAddress();


        viewHolder.textViewName.setText(name);
        viewHolder.textViewDescrition.setText(Description);
        viewHolder.textViewAddress.setText(Address);
        return convertView;
    }

    class ViewHolder{
        TextView textViewName;
        TextView textViewDescrition;
        TextView textViewAddress;

        ViewHolder(View row){
            this.textViewName = (TextView) row
                    .findViewById(R.id.textName);
            this.textViewDescrition = (TextView) row
                    .findViewById(R.id.textDescription);
            this.textViewAddress = (TextView) row
                    .findViewById(R.id.textAddress);
        }
    }

}