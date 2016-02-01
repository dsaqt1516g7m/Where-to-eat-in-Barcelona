package edu.upc.eetac.dsa.where;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.TextView;

import edu.upc.eetac.dsa.where.Client.Entity.CommentsCollection;

/**
 * Created by Carolina on 18/01/2016.
 */
/*public class CommentsCollectionAdapter extends BaseAdapter {
    private CommentsCollection restaurantCollection;
    private LayoutInflater layoutInflater;;

    public CommentsCollectionAdapter(Context context, CommentsCollection commentsCollection){
        layoutInflater = LayoutInflater.from(context);
        this.restaurantCollection = restaurantCollection;
    }

    @Override
    public int getCount() {
        return restaurantCollection.getComments().size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantCollection.getComments().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_comments, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String Creator = CommentsCollection;
        String Title = CommentsCollection;
        String Comment = CommentsCollection;


        viewHolder.textViewCreator.setText(Creator);
        viewHolder.textViewTitle.setText(Title);
        viewHolder.textViewComment.setText(Comment);
        return convertView;
    }

    class ViewHolder{
        TextView textViewCreator;
        TextView textViewTitle;
        TextView textViewComment;

        ViewHolder(View row){
            this.textViewCreator = (TextView) row
                    .findViewById(R.id.textCreator);
            this.textViewTitle = (TextView) row
                    .findViewById(R.id.textTitle);
            this.textViewComment = (TextView) row
                    .findViewById(R.id.textComment);
        }
    }

}*/